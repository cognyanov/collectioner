package collectioner;

import collectioner.service.ItemService;
import collectioner.service.UserRoleService;
import collectioner.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class DBInit implements CommandLineRunner {
    private final UserRoleService userRoleService;
    private final ItemService itemService;
    private final UserService userService;


    public DBInit(UserRoleService userRoleService, ItemService itemService, UserService userService) {
        this.userRoleService = userRoleService;
        this.itemService = itemService;

        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        userRoleService.initRoles();
        itemService.initItems();
        userService.initAdmin();
    }
}
