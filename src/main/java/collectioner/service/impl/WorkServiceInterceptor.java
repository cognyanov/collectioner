package collectioner.service.impl;

import collectioner.model.entity.HeroEntity;
import collectioner.repository.HeroRepository;
import collectioner.service.HeroService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class WorkServiceInterceptor implements HandlerInterceptor {
    private final HeroService heroService;
    private final HeroRepository heroRepository;

    public WorkServiceInterceptor(HeroService heroService, HeroRepository heroRepository) {
        this.heroService = heroService;
        this.heroRepository = heroRepository;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception exception) throws Exception {
        HeroEntity hero = heroService.getCurrentHero();
        if (hero.getDaysWorked() == 7) {
            hero.setDaysWorked(0);
            hero.setGold(hero.getGold() + 5);
        }
        heroRepository.save(hero);
    }

}