package collectioner.service;

import collectioner.model.entity.UserEntity;
import collectioner.model.service.UserRegistrationServiceModel;
import collectioner.repository.HeroRepository;
import collectioner.repository.ItemRepository;
import collectioner.repository.UserRepository;
import collectioner.repository.UserRoleRepository;
import collectioner.service.impl.CollectionerUserService;
import collectioner.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Autowired
    private UserService serviceToTest;

    @Autowired
    private UserRepository userRepository;

    @Mock
    ModelMapper modelMapper;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    UserRoleRepository userRoleRepository;
    @Mock
    UserRepository mockUserRepository;
    @Mock
    CollectionerUserService collectionerUserService;
    @Mock
    HeroRepository heroRepository;
    @Mock
    ItemRepository mockItemRepository;

    @BeforeEach
    public void setUp() {
        serviceToTest = new UserServiceImpl(modelMapper, passwordEncoder, userRoleRepository,
                mockUserRepository, collectionerUserService, heroRepository, mockItemRepository);
    }

    @Test
    public void testRegisterUser() {
        UserRegistrationServiceModel model = new UserRegistrationServiceModel();

        model.setUsername("Reg1");
        model.setPassword("123213");

        serviceToTest.registerUser(model);

        UserEntity userEntity = userRepository.findByUsername("Reg1").orElse(null);

        Assertions.assertEquals(userEntity.getUsername(), model.getUsername());
        Assertions.assertEquals(100, userEntity.getHero().getBaseHP());
        Assertions.assertEquals(10, userEntity.getHero().getBaseAttack());
        Assertions.assertEquals(10, userEntity.getHero().getBaseDefense());
        Assertions.assertEquals(10, userEntity.getHero().getEnergy());
        Assertions.assertEquals(20, userEntity.getHero().getGold());
        Assertions.assertEquals(0, userEntity.getHero().getWeapon().getATK());
        Assertions.assertEquals(0,userEntity.getHero().getShield().getATK());

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
