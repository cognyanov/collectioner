package collectioner.service;

import collectioner.model.entity.HeroEntity;

public interface HeroService {

    HeroEntity getCurrentHero();

    boolean work();

    boolean train();

    boolean upgradeWeapon();
    boolean upgradeShield();

    void restoreEnergy();

    void usePotion();

    boolean attackVoid();
    boolean attackNether();
    boolean attackFire();

    int[] wonVoid();
    int[] wonNether();
    int[] wonFire();

    int bake();


    void buy10EnergyPotions();
}
