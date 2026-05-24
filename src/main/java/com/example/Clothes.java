package com.example;

import java.util.Objects;

/**
 * Клас, що представляє одяг.
 * Має 4 поля: тип, розмір, ціна, бренд.
 */
public class Clothes {
    private String type;      // тип одягу (футболка, джинси, куртка)
    private String size;      // розмір (S, M, L, XL)
    private double price;     // ціна (повинна бути > 0)
    private String brand;     // бренд (не може бути порожнім)

    /**
     * Конструктор з валідацією всіх полів.
     * @param type тип одягу (не може бути null або порожнім)
     * @param size розмір (не може бути null або порожнім)
     * @param price ціна (має бути > 0)
     * @param brand бренд (не може бути null або порожнім)
     * @throws IllegalArgumentException якщо будь-яке поле некоректне
     */
    public Clothes(String type, String size, double price, String brand) {
        setType(type);
        setSize(size);
        setPrice(price);
        setBrand(brand);
    }

    public String getType() { return type; }
    public String getSize() { return size; }
    public double getPrice() { return price; }
    public String getBrand() { return brand; }

    public void setType(String type) {
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Тип одягу не може бути порожнім");
        }
        this.type = type.trim();
    }

    public void setSize(String size) {
        if (size == null || size.trim().isEmpty()) {
            throw new IllegalArgumentException("Розмір не може бути порожнім");
        }
        String upperSize = size.trim().toUpperCase();
        if (!upperSize.matches("S|M|L|XL")) {
            throw new IllegalArgumentException("Розмір має бути S, M, L або XL");
        }
        this.size = upperSize;
    }

    public void setPrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Ціна має бути більшою за 0");
        }
        this.price = price;
    }

    public void setBrand(String brand) {
        if (brand == null || brand.trim().isEmpty()) {
            throw new IllegalArgumentException("Бренд не може бути порожнім");
        }
        this.brand = brand.trim();
    }

    @Override
    public String toString() {
        return String.format("Одяг: %s, бренд: %s, розмір: %s, ціна: %.2f грн",
                type, brand, size, price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clothes clothes = (Clothes) o;
        return Double.compare(clothes.price, price) == 0 &&
               Objects.equals(type, clothes.type) &&
               Objects.equals(size, clothes.size) &&
               Objects.equals(brand, clothes.brand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, size, price, brand);
    }
}