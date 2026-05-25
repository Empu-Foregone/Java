package com.example;

import java.util.Objects;

/**
 * Клас, що представляє одяг.
 * Має поля: тип, розмір (enum), ціна, бренд, матеріал (enum).
 */
public class Clothes {
    private static int totalCreated = 0;

    private String type;
    private Size size;
    private double price;
    private String brand;
    private Material material;

    /**
     * Основний конструктор з валідацією.
     * @param type тип одягу (не може бути порожнім)
     * @param size розмір (не може бути null)
     * @param price ціна (має бути > 0)
     * @param brand бренд (не може бути порожнім)
     * @param material матеріал (не може бути null)
     */
    public Clothes(String type, Size size, double price, String brand, Material material) {
        setType(type);
        setSize(size);
        setPrice(price);
        setBrand(brand);
        setMaterial(material);
        totalCreated++;
    }

    /**
     * Конструктор копіювання.
     * @param other інший об'єкт Clothes
     */
    public Clothes(Clothes other) {
        this(other.type, other.size, other.price, other.brand, other.material);
    }

    public String getType() { return type; }
    public Size getSize() { return size; }
    public double getPrice() { return price; }
    public String getBrand() { return brand; }
    public Material getMaterial() { return material; }

    public static int getTotalCreated() { return totalCreated; }

    public void setType(String type) {
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Тип одягу не може бути порожнім");
        }
        this.type = type.trim();
    }

    public void setSize(Size size) {
        if (size == null) {
            throw new IllegalArgumentException("Розмір не може бути null");
        }
        this.size = size;
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

    public void setMaterial(Material material) {
        if (material == null) {
            throw new IllegalArgumentException("Матеріал не може бути null");
        }
        this.material = material;
    }

    @Override
    public String toString() {
        return String.format("Одяг: %s, бренд: %s, розмір: %s (%s), матеріал: %s, ціна: %.2f грн",
                type, brand, size.name(), size.getDescription(), material.getUkrainianName(), price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clothes clothes = (Clothes) o;
        return Double.compare(clothes.price, price) == 0 &&
               Objects.equals(type, clothes.type) &&
               size == clothes.size &&
               Objects.equals(brand, clothes.brand) &&
               material == clothes.material;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, size, price, brand, material);
    }
}