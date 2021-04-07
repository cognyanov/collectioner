package collectioner.web;

import collectioner.model.entity.HeroEntity;
import collectioner.service.HeroService;
import org.apache.catalina.filters.ExpiresFilter;
import org.apache.coyote.Request;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.print.attribute.Attribute;
import javax.servlet.http.HttpSession;
import javax.swing.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

@Controller

public class HomeController {

    private final HeroService heroService;

    public HomeController(HeroService heroService) {
        this.heroService = heroService;
    }

    @ModelAttribute("getUsername")
    public String getUsername() {
        StringBuilder name = new StringBuilder();
        String oldName = getCurrentUsername();
        name.append(oldName.substring(0, 1).toUpperCase());
        name.append(oldName.substring(1));
        return name.toString();

    }

    @ModelAttribute("hero")
    public HeroEntity getHero() {
        try {
            return heroService.getCurrentHero();
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/energy")
    public String energy() {
        return heroService.getCurrentHero().getEnergy() + "";
    }


    @GetMapping("/")
    public String index() {

        return "index";
    }

    @GetMapping("/home")
    public String home() {

        return "home";
    }

    @GetMapping("/restoreEnergy")
    public String restoreEnergy() {
        heroService.restoreEnergy();

        return "redirect:/home";
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