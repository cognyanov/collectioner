package collectioner.service.impl;

import collectioner.model.entity.*;
import collectioner.repository.HeroRepository;
import collectioner.repository.ItemRepository;
import collectioner.repository.UserRepository;
import collectioner.service.HeroService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class HeroServiceImpl implements HeroService {

    private final UserRepository userRepository;
    private final HeroRepository heroRepository;
    private final ItemRepository itemRepository;

    public HeroServiceImpl(UserRepository userRepository, HeroRepository heroRepository, ItemRepository itemRepository) {
        this.userRepository = userRepository;
        this.heroRepository = heroRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public HeroEntity getCurrentHero() {
        UserEntity user = userRepository.findByUsername(getCurrentUsername()).orElse(null);

        if (user == null) {
            return null;
        }
        return user.getHero();
    }

    @Override
    public boolean work() {
        HeroEntity hero = getCurrentHero();

        if (hero.getEnergy() >= 1) {
            hero.setEnergy(hero.getEnergy() - 1);
            hero.setGold(hero.getGold() + 5);
            hero.setHasWorked(true);
            hero.setDaysWorked(hero.getDaysWorked() + 1);
            heroRepository.save(hero);
            return true;
        } else {
            return false;
        }

    }

    @Override
    public boolean train() {
        HeroEntity hero = getCurrentHero();

        if (hero.getEnergy() >= 1) {
            hero.setEnergy(hero.getEnergy() - 1);
            hero.setBaseAttack(hero.getBaseAttack() + 4);
            hero.setBaseDefense(hero.getBaseDefense() + 2);
            hero.updateStats();
            hero.setHasTrained(true);
            hero.setDaysTrained(hero.getDaysTrained() + 1);
            heroRepository.save(hero);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean upgradeWeapon() {
        HeroEntity hero = getCurrentHero();

        if (hero.getWeapon().getId() == 1L && hero.getGold() >= 10) {
            hero.setWeapon(itemRepository.findById(2L).orElse(null));
            hero.setGold(hero.getGold() - 10);
            hero.updateStats();
            hero.setAluminium(hero.getAluminium() - 5);
            heroRepository.save(hero);
            return true;
        } else if (hero.getWeapon().getId() == 2L && hero.getGold() >= 30) {
            hero.setWeapon(itemRepository.findById(3L).orElse(null));
            hero.setGold(hero.getGold() - 30);
            hero.updateStats();
            hero.setAluminium(hero.getAluminium() - 30);
            heroRepository.save(hero);
            return true;
        } else {
            return false;
        }
    }

    public boolean upgradeShield() {
        HeroEntity hero = getCurrentHero();

        if (hero.getShield().getId() == 4L && hero.getGold() >= 10) {
            hero.setShield(itemRepository.findById(5L).orElse(null));
            hero.setGold(hero.getGold() - 10);
            hero.updateStats();
            hero.setSteel(hero.getSteel() - 5);
            heroRepository.save(hero);
            return true;
        } else if (hero.getShield().getId() == 5L && hero.getGold() >= 30) {
            hero.setShield(itemRepository.findById(6L).orElse(null));
            hero.setGold(hero.getGold() - 30);
            hero.updateStats();
            hero.setSteel(hero.getSteel() - 30);
            heroRepository.save(hero);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void restoreEnergy() {
        HeroEntity hero = getCurrentHero();
        while (hero.getEnergyToRestore() > 0 && hero.getEnergy() < 10) {
            if (hero.getSteaks() > 0) {
                hero.setEnergy(hero.getEnergy() + 1);
                hero.setEnergyToRestore(hero.getEnergyToRestore() - 1);
                hero.setSteaks(hero.getSteaks() - 1);
            } else {
                break;
            }
        }
        heroRepository.save(hero);
    }

    @Override
    public void usePotion() {
        HeroEntity hero = getCurrentHero();
        if (hero.getEnergy() < 10) {
            if (hero.getEnergyPotions() > 0) {
                hero.setEnergy(10);
                hero.setEnergyPotions(hero.getEnergyPotions() - 1);
                heroRepository.save(hero);
            }
        }
    }

    @Override
    public boolean attackVoid() {
        HeroEntity hero = getCurrentHero();
        hero.setEnergy(hero.getEnergy() - 1);
        heroRepository.save(hero);
        Monster voidMonster = new VoidMonster();
        boolean heroWin = false;


        while (hero.getHP() > 0 && voidMonster.getHP() > 0) {
            hero.hit(voidMonster);
            if (voidMonster.getHP() < 1) {
                heroWin = true;
                break;
            }
            voidMonster.hit(hero);
            if (hero.getHP() < 1) {
                break;
            }
        }

        return heroWin;
    }

    @Override
    public boolean attackNether() {
        HeroEntity hero = getCurrentHero();
        hero.setEnergy(hero.getEnergy() - 2);
        heroRepository.save(hero);
        Monster netherMonster = new NetherMonster();
        boolean heroWin = false;


        while (hero.getHP() > 0 && netherMonster.getHP() > 0) {
            hero.hit(netherMonster);
            if (netherMonster.getHP() < 1) {
                heroWin = true;
                break;
            }
            netherMonster.hit(hero);
            if (hero.getHP() < 1) {
                break;
            }
        }

        return heroWin;
    }

    @Override
    public boolean attackFire() {
        HeroEntity hero = getCurrentHero();
        hero.setEnergy(hero.getEnergy() - 2);
        heroRepository.save(hero);
        Monster fireMonster = new FireMonster();
        boolean heroWin = false;

        while (hero.getHP() > 0 && fireMonster.getHP() > 0) {
            hero.hit(fireMonster);
            if (fireMonster.getHP() < 1) {
                heroWin = true;
                break;
            }
            fireMonster.hit(hero);
            if (hero.getHP() < 1) {
                break;
            }
        }

        return heroWin;
    }


    @Override
    public int[] wonVoid() {
        int[] materials = new int[2];

        HeroEntity winner = getCurrentHero();
        winner.setGold(winner.getGold() + 1);
        winner.setRawSteaks(winner.getRawSteaks() + 2);

        Random rand = new Random();

        int num = rand.nextInt();
        if (num % 2 == 0) {
            Random random = new Random();
            if (random.nextInt() % 2 == 0) {
                materials[0] = 1;
                winner.setAluminium(winner.getAluminium() + 2);
            } else {
                materials[1] = 1;
                winner.setSteel(winner.getSteel() + 2);
            }
        }

        heroRepository.save(winner);

        return materials;
    }

    @Override
    public int[] wonNether() {
        int[] materials = new int[2];

        HeroEntity winner = getCurrentHero();
        winner.setGold(winner.getGold() + 2);
        winner.setRawSteaks(winner.getRawSteaks() + 2);

        Random rand = new Random();

        int num = rand.nextInt();
        if (num % 2 == 0) {
            Random random = new Random();
            if (random.nextInt() % 2 == 0) {
                materials[0] = 1;
                winner.setAluminium(winner.getAluminium() + 1);
            } else {
                materials[1] = 1;
                winner.setSteel(winner.getSteel() + 1);
            }
        }

        heroRepository.save(winner);

        return materials;
    }

    @Override
    public int[] wonFire() {
        int[] materials = new int[2];

        HeroEntity winner = getCurrentHero();
        winner.setGold(winner.getGold() + 3);
        winner.setRawSteaks(winner.getRawSteaks() + 1);

        Random rand = new Random();

        int num = rand.nextInt();
        if (num % 2 == 0) {
            Random random = new Random();
            if (random.nextInt() % 2 == 0) {
                materials[0] = 1;
                winner.setAluminium(winner.getAluminium() + 5);
            } else {
                materials[1] = 1;
                winner.setSteel(winner.getSteel() + 5);
            }
        }

        heroRepository.save(winner);

        return materials;
    }


    @Override
    public int bake() {
        HeroEntity hero = getCurrentHero();
        int steaks = hero.getRawSteaks();
        hero.setSteaks(hero.getRawSteaks() + hero.getSteaks());
        hero.setRawSteaks(0);
        heroRepository.save(hero);
        return steaks;
    }

    @Override
    public void buy10EnergyPotions() {
        HeroEntity hero = getCurrentHero();
        hero.setGold(hero.getGold() - 30);
        hero.setEnergyPotions(hero.getEnergyPotions() + 10);
        heroRepository.save(hero);
    }

    public String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }
}
