package com.example;

import java.util.Objects;

/**
 * Клас-обгортка для зберігання об'єкта Clothes разом з його кількістю.
 */
public class StoreItem {
    private Clothes clothes;
    private int quantity;

    public StoreItem(Clothes clothes, int quantity) {
        setClothes(clothes);
        setQuantity(quantity);
    }

    public Clothes getClothes() {
        return clothes;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setClothes(Clothes clothes) {
        if (clothes == null) {
            throw new IllegalArgumentException("Одяг не може бути null");
        }
        this.clothes = clothes;
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Кількість має бути більшою за 0");
        }
        this.quantity = quantity;
    }

    public void addQuantity(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Кількість для додавання має бути більшою за 0");
        }
        this.quantity += amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoreItem that = (StoreItem) o;
        return Objects.equals(clothes, that.clothes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clothes);
    }

    @Override
    public String toString() {
        return clothes.toString() + " [Кількість: " + quantity + " шт.]";
    }
}