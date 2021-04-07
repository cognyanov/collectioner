package collectioner;

import collectioner.service.ItemService;
import collectioner.service.UserRoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class DBInit implements CommandLineRunner {
    private final UserRoleService userRoleService;
    private final ItemService itemService;


    public DBInit(UserRoleService userRoleService, ItemService itemService) {
        this.userRoleService = userRoleService;
        this.itemService = itemService;

    }

    @Override
    public void run(String... args) throws Exception {
        userRoleService.initRoles();
        itemService.initItems();
    }
}
