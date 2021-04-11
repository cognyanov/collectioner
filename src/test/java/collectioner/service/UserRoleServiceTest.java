package collectioner.service;

import collectioner.model.entity.UserEntity;
import collectioner.model.entity.enums.UserRole;
import collectioner.model.service.UserRolesServiceModel;
import collectioner.repository.UserRepository;
import collectioner.repository.UserRoleRepository;
import collectioner.service.impl.UserRoleServiceImpl;
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
public class UserRoleServiceTest {
    @Autowired
    private UserRoleService serviceToTest;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Mock
    UserRoleRepository mockUserRoleRepository;

    @Mock
    UserRepository mockUserRepository;

    @BeforeEach
    public void setUp() {
        serviceToTest = new UserRoleServiceImpl(mockUserRoleRepository, mockUserRepository);
    }

    @Test
    public void testInitRoles() {
        serviceToTest.initRoles();

        Assertions.assertTrue(userRoleRepository.findAll().size() > 1);
    }

    @Test
    public void testAddRoleToUser() {
        UserEntity user = new UserEntity();
        user.addRole(userRoleRepository.findByRole(UserRole.USER).get());
        Assertions.assertTrue(user.getRoles().size() > 0);
    }

    @Test
    public void testDemoteUser() {

        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setUsername("roles1");
        user.setPassword("123123");

        userRepository.save(user);

        user.addRole(userRoleRepository.findByRole(UserRole.USER).get());
        user.addRole(userRoleRepository.findByRole(UserRole.ADMIN).get());

        UserRolesServiceModel userRolesServiceModel = new UserRolesServiceModel();
        userRolesServiceModel.setUsername("roles1");
        userRolesServiceModel.setRole("USER");
        serviceToTest.demoteUserRole(userRolesServiceModel);

    }
}
