package collectioner.web;

import collectioner.model.entity.HeroEntity;
import collectioner.model.entity.UserEntity;
import collectioner.service.HeroService;
import collectioner.service.RankingsService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;


@Controller
public class RankingsController {
    private final HeroService heroService;
    private final RankingsService rankingsService;

    public RankingsController(HeroService heroService, RankingsService rankingsService) {
        this.heroService = heroService;
        this.rankingsService = rankingsService;
    }

    @ModelAttribute("top10ByAtk")
    public List<UserEntity> getTop10ByAttack(){
        return rankingsService.top10ByAttack();

    }
    @ModelAttribute("top10ByDef")
    public List<UserEntity> getTop10ByDefense(){
        return rankingsService.top10ByDef();
    }
    @ModelAttribute("top10ByHP")
    public List<UserEntity> getTop10ByHP(){
        return rankingsService.top10ByHP();
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

    @GetMapping("/rankings")
    public String rankings() {
        return "rankings";
    }
    @GetMapping("/rankings/byAttack")
    public String rankingByAttack() {
        return "rankByAttack";
    }
    @GetMapping("/rankings/byDefense")
    public String rankingByDefense() {
        return "rankByDefense";
    }
    @GetMapping("/rankings/byHP")
    public String rankingByHP() {
        return "rankByHP";
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
