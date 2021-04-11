package collectioner.web;

import collectioner.model.entity.HeroEntity;
import collectioner.model.entity.UserEntity;
import collectioner.repository.HeroRepository;
import collectioner.repository.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
@RunWith(SpringRunner.class)
public class HomeControllerTest {
    @Autowired
    private HomeController serviceToTest;
    @Autowired
    private HeroRepository heroRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testIndex() {
        Assertions.assertEquals("index", serviceToTest.index());
    }
    @Test
    public void testHome() {
        Assertions.assertEquals("home", serviceToTest.home());
    }
    @Test
    public void testRestoreEnergy() {

        HeroEntity hero = new HeroEntity();
        heroRepository.save(hero);

        UserEntity user = new UserEntity();
        user.setUsername("Reg3");
        user.setPassword("123123");
        user.setHero(hero);
        userRepository.save(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "Reg3",
                "123123"
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);



        Assertions.assertEquals("redirect:/home", serviceToTest.restoreEnergy());
    }

    @Test
    public void testGetUsername() {

        HeroEntity hero = new HeroEntity();
        heroRepository.save(hero);

        UserEntity user = new UserEntity();
        user.setUsername("Reg4");
        user.setPassword("123123");
        user.setHero(hero);
        userRepository.save(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "Reg4",
                "123123"
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Assertions.assertEquals(user.getUsername(), serviceToTest.getCurrentUsername());
    }

    @Test
    public void testHero() {
        HeroEntity hero = new HeroEntity();
        heroRepository.save(hero);

        UserEntity user = new UserEntity();
        user.setUsername("Reg5");
        user.setPassword("123123");
        user.setHero(hero);
        userRepository.save(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "Reg5",
                "123123"
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Assertions.assertEquals(user.getHero().getId(), serviceToTest.getHero().getId());
    }


}
