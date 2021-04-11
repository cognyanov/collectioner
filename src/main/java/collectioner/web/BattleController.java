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
public class BattleController {

    private final HeroService heroService;

    public BattleController(HeroService heroService) {
        this.heroService = heroService;
    }

    @ModelAttribute("wonBattle")
    public boolean wonBattle() {
        return false;
    }

    @ModelAttribute("wonBattle1")
    public boolean wonBattle1() {
        return false;
    }

    @ModelAttribute("wonBattle2")
    public boolean wonBattle2() {
        return false;
    }

    @ModelAttribute("wonBattle3")
    public boolean wonBattle3() {
        return false;
    }

    @ModelAttribute("lostBattle")
    public boolean lostBattle() {
        return false;
    }

    @ModelAttribute("notEnoughEnergy")
    public boolean notEnoughEnergy() {
        return false;
    }

    @ModelAttribute("aluminiumWon")
    public int aluminiumWon() {
        return 0;
    }

    @ModelAttribute("steelWon")
    public int steelWon() {
        return 0;
    }

    @ModelAttribute("lowToEnterVoid")
    public boolean lowToEnterVoid() {
        return heroService.getCurrentHero().getAttack() < 30;
    }

    @ModelAttribute("lowToEnterNether")
    public boolean lowToEnterNether() {
        return heroService.getCurrentHero().getAttack() < 100;
    }

    @ModelAttribute("lowToEnterFire")
    public boolean lowToEnterFire() {
        return heroService.getCurrentHero().getAttack() < 300;
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

    @ModelAttribute("canRestoreEnergy")
    public boolean canRestoreEnergy() {
        if (heroService.getCurrentHero() != null) {

            if (heroService.getCurrentHero().getEnergyToRestore() < 1 || heroService.getCurrentHero().getSteaks() < 1 || heroService.getCurrentHero().getEnergy() > 9) {
                return false;
            }
        }
        return true;
    }


    @GetMapping("/fight")
    public String fight() {

        return "fight";
    }

    public String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    @GetMapping("/attack/void")
    public String attackVoid(RedirectAttributes redirectAttributes) {
        if (lowToEnterVoid()) {
            return "/fight";
        }

        if (heroService.getCurrentHero().getEnergy() < 1) {
            redirectAttributes.addFlashAttribute("notEnoughEnergy", true);
            return "redirect:/fight";
        } else if (heroService.getCurrentHero().getAttack() < 30) {
            redirectAttributes.addFlashAttribute("lowToEnter", true);
            return "redirect:/fight";
        }


        if (heroService.attackVoid()) {
            int[] matsWon = heroService.wonVoid();
            if (matsWon[0] > 0) {
                redirectAttributes.addFlashAttribute("aluminiumWon", matsWon[0]);
            } else {
                redirectAttributes.addFlashAttribute("steelWon", matsWon[1]);
            }
            redirectAttributes.addFlashAttribute("wonBattle", true);
            redirectAttributes.addFlashAttribute("wonBattle1", true);
        } else {
            redirectAttributes.addFlashAttribute("lostBattle", true);
        }

        return "redirect:/fight";
    }


    @GetMapping("/attack/nether")
    public String attackNether(RedirectAttributes redirectAttributes) {
        if (lowToEnterNether()) {
            return "redirect:/fight";
        }

        if (heroService.getCurrentHero().getEnergy() < 2) {
            redirectAttributes.addFlashAttribute("notEnoughEnergy", true);
            return "redirect:/fight";
        }

        if (heroService.attackNether()) {
            int[] matsWon = heroService.wonNether();
            if (matsWon[0] > 0) {
                redirectAttributes.addFlashAttribute("aluminiumWon", matsWon[0]);
            } else {
                redirectAttributes.addFlashAttribute("steelWon", matsWon[1]);
            }
            redirectAttributes.addFlashAttribute("wonBattle", true);
            redirectAttributes.addFlashAttribute("wonBattle2", true);
        } else {
            redirectAttributes.addFlashAttribute("lostBattle", true);
        }

        return "redirect:/fight";
    }

    @GetMapping("/attack/fire")
    public String attackFire(RedirectAttributes redirectAttributes) {
        if (lowToEnterFire()) {
            return "redirect:/fight";
        }

        if (heroService.getCurrentHero().getEnergy() < 5) {
            redirectAttributes.addFlashAttribute("notEnoughEnergy", true);
            return "redirect:/fight";
        }

        if (heroService.attackFire()) {
            int[] matsWon = heroService.wonFire();
            if (matsWon[0] > 0) {
                redirectAttributes.addFlashAttribute("aluminiumWon", matsWon[0]);
            } else {
                redirectAttributes.addFlashAttribute("steelWon", matsWon[1]);
            }
            redirectAttributes.addFlashAttribute("wonBattle", true);
            redirectAttributes.addFlashAttribute("wonBattle3", true);
        } else {
            redirectAttributes.addFlashAttribute("lostBattle", true);
        }

        return "redirect:/fight";
    }

    @GetMapping("closeWonPopup")
    public String closeWonPopup() {
        return "redirect:/fight";
    }

}
