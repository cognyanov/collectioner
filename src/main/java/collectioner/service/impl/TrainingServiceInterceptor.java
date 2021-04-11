package collectioner.service.impl;

import collectioner.model.entity.HeroEntity;
import collectioner.repository.HeroRepository;
import collectioner.service.HeroService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TrainingServiceInterceptor implements HandlerInterceptor {

    private final HeroService heroService;
    private final HeroRepository heroRepository;

    public TrainingServiceInterceptor(HeroService heroService, HeroRepository heroRepository) {
        this.heroService = heroService;
        this.heroRepository = heroRepository;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception exception) {
        HeroEntity hero = heroService.getCurrentHero();

        if (hero.getDaysTrained() == 5) {
            hero.setDaysTrained(0);
            hero.setBaseAttack(hero.getBaseAttack() + 4);
            hero.setBaseDefense(hero.getBaseDefense() + 2);
            hero.updateStats();

            heroRepository.save(hero);
        }
    }
}
