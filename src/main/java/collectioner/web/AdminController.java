package collectioner.web;

import collectioner.model.binding.UserDemoteRoleBindingModel;
import collectioner.model.binding.UserRolesBindingModel;
import collectioner.model.service.UserRolesServiceModel;
import collectioner.service.UserRoleService;
import collectioner.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class AdminController {

    private final UserRoleService userRoleService;
    private final ModelMapper modelMapper;
    private final UserService userService;

    public AdminController(UserRoleService userRoleService, ModelMapper modelMapper, UserService userService) {
        this.userRoleService = userRoleService;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @ModelAttribute("hasHigherRole")
    public boolean hasHigherRole() {
        return false;
    }

    @ModelAttribute("usernameNotFound")
    public boolean usernameNotFound() {
        return false;
    }

    @ModelAttribute("invalidUsername")
    public boolean invalidUsername() {
        return false;
    }

    @ModelAttribute("invalidDemote")
    public boolean invalidDemote() {
        return false;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public String test() {
        return "admin";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/admin/updateUserRoles")
    public String updateUserRoles(@Valid UserRolesBindingModel userRolesBindingModel, BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("invalidUsername", true);
            return "redirect:/admin";
        }

        if (!userService.usernameExists(userRolesBindingModel.username)) {
            redirectAttributes.addFlashAttribute("usernameNotFound", true);
            return "redirect:/admin";
        }

        UserRolesServiceModel userRolesServiceModel = modelMapper.map(userRolesBindingModel, UserRolesServiceModel.class);
        if (!userRoleService.addRoleToUser(userRolesServiceModel)) {
            redirectAttributes.addFlashAttribute("hasHigherRole", true);
            return "redirect:/admin";
        }

        return "redirect:/home";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/admin/demoteUserRole")
    public String demoteUserRole(@Valid UserDemoteRoleBindingModel userDemoteRoleBindingModel, BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("invalidUsername", true);
            return "redirect:/admin";
        }

        if (!userService.usernameExists(userDemoteRoleBindingModel.usernameDemote)) {
            redirectAttributes.addFlashAttribute("usernameNotFound", true);
            return "redirect:/admin";
        }

        UserRolesServiceModel userRolesServiceModel = modelMapper.map(userDemoteRoleBindingModel, UserRolesServiceModel.class);
        if (!userRoleService.demoteUserRole(userRolesServiceModel)) {
            redirectAttributes.addFlashAttribute("invalidDemote", true);
            return "redirect:/admin";
        }

        return "redirect:/home";

    }
}
