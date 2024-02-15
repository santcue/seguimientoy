package cue.edu.co.model;

import java.io.Serializable;
import java.util.Objects;

public class Toy implements Serializable {
    private String name;
    private char type;
    private double price;
    private int quantity;

    public Toy(String name, char type, double price, int quantity) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Type: " + type + ", Price: $" + price + ", Quantity: " + quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Toy toy = (Toy) o;
        return Objects.equals(name, toy.name) && type == toy.type && Double.compare(toy.price, price) == 0 && quantity == toy.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, price, quantity);
    }
}
