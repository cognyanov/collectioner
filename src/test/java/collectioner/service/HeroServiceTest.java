package collectioner.service;

import collectioner.model.entity.HeroEntity;
import collectioner.model.entity.UserEntity;
import collectioner.repository.HeroRepository;
import collectioner.repository.ItemRepository;
import collectioner.repository.UserRepository;
import collectioner.service.impl.HeroServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@RunWith(SpringRunner.class)
public class HeroServiceTest {

    @Autowired
    private HeroService serviceToTest;

    @Mock
    UserRepository mockUserRepository;

    @Mock
    HeroRepository mockHeroRepository;

    @Mock
    ItemRepository mockItemRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    HeroRepository heroRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    HeroService heroService;

    @BeforeEach
    public void setUp() {
        serviceToTest = new HeroServiceImpl(mockUserRepository, mockHeroRepository, mockItemRepository);
    }




    @Test
    public void testGetCurrentHero() {

        HeroEntity hero = new HeroEntity();
        hero.setId(1L);
        heroRepository.save(hero);

        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setUsername("Kris");
        user.setPassword("123123");
        user.setHero(hero);
        userRepository.save(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "Kris",
                "123123"
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Assertions.assertEquals(1L, serviceToTest.getCurrentHero().getId());
        UserEntity currUser = userRepository.findByUsername(getCurrentUsername()).orElse(null);


        assertThat(serviceToTest.getCurrentHero(), samePropertyValuesAs(currUser.getHero()));
        Assertions.assertNotEquals(null, serviceToTest.getCurrentHero());
    }

    @Test
    public void testGetCurrentHeroNull() {
        Assertions.assertThrows(NullPointerException.class, () -> serviceToTest.getCurrentHero());
    }

    @Test
    public void testGetCurrentHeroWithNullAuth() {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "dasd",
                "dweqeqeq"
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        Assertions.assertNull(serviceToTest.getCurrentHero());
    }

    @Test
    public void testWorkValid() {
        HeroEntity hero = new HeroEntity();
        hero.setId(1L);
        hero.setEnergy(1000);
        heroRepository.save(hero);

        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setUsername("Kris");
        user.setPassword("123123");
        user.setHero(hero);
        userRepository.save(user);


        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "Kris",
                "123123"
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        HeroEntity heroEntity = userRepository.findByUsername("Kris").get().getHero();
        int beforeEnergy = heroEntity.getEnergy();

        serviceToTest.work();
        int afterEnergy = userRepository.findByUsername("Kris").get().getHero().getEnergy();
        Assertions.assertEquals(beforeEnergy - 1, afterEnergy);

    }

    @Test
    public void testWorkLowEnergy() {
        HeroEntity hero = new HeroEntity();
        hero.setId(2L);
        heroRepository.save(hero);

        UserEntity user = new UserEntity();
        user.setId(2L);
        user.setUsername("Dada");
        user.setPassword("123123");
        user.setHero(hero);
        userRepository.save(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "Dada",
                "123123"
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Assertions.assertFalse(serviceToTest.work());
    }

    @Test
    public void testTrainValid() {
        HeroEntity hero = new HeroEntity();
        hero.setId(3L);
        hero.setEnergy(100);
        hero.setWeapon(itemRepository.findById(1L).get());
        hero.setShield(itemRepository.findById(4L).get());
        heroRepository.save(hero);

        UserEntity user = new UserEntity();
        user.setId(3L);
        user.setUsername("CanTrain");
        user.setPassword("123123");
        user.setHero(hero);
        userRepository.save(user);


        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "CanTrain",
                "123123"
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Assertions.assertTrue(serviceToTest.train());
    }

    @Test
    public void testTrainLowEnergy() {
        HeroEntity hero = new HeroEntity();
        hero.setId(2L);
        heroRepository.save(hero);

        UserEntity user = new UserEntity();
        user.setId(2L);
        user.setUsername("Dada");
        user.setPassword("123123");
        user.setHero(hero);
        userRepository.save(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "Dada",
                "123123"
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Assertions.assertFalse(serviceToTest.train());
    }

    @Test
    public void testUpgradeWeaponToLvl1() {
        HeroEntity hero = new HeroEntity();

        hero.setWeapon(itemRepository.findById(1L).get());
        hero.setShield(itemRepository.findById(4L).get());
        hero.setGold(1000);
        hero.setAluminium(1000);
        hero.setSteel(1000);
        heroRepository.save(hero);

        UserEntity user = new UserEntity();

        user.setUsername("numberA");
        user.setPassword("123123");
        user.setHero(hero);

        userRepository.save(user);


        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "numberA",
                "123123"
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Assertions.assertTrue(serviceToTest.upgradeWeapon());
    }

    @Test
    public void testUpgradeWeaponToLvl2() {
        HeroEntity hero = new HeroEntity();

        hero.setWeapon(itemRepository.findById(2L).get());
        hero.setShield(itemRepository.findById(5L).get());
        hero.setGold(1000);
        hero.setAluminium(1000);
        hero.setSteel(1000);
        heroRepository.save(hero);

        UserEntity user = new UserEntity();

        user.setHero(hero);
        user.setUsername("numberB");
        user.setPassword("123123");
        userRepository.save(user);


        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "numberB",
                "123123"
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Assertions.assertTrue(serviceToTest.upgradeWeapon());
    }

    @Test
    public void testUpgradeWeaponNotEnoughMats() {
        HeroEntity hero = new HeroEntity();

        hero.setWeapon(itemRepository.findById(1L).get());
        hero.setShield(itemRepository.findById(4L).get());
        heroRepository.save(hero);

        UserEntity user = new UserEntity();

        user.setUsername("wepNotMats");
        user.setPassword("123123");
        user.setHero(hero);
        userRepository.save(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "wepNotMats",
                "123123"
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Assertions.assertFalse(serviceToTest.upgradeWeapon());
    }

    @Test
    public void testUpgradeShieldToLvl1() {
        HeroEntity hero = new HeroEntity();

        hero.setWeapon(itemRepository.findById(1L).get());
        hero.setShield(itemRepository.findById(4L).get());
        hero.setGold(1000);
        hero.setAluminium(1000);
        hero.setSteel(1000);
        heroRepository.save(hero);

        UserEntity user = new UserEntity();

        user.setUsername("numberC");
        user.setPassword("123123");
        user.setHero(hero);

        userRepository.save(user);


        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "numberC",
                "123123"
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Assertions.assertTrue(serviceToTest.upgradeShield());
    }

    @Test
    public void testUpgradeShieldToLvl2() {
        HeroEntity hero = new HeroEntity();

        hero.setWeapon(itemRepository.findById(2L).get());
        hero.setShield(itemRepository.findById(5L).get());
        hero.setGold(1000);
        hero.setAluminium(1000);
        hero.setSteel(1000);
        heroRepository.save(hero);

        UserEntity user = new UserEntity();

        user.setHero(hero);
        user.setUsername("numberD");
        user.setPassword("123123");
        userRepository.save(user);


        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "numberD",
                "123123"
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Assertions.assertTrue(serviceToTest.upgradeWeapon());
    }

    @Test
    public void testUpgradeShieldNotEnoughMats() {
        HeroEntity hero = new HeroEntity();

        hero.setWeapon(itemRepository.findById(1L).get());
        hero.setShield(itemRepository.findById(4L).get());
        heroRepository.save(hero);

        UserEntity user = new UserEntity();

        user.setUsername("shieldNotEnoughMats");
        user.setPassword("123123");
        user.setHero(hero);
        userRepository.save(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "shieldNotEnoughMats",
                "123123"
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Assertions.assertFalse(serviceToTest.upgradeWeapon());
    }

    @Test
    public void testRestoreEnergy() {
        HeroEntity hero = new HeroEntity();

        hero.setWeapon(itemRepository.findById(1L).get());
        hero.setShield(itemRepository.findById(4L).get());
        hero.setEnergy(5);
        hero.setEnergyToRestore(1);
        hero.setSteaks(5);
        heroRepository.save(hero);

        UserEntity user = new UserEntity();

        user.setUsername("numberE");
        user.setPassword("123123");
        user.setHero(hero);
        userRepository.save(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "numberE",
                "123123"
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        serviceToTest.restoreEnergy();

        HeroEntity newHero = userRepository.findByUsername("numberE").get().getHero();

        Assertions.assertEquals(hero.getEnergy() + 1, newHero.getEnergy());
    }

    @Test
    public void testRestoreEnergyLowSteaks() {
        HeroEntity hero = new HeroEntity();

        hero.setWeapon(itemRepository.findById(1L).get());
        hero.setShield(itemRepository.findById(4L).get());
        hero.setEnergy(5);
        hero.setEnergyToRestore(5);
        hero.setSteaks(1);
        heroRepository.save(hero);

        UserEntity user = new UserEntity();

        user.setUsername("numberF");
        user.setPassword("123123");
        user.setHero(hero);
        userRepository.save(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "numberF",
                "123123"
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        serviceToTest.restoreEnergy();

        HeroEntity newHero = userRepository.findByUsername("numberF").get().getHero();

        Assertions.assertEquals(hero.getEnergy() + 1, newHero.getEnergy());
    }

    @Test
    public void testUsePotionValid() {
        HeroEntity hero = new HeroEntity();

        hero.setWeapon(itemRepository.findById(1L).get());
        hero.setShield(itemRepository.findById(4L).get());
        hero.setEnergy(5);
        hero.setEnergyToRestore(5);
        hero.setSteaks(1);
        hero.setEnergyPotions(2);
        heroRepository.save(hero);

        UserEntity user = new UserEntity();

        user.setUsername("numberG");
        user.setPassword("123123");
        user.setHero(hero);
        userRepository.save(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "numberG",
                "123123"
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        serviceToTest.usePotion();
        HeroEntity newHero = userRepository.findByUsername("numberG").get().getHero();

        Assertions.assertEquals(10, newHero.getEnergy());

    }

    @Test
    public void testUsePotionInvalid() {
        HeroEntity hero = new HeroEntity();

        hero.setWeapon(itemRepository.findById(1L).get());
        hero.setShield(itemRepository.findById(4L).get());
        hero.setEnergy(5);
        hero.setEnergyToRestore(5);
        hero.setSteaks(1);
        hero.setEnergyPotions(0);
        heroRepository.save(hero);

        UserEntity user = new UserEntity();

        user.setUsername("numberH");
        user.setPassword("123123");
        user.setHero(hero);
        userRepository.save(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "numberH",
                "123123"
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        serviceToTest.usePotion();
        HeroEntity newHero = userRepository.findByUsername("numberH").get().getHero();

        Assertions.assertEquals(5, newHero.getEnergy());

    }

    @Test
    public void testUsePotionInvalidEnergy() {
        HeroEntity hero = new HeroEntity();

        hero.setWeapon(itemRepository.findById(1L).get());
        hero.setShield(itemRepository.findById(4L).get());
        hero.setEnergy(10);
        hero.setEnergyToRestore(5);
        hero.setSteaks(1);
        hero.setEnergyPotions(1);
        heroRepository.save(hero);

        UserEntity user = new UserEntity();

        user.setUsername("numberI");
        user.setPassword("123123");
        user.setHero(hero);
        userRepository.save(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "numberI",
                "123123"
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        serviceToTest.usePotion();
        HeroEntity newHero = userRepository.findByUsername("numberI").get().getHero();

        Assertions.assertEquals(10, newHero.getEnergy());

    }

    @Test
    public void testAttackVoidValid() {
        HeroEntity hero = new HeroEntity();

        hero.setWeapon(itemRepository.findById(1L).get());
        hero.setShield(itemRepository.findById(4L).get());
        hero.setHP(1000);
        hero.setAttack(100);
        hero.setDefense(100);
        hero.setEnergy(10);
        hero.setEnergyToRestore(5);
        hero.setSteaks(1);
        hero.setEnergyPotions(1);
        heroRepository.save(hero);

        UserEntity user = new UserEntity();

        user.setUsername("numberJ");
        user.setPassword("123123");
        user.setHero(hero);
        userRepository.save(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "numberJ",
                "123123"
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);


        Assertions.assertTrue(serviceToTest.attackVoid());
    }

    @Test
    public void testAttackVoidLose() {
        HeroEntity hero = new HeroEntity();

        hero.setWeapon(itemRepository.findById(1L).get());
        hero.setShield(itemRepository.findById(4L).get());
        hero.setHP(5);
        hero.setAttack(30);
        hero.setDefense(1);
        hero.setEnergy(10);
        hero.setEnergyToRestore(5);
        hero.setSteaks(1);
        hero.setEnergyPotions(1);
        heroRepository.save(hero);

        UserEntity user = new UserEntity();

        user.setUsername("numberK");
        user.setPassword("123123");
        user.setHero(hero);
        userRepository.save(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "numberK",
                "123123"
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);


        Assertions.assertFalse(serviceToTest.attackVoid());
    }

    @Test
    public void testAttackNetherValid() {
        HeroEntity hero = new HeroEntity();

        hero.setWeapon(itemRepository.findById(1L).get());
        hero.setShield(itemRepository.findById(4L).get());
        hero.setHP(1000);
        hero.setAttack(10000);
        hero.setDefense(1000);
        hero.setEnergy(10);
        hero.setEnergyToRestore(5);
        hero.setSteaks(1);
        hero.setEnergyPotions(1);
        heroRepository.save(hero);

        UserEntity user = new UserEntity();

        user.setUsername("numberL");
        user.setPassword("123123");
        user.setHero(hero);
        userRepository.save(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "numberL",
                "123123"
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);


        Assertions.assertTrue(serviceToTest.attackNether());
    }

    @Test
    public void testAttackNetherLose() {
        HeroEntity hero = new HeroEntity();

        hero.setWeapon(itemRepository.findById(1L).get());
        hero.setShield(itemRepository.findById(4L).get());
        hero.setHP(5);
        hero.setAttack(100);
        hero.setDefense(1);
        hero.setEnergy(10);
        hero.setEnergyToRestore(5);
        hero.setSteaks(1);
        hero.setEnergyPotions(1);
        heroRepository.save(hero);

        UserEntity user = new UserEntity();

        user.setUsername("numberM");
        user.setPassword("123123");
        user.setHero(hero);
        userRepository.save(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "numberM",
                "123123"
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);


        Assertions.assertFalse(serviceToTest.attackNether());
    }

    @Test
    public void testAttackFireValid() {
        HeroEntity hero = new HeroEntity();

        hero.setWeapon(itemRepository.findById(1L).get());
        hero.setShield(itemRepository.findById(4L).get());
        hero.setHP(10000);
        hero.setAttack(10000);
        hero.setDefense(10000);
        hero.setEnergy(10);
        hero.setEnergyToRestore(5);
        hero.setSteaks(1);
        hero.setEnergyPotions(1);
        heroRepository.save(hero);

        UserEntity user = new UserEntity();

        user.setUsername("numberN");
        user.setPassword("123123");
        user.setHero(hero);
        userRepository.save(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "numberN",
                "123123"
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);


        Assertions.assertTrue(serviceToTest.attackFire());
    }

    @Test
    public void testAttackFireLose() {
        HeroEntity hero = new HeroEntity();

        hero.setWeapon(itemRepository.findById(1L).get());
        hero.setShield(itemRepository.findById(4L).get());
        hero.setHP(5);
        hero.setAttack(300);
        hero.setDefense(1);
        hero.setEnergy(10);
        hero.setEnergyToRestore(5);
        hero.setSteaks(1);
        hero.setEnergyPotions(1);
        heroRepository.save(hero);

        UserEntity user = new UserEntity();

        user.setUsername("numberO");
        user.setPassword("123123");
        user.setHero(hero);
        userRepository.save(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "numberO",
                "123123"
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);


        Assertions.assertFalse(serviceToTest.attackFire());
    }

    @Test
    public void testWonVoid() {
        HeroEntity hero = new HeroEntity();

        hero.setWeapon(itemRepository.findById(1L).get());
        hero.setShield(itemRepository.findById(4L).get());
        hero.setHP(5);
        hero.setAttack(300);
        hero.setDefense(1);
        hero.setEnergy(10);
        hero.setEnergyToRestore(5);
        hero.setSteaks(1);
        hero.setEnergyPotions(1);
        heroRepository.save(hero);

        UserEntity user = new UserEntity();

        user.setUsername("numberP");
        user.setPassword("123123");
        user.setHero(hero);
        userRepository.save(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "numberP",
                "123123"
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Assertions.assertEquals(2, serviceToTest.wonVoid().length);
    }

    @Test
    public void testWonNether() {
        HeroEntity hero = new HeroEntity();
        heroRepository.save(hero);
        UserEntity user = new UserEntity();

        user.setUsername("numberQ");
        user.setPassword("123123");
        user.setHero(hero);
        userRepository.save(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "numberQ",
                "123123"
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Assertions.assertEquals(2, serviceToTest.wonNether().length);
    }

    @Test
    public void testWonFire() {
        HeroEntity hero = new HeroEntity();
        heroRepository.save(hero);
        UserEntity user = new UserEntity();

        user.setUsername("numberR");
        user.setPassword("123123");
        user.setHero(hero);
        userRepository.save(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "numberR",
                "123123"
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Assertions.assertEquals(2, serviceToTest.wonFire().length);
    }

    @Test
    public void testBakeValid() {
        HeroEntity hero = new HeroEntity();

        hero.setWeapon(itemRepository.findById(1L).get());
        hero.setShield(itemRepository.findById(4L).get());
        hero.setHP(5);
        hero.setAttack(300);
        hero.setDefense(1);
        hero.setEnergy(10);
        hero.setEnergyToRestore(5);
        hero.setSteaks(0);
        hero.setRawSteaks(10);
        hero.setEnergyPotions(1);
        heroRepository.save(hero);

        UserEntity user = new UserEntity();

        user.setUsername("numberS");
        user.setPassword("123123");
        user.setHero(hero);
        userRepository.save(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "numberS",
                "123123"
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Assertions.assertEquals(10, serviceToTest.bake());
    }
    @Test
    public void testBakeInvalid() {
        HeroEntity hero = new HeroEntity();

        hero.setWeapon(itemRepository.findById(1L).get());
        hero.setShield(itemRepository.findById(4L).get());
        hero.setHP(5);
        hero.setAttack(300);
        hero.setDefense(1);
        hero.setEnergy(10);
        hero.setEnergyToRestore(5);
        hero.setSteaks(0);
        hero.setRawSteaks(0);
        hero.setEnergyPotions(1);
        heroRepository.save(hero);

        UserEntity user = new UserEntity();

        user.setUsername("numberT");
        user.setPassword("123123");
        user.setHero(hero);
        userRepository.save(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "numberT",
                "123123"
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Assertions.assertEquals(0, serviceToTest.bake());
    }

    @Test
    public void testBuy10EnergyPotions() {
        HeroEntity hero = new HeroEntity();

        hero.setWeapon(itemRepository.findById(1L).get());
        hero.setShield(itemRepository.findById(4L).get());
        hero.setGold(100);
        hero.setHP(5);
        hero.setAttack(300);
        hero.setDefense(1);
        hero.setEnergy(10);
        hero.setEnergyToRestore(5);
        hero.setSteaks(0);
        hero.setRawSteaks(0);
        hero.setEnergyPotions(0);
        heroRepository.save(hero);

        UserEntity user = new UserEntity();

        user.setUsername("numberU");
        user.setPassword("123123");
        user.setHero(hero);
        userRepository.save(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "numberU",
                "123123"
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        serviceToTest.buy10EnergyPotions();

        int potions = userRepository.findByUsername("numberU").get().getHero().getEnergyPotions();

        Assertions.assertEquals(10, potions);
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
