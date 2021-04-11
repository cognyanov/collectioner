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
    private int aluminium;
    private int steel;
    private int steaks;
    private int rawSteaks;
    private int energyPotions;
    private int energyToRestore;
    private int daysWorked;
    private int daysTrained;

    @ManyToOne
    private ItemEntity weapon;
    @ManyToOne
    private ItemEntity shield;

    public void updateStats() {
        this.HP = this.baseHP + this.weapon.getHP() + this.shield.getHP();
        this.attack = this.baseAttack + this.weapon.getATK() + this.shield.getATK();
        this.defense = this.baseDefense + this.weapon.getDEF() + this.shield.getDEF();
    }

    public void hit(Monster monster) {
        int attack = Math.max(0, this.attack - monster.getDEF());
        monster.setHP(monster.getHP() - attack);
    }


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

    public int getAluminium() {
        return aluminium;
    }

    public void setAluminium(int aluminium) {
        this.aluminium = aluminium;
    }

    public int getSteel() {
        return steel;
    }

    public void setSteel(int steel) {
        this.steel = steel;
    }

    public int getSteaks() {
        return steaks;
    }

    public void setSteaks(int steaks) {
        this.steaks = steaks;
    }

    public int getRawSteaks() {
        return rawSteaks;
    }

    public void setRawSteaks(int rawSteaks) {
        this.rawSteaks = rawSteaks;
    }

    public int getEnergyPotions() {
        return energyPotions;
    }

    public void setEnergyPotions(int energyPotions) {
        this.energyPotions = energyPotions;
    }

    public int getEnergyToRestore() {
        return energyToRestore;
    }

    public void setEnergyToRestore(int energyToRestore) {
        this.energyToRestore = energyToRestore;
    }

    public int getDaysWorked() {
        return daysWorked;
    }

    public void setDaysWorked(int daysWorked) {
        this.daysWorked = daysWorked;
    }

    public int getDaysTrained() {
        return daysTrained;
    }

    public void setDaysTrained(int daysTrained) {
        this.daysTrained = daysTrained;
    }
}
