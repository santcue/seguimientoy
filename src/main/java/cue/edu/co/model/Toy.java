package cue.edu.co.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Toy implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String name;
    private char type;
    private double price;
    private int quantity;

    public Toy(String name, char type, double price, int quantity) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (type != 'O' && type != '1' && type != '2') {
            throw new IllegalArgumentException("Invalid toy type");
        }
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }

        this.name = name;
        this.type = type;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        if (type != 'O' && type != '1' && type != '2') {
            throw new IllegalArgumentException("Invalid toy type");
        }
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero");
        }
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
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
