package ua.kiev.prog;


import javax.persistence.*;

@Entity
@Table(name = "Menu")
public class Menu {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String name;
    private int weight;
    private int price;
    private boolean discount;

    public Menu() {
    }

    public Menu(String name, int price, int weight,  boolean discount) {
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.discount = discount;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isDiscount() {
        return discount;
    }

    public void setDiscount(boolean discount) {
        this.discount = discount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "weight=" + weight +
                ", price=" + price +
                ", discount=" + discount +
                '}';
    }
}
