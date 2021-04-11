package collectioner.web;

import collectioner.model.entity.HeroEntity;
import collectioner.service.HeroService;
import collectioner.service.impl.WorkServiceInterceptor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class WorkController {

    private final HeroService heroService;


    public WorkController(HeroService heroService) {
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

    @ModelAttribute("bonusWork")
    public boolean bonusWork() {
        return false;
    }

    @ModelAttribute("canRestoreEnergy")
    public boolean canRestoreEnergy() {
        return heroService.getCurrentHero().getEnergyToRestore() >= 1 && heroService.getCurrentHero().getSteaks() >= 1 && heroService.getCurrentHero().getEnergy() <= 9;
    }

    @GetMapping("/work")
    public String workPage() {
        return "work";
    }

    @GetMapping("/work/workNow")
    public String workNow(RedirectAttributes redirectAttributes) {
        if (!heroService.getCurrentHero().isHasWorked()) {


            if (heroService.work()) {
                if (heroService.getCurrentHero().getDaysWorked() == 7) {
                redirectAttributes.addFlashAttribute("bonusWork", true);
            }
                redirectAttributes.addFlashAttribute("successfullyWorked", true);
            } else {
                redirectAttributes.addFlashAttribute("notEnoughEnergy", true);
            }

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
