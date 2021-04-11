package collectioner.model.entity;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

@Entity
@Table(name = "items")
public class ItemEntity extends BaseEntity {
    private String name;
    private int HP;
    private int ATK;
    private int DEF;
    private int price;
    private int priceAluminium;

    public ItemEntity() {
    }

    public ItemEntity(String name, int HP, int ATK, int DEF, int price, int priceAluminium) {
        this.name = name;
        this.HP = HP;
        this.ATK = ATK;
        this.DEF = DEF;
        this.price = price;
        this.priceAluminium = priceAluminium;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPriceAluminium() {
        return priceAluminium;
    }

    public void setPriceAluminium(int priceAluminium) {
        this.priceAluminium = priceAluminium;
    }
}
