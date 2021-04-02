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
public class ItemsUpgradeController {

    private final HeroService heroService;

    public ItemsUpgradeController(HeroService heroService) {
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


    @ModelAttribute("successfullyUpgradedWeapon")
    public boolean successfullyUpgradedWeapon() {
        return false;
    }


    @ModelAttribute("successfullyUpgradedShield")
    public boolean successfullyUpgradedShield() {
        return false;
    }

    @ModelAttribute("notEnoughGold")
    public boolean notEnoughGold() {
        return false;
    }

    @ModelAttribute("maxedWeapon")
    public boolean maxedWeapon() {
        if (heroService.getCurrentHero().getWeapon().getId() == 3L) {
            return true;
        }
        return false;
    }
    @ModelAttribute("maxedShield")
    public boolean maxedShield() {
        if (heroService.getCurrentHero().getShield().getId() == 6L) {
            return true;
        }
        return false;
    }


    @GetMapping("/items-upgrade")
    public String itemsUpgradePage() {
        return "items-upgrade";
    }

    @GetMapping("/items-upgrade/upgrade-weapon")
    public String upgradeWeapon(RedirectAttributes redirectAttributes) {

            if (heroService.upgradeWeapon()) {
                redirectAttributes.addFlashAttribute("successfullyUpgradedWeapon", true);
            } else {
                redirectAttributes.addFlashAttribute("notEnoughGold", true);
            }
            return "redirect:/items-upgrade";

    }

    @GetMapping("/items-upgrade/upgrade-shield")
    public String upgradeShield(RedirectAttributes redirectAttributes) {

           if (heroService.upgradeShield()) {
               redirectAttributes.addFlashAttribute("successfullyUpgradedShield", true);
           } else {
               redirectAttributes.addFlashAttribute("notEnoughGold", true);
           }
           return "redirect:/items-upgrade";

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
