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
public class StorageController {

    private final HeroService heroService;

    public StorageController(HeroService heroService) {
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

    @ModelAttribute("totalStockStorage")
    public int totalStockStorage() {
        HeroEntity hero = heroService.getCurrentHero();

        return hero.getSteaks() + hero.getEnergyPotions() + hero.getAluminium() + hero.getSteel() + hero.getRawSteaks() + 2;

    }
    @ModelAttribute("preparedSteaks")
    public int preparedSteaks() {
        return 0;
    }
    @ModelAttribute("notRawMeat")
    public boolean notRawMeat() {
        return false;
    }

    @ModelAttribute("notEnoughGold")
    public boolean notEnoughGold() {
        return false;
    }

    @ModelAttribute("boughtPotions")
    public boolean boughtPotions() {
        return false;
    }





    @ModelAttribute("hero")
    public HeroEntity getHero() {
        try {
            return heroService.getCurrentHero();
        } catch (Exception e) {
            return null;
        }
    }

    @ModelAttribute("canRestoreEnergy")
    public boolean canRestoreEnergy() {
        return heroService.getCurrentHero().getEnergyToRestore() >= 1 && heroService.getCurrentHero().getSteaks() >= 1 && heroService.getCurrentHero().getEnergy() <= 9;
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
    @GetMapping("/storage/bake")
    public String bakeSteaks(RedirectAttributes redirectAttributes) {
        if (heroService.getCurrentHero().getRawSteaks() < 1) {
            redirectAttributes.addFlashAttribute("notRawMeat", true);
            return "redirect:/storage";
        }

        int steaks = heroService.bake();
        redirectAttributes.addFlashAttribute("preparedSteaks", steaks);
        return "redirect:/storage";
    }

    @GetMapping("buy/energyPotions/confirm")
    public String buyEnergyPotions(RedirectAttributes redirectAttributes) {
        if (heroService.getCurrentHero().getGold() < 30) {
            redirectAttributes.addFlashAttribute("notEnoughGold", true);
            return "redirect:/storage";
        }

        heroService.buy10EnergyPotions();
        redirectAttributes.addFlashAttribute("boughtPotions", true);

        return "redirect:/storage";
    }

}
