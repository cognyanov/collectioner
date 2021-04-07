package collectioner.service;

import collectioner.model.service.UserRolesServiceModel;

public interface UserRoleService {
    void initRoles();

    boolean addRoleToUser(UserRolesServiceModel userRolesServiceModel);

    boolean demoteUserRole(UserRolesServiceModel userRolesServiceModel);
}
