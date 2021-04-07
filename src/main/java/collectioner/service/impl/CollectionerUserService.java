package collectioner.service.impl;

import collectioner.repository.HeroRepository;
import collectioner.service.HeroService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class CollectionerUserService implements UserDetailsService {

    private final HeroService heroService;
    private final HeroRepository heroRepository;

    public CollectionerUserService(HeroService heroService, HeroRepository heroRepository) {
        this.heroService = heroService;
        this.heroRepository = heroRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }

    @Scheduled(fixedDelay = 10000, initialDelay = 10000)
    public void scheduleUpdateEnergyToAll() {

        heroRepository.findAll().forEach(h -> {
            if (h.getEnergyToRestore() < 10) {
                h.setEnergyToRestore(h.getEnergyToRestore() + 1);
                heroRepository.save(h);
            }
        });
    }

    @Scheduled(fixedDelay = 86400000, initialDelay = 86400000)
    public void updateDay() {
        heroRepository.findAll().forEach(h -> {
            if (h.isHasWorked()) {
                h.setHasWorked(false);
                heroRepository.save(h);
            }
        });
    }

}
