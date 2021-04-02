package collectioner.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "heroes")
public class HeroEntity extends BaseEntity{
    private int HP;
    private int attack;
    private int defense;
    private int energy;
    private int baseHP;
    private int baseAttack;
    private int baseDefense;
    private int gold;
    private boolean hasWorked = false;
    private boolean hasTrained = false;

    @ManyToOne
    private ItemEntity weapon;
    @ManyToOne
    private ItemEntity shield;


    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getBaseHP() {
        return baseHP;
    }

    public void setBaseHP(int baseHP) {
        this.baseHP = baseHP;
    }

    public int getBaseDefense() {
        return baseDefense;
    }

    public void setBaseDefense(int baseDefense) {
        this.baseDefense = baseDefense;
    }

    public int getBaseAttack() {
        return baseAttack;
    }

    public void setBaseAttack(int baseAttack) {
        this.baseAttack = baseAttack;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public ItemEntity getWeapon() {
        return weapon;
    }

    public void setWeapon(ItemEntity weapon) {
        this.weapon = weapon;
    }

    public ItemEntity getShield() {
        return shield;
    }

    public void setShield(ItemEntity shield) {
        this.shield = shield;
    }

    public boolean isHasWorked() {
        return hasWorked;
    }

    public void setHasWorked(boolean hasWorked) {
        this.hasWorked = hasWorked;
    }

    public boolean isHasTrained() {
        return hasTrained;
    }

    public void setHasTrained(boolean hasTrained) {
        this.hasTrained = hasTrained;
    }

    public void updateStats() {
        this.HP = this.baseHP + this.weapon.getHP() + this.shield.getHP();
        this.attack = this.baseAttack + this.weapon.getATK() + this.shield.getATK();
        this.defense = this.baseDefense + this.weapon.getDEF() + this.shield.getDEF();
    }
}
