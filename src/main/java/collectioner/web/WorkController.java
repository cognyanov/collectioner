package collectioner.web;

import collectioner.model.entity.HeroEntity;
import collectioner.service.HeroService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class WorkController {

    private final HeroService heroService;

    public WorkController(HeroService heroService) {
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
    @ModelAttribute("hasWorked")
    public boolean hasWorked() {
        return heroService.getCurrentHero().isHasWorked();
    }

    @ModelAttribute("successfullyWorked")
    public boolean successfullyWorked() {
        return false;
    }

    @ModelAttribute("notEnoughEnergy")
    public boolean notEnoughEnergy() {
        return false;
    }

    @GetMapping("/work")
    public String workPage() {

        return "work";
    }

    @GetMapping("/work/workNow")
    public String workNow(RedirectAttributes redirectAttributes) {
        if (heroService.work()) {
            redirectAttributes.addFlashAttribute("successfullyWorked", true);
        } else {
            redirectAttributes.addFlashAttribute("notEnoughEnergy", true);
        }

        return "redirect:/work";
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
