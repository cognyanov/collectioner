package collectioner.service;

import collectioner.model.entity.HeroEntity;

public interface HeroService {

    HeroEntity getCurrentHero();

    boolean work();

    boolean train();

    boolean upgradeWeapon();
    boolean upgradeShield();
}
