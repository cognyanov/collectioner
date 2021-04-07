package collectioner.service.impl;

import collectioner.model.entity.HeroEntity;
import collectioner.model.entity.ItemEntity;
import collectioner.model.entity.UserEntity;
import collectioner.repository.HeroRepository;
import collectioner.repository.ItemRepository;
import collectioner.repository.UserRepository;
import collectioner.service.HeroService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

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

        return user.getHero();
    }

    @Override
    public boolean work() {
        HeroEntity hero = getCurrentHero();

        if (hero.getEnergy() >= 1) {
            hero.setEnergy(hero.getEnergy() - 1);
            hero.setGold(hero.getGold() + 5);
            hero.setHasWorked(true);
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
            hero.setAttack(hero.getAttack() + 4);
            hero.setDefense(hero.getDefense() + 2);
            hero.setHasTrained(true);
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
            heroRepository.save(hero);
            return true;
        } else if (hero.getWeapon().getId() == 2L && hero.getGold() >= 30) {
            hero.setWeapon(itemRepository.findById(3L).orElse(null));
            hero.setGold(hero.getGold() - 30);
            hero.updateStats();
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
            heroRepository.save(hero);
            return true;
        } else if (hero.getShield().getId() == 5L && hero.getGold() >= 30) {
            hero.setShield(itemRepository.findById(6L).orElse(null));
            hero.setGold(hero.getGold() - 30);
            hero.updateStats();
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

    public String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }
}
