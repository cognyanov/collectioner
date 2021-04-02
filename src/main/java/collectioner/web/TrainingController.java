package collectioner.web;

import collectioner.model.entity.HeroEntity;
import collectioner.service.HeroService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TrainingController {

    private final HeroService heroService;

    public TrainingController(HeroService heroService) {
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

    @ModelAttribute("successfullyWorkedOut")
    public boolean successfullyWorked() {
        return false;
    }

    @ModelAttribute("notEnoughEnergy")
    public boolean notEnoughEnergy() {
        return false;
    }

    @GetMapping("/training-ground")
    public String trainingPage() {

        return "trainingGround";
    }

    @GetMapping("/training-ground/trainNow")
    public String trainNow(RedirectAttributes redirectAttributes) {
        if (heroService.train()) {
            redirectAttributes.addFlashAttribute("successfullyWorkedOut", true);
        } else {
            redirectAttributes.addFlashAttribute("notEnoughEnergy", true);
        }

        return "redirect:/training-ground";
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
