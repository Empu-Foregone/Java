package com.example;

import java.util.Objects;

public class Clothes {
    private String type;
    private String size;
    private double price;

    public Clothes(String type, String size, double price) {
        this.type = type;
        this.size = size;
        this.price = price;
    }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    @Override
    public String toString() {
        return "Одяг: " + type + ", розмір: " + size + ", ціна: " + price + " грн";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clothes clothes = (Clothes) o;
        return Double.compare(clothes.price, price) == 0 &&
               Objects.equals(type, clothes.type) &&
               Objects.equals(size, clothes.size);
    }
}