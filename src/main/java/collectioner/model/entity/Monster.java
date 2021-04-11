package collectioner.model.entity;

import javax.persistence.MappedSuperclass;


public abstract class Monster {
    public int HP;
    public int ATK;
    public int DEF;

    public Monster() {
    }

    public void hit(HeroEntity hero) {
        int attack = Math.max(0, this.ATK - hero.getDefense());
        hero.setHP(hero.getHP() - attack);
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getATK() {
        return ATK;
    }

    public void setATK(int ATK) {
        this.ATK = ATK;
    }

    public int getDEF() {
        return DEF;
    }

    public void setDEF(int DEF) {
        this.DEF = DEF;
    }

}
