package collectioner.service.impl;

import collectioner.model.entity.UserEntity;
import collectioner.model.entity.UserRoleEntity;
import collectioner.model.entity.enums.UserRole;
import collectioner.model.service.UserRolesServiceModel;
import collectioner.repository.UserRepository;
import collectioner.repository.UserRoleRepository;
import collectioner.service.UserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository, UserRepository userRepository) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void initRoles() {
        if (userRoleRepository.findAll().size() == 0) {
            UserRoleEntity adminRole = new UserRoleEntity().setRole(UserRole.ADMIN);
            UserRoleEntity userRole = new UserRoleEntity().setRole(UserRole.USER);

            userRoleRepository.saveAll(List.of(adminRole, userRole));
        }
    }

    @Override
    public boolean addRoleToUser(UserRolesServiceModel userRolesServiceModel) {
        Optional<UserEntity> user = userRepository.findByUsername(userRolesServiceModel.username);
        if (user.isPresent()) {
            UserEntity currUser = user.get();
            List<UserRoleEntity> currRoles = currUser.getRoles();
            if (userRolesServiceModel.role.equals("ADMIN")) {
                boolean hasThatRole = false;
                for (UserRoleEntity currRole : currRoles) {
                    if (currRole.getRole().name().equals("ADMIN")) {
                        hasThatRole = true;
                        break;
                    }
                }
                if (hasThatRole) {
                    return false;
                }
                UserRoleEntity newRole = userRoleRepository.findByRole(UserRole.ADMIN).orElse(null);
                currRoles.add(newRole);
                currUser.setRoles(currRoles);
                userRepository.save(currUser);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean demoteUserRole(UserRolesServiceModel userRolesServiceModel) {
        Optional<UserEntity> user = userRepository.findByUsername(userRolesServiceModel.username);
        if (user.isPresent()) {
            UserEntity currUser = user.get();
            List<UserRoleEntity> currRoles = currUser.getRoles();
            if (userRolesServiceModel.role.equals("USER")) {
               boolean hasHigherRole = false;
                for (UserRoleEntity currRole : currRoles) {
                    if (currRole.getId() == 1) {
                        hasHigherRole = true;
                        break;
                    }
                }
                if (hasHigherRole) {
                    UserRoleEntity newRole = userRoleRepository.findByRole(UserRole.USER).orElse(null);
                    List<UserRoleEntity> newRoles = List.of(newRole);
                    currUser.setRoles(newRoles);
                    userRepository.save(currUser);
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }

        return false;
    }
}
