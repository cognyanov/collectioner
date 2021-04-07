package collectioner.web;

import collectioner.model.entity.HeroEntity;
import collectioner.service.HeroService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class StorageController {

    private final HeroService heroService;

    public StorageController(HeroService heroService) {
        this.heroService = heroService;
    }

    @ModelAttribute("getUsername")
    public String getUsername() {
        return getCurrentUsername();

    }

    @ModelAttribute("hero")
    public HeroEntity getHero() {
        try {
            return heroService.getCurrentHero();
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/storage")
    public String storage() {
        return "storage";
    }

    public String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    @GetMapping("/restoreFullEnergy")
    public String restoreFullEnergy() {
        heroService.usePotion();
        return "redirect:/storage";
    }
}
