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
        StringBuilder name = new StringBuilder();
        String oldName = getCurrentUsername();
        name.append(oldName.substring(0, 1).toUpperCase());
        name.append(oldName.substring(1).toLowerCase());
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

    @ModelAttribute("successfullyWorkedOut")
    public boolean successfullyWorked() {
        return false;
    }

    @ModelAttribute("notEnoughEnergy")
    public boolean notEnoughEnergy() {
        return false;
    }

    @ModelAttribute("canRestoreEnergy")
    public boolean canRestoreEnergy() {
        return heroService.getCurrentHero().getEnergyToRestore() >= 1 && heroService.getCurrentHero().getSteaks() >= 1 && heroService.getCurrentHero().getEnergy() <= 9;
    }
    @ModelAttribute("hasTrained")
    public boolean hasTrained() {
        return heroService.getCurrentHero().isHasTrained();
    }

    @ModelAttribute("bonusTrain")
    public boolean bonusTrain() {
        return false;
    }


    @GetMapping("/training-ground")
    public String trainingPage() {
        return "training-ground";
    }

    @GetMapping("/training-ground/trainNow")
    public String trainNow(RedirectAttributes redirectAttributes) {
        if (!heroService.getCurrentHero().isHasTrained()) {

            if (heroService.train()) {
                if (heroService.getCurrentHero().getDaysTrained() == 5) {
                    redirectAttributes.addFlashAttribute("bonusTrain", true);
                }
                redirectAttributes.addFlashAttribute("successfullyWorkedOut", true);
            } else {
                redirectAttributes.addFlashAttribute("notEnoughEnergy", true);
            }

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
