package collectioner.web;

import collectioner.model.binding.UserLoginBindingModel;
import collectioner.model.binding.UserRegistrationBindingModel;
import collectioner.model.service.UserLoginServiceModel;
import collectioner.model.service.UserRegistrationServiceModel;
import collectioner.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.security.auth.login.LoginException;
import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    private final ModelMapper modelMapper;
    private final UserService userService;

    public UserController(ModelMapper modelMapper, UserService userService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @ModelAttribute("registrationBindingModel")
    public UserRegistrationBindingModel createBindingModel() {
        return new UserRegistrationBindingModel();
    }

    @ModelAttribute("loginBindingModel")
    public UserLoginBindingModel createLoginBindingModel() {
        return new UserLoginBindingModel();
    }

    @ModelAttribute("userExistsError")
    public boolean userExists() {
        return false;
    }
    @ModelAttribute("diffPasswords")
    public boolean diffPasswords() {
        return false;
    }
    @ModelAttribute("userNotFound")
    public boolean userNotFound() {
        return false;
    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String registerPost(
            @Valid UserRegistrationBindingModel registrationBindingModel,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("registrationBindingModel", registrationBindingModel);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.registrationBindingModel", bindingResult);
            return "redirect:/users/register";
        }

        if (userService.usernameExists(registrationBindingModel.getUsername())) {
            redirectAttributes.addFlashAttribute("registrationBindingModel", registrationBindingModel);
            redirectAttributes.addFlashAttribute("userExistsError", true);

            return "redirect:/users/register";
        }

        if (!registrationBindingModel.getPassword().equals(registrationBindingModel.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("registrationBindingModel", registrationBindingModel);
            redirectAttributes.addFlashAttribute("diffPasswords", true);
            return "redirect:/users/register";
        }


        UserRegistrationServiceModel userServiceModel = modelMapper
                .map(registrationBindingModel, UserRegistrationServiceModel.class);

        userService.registerUser(userServiceModel);

        return "login";
    }

    @PostMapping("/login")
    public String loginPost(UserLoginBindingModel loginBindingModel,
                            RedirectAttributes redirectAttributes) {

        UserLoginServiceModel userServiceModel = modelMapper
                .map(loginBindingModel, UserLoginServiceModel.class);


            try {
                userService.loginUser(userServiceModel);

            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("userNotFound", true);
                return "redirect:/users/login";
            }


        return "redirect:/home";
    }

    @GetMapping("/users/login-error")
    public String loginFail() {
        return "redirect:/users/register";
    }

}
