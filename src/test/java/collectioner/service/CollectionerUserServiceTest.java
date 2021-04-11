package collectioner.service;

import collectioner.model.entity.HeroEntity;
import collectioner.model.entity.UserEntity;
import collectioner.repository.HeroRepository;
import collectioner.repository.UserRepository;
import collectioner.service.impl.CollectionerUserService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@RunWith(SpringRunner.class)
public class CollectionerUserServiceTest {
    @Autowired
    private CollectionerUserService serviceToTest;

    @Autowired
    private HeroRepository heroRepository;

    @Autowired
    private UserRepository userRepository;

    @Mock
    HeroService mockHeroService;

    @Mock
    HeroRepository mockHeroRepository;

    @BeforeEach
    public void setUp() {
        serviceToTest = new CollectionerUserService(mockHeroService, mockHeroRepository);
    }

    @Test
    public void testScheduleEnergyToAll() {
        HeroEntity hero = new HeroEntity();
        hero.setEnergyToRestore(5);
        heroRepository.save(hero);

        UserEntity user = new UserEntity();
        user.setUsername("csA");
        user.setPassword("123123");
        user.setHero(hero);
        userRepository.save(user);

        serviceToTest.scheduleUpdateEnergyToAll();

        Assertions.assertEquals(5, hero.getEnergyToRestore());
    }

    @Test
    public void testLoadUserByUsername() {
        Assertions.assertEquals(null, serviceToTest.loadUserByUsername("Kris"));
    }

    @Test
    public void testUpdateDay() {
        HeroEntity hero = new HeroEntity();
        hero.setEnergyToRestore(5);
        hero.setHasTrained(false);
        hero.setHasWorked(false);
        heroRepository.save(hero);

        UserEntity user = new UserEntity();
        user.setUsername("csB");
        user.setPassword("123123");
        user.setHero(hero);
        userRepository.save(user);

        serviceToTest.updateDay();

        Assertions.assertFalse(hero.isHasTrained());
        Assertions.assertFalse(hero.isHasWorked());
    }
}
