package collectioner.service;

import collectioner.model.entity.UserEntity;
import collectioner.model.service.UserLoginServiceModel;
import collectioner.model.service.UserRegistrationServiceModel;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {


    void registerUser(UserRegistrationServiceModel userServiceModel);

    boolean usernameExists(String username);

    void loginUser(UserLoginServiceModel userServiceModel);
}
