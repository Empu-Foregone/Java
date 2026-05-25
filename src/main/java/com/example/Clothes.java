package com.example;

import java.util.Objects;
import java.util.UUID;

public abstract class Clothes implements Comparable<Clothes>, Identifiable {
    protected String type;
    protected Size size;
    protected double price;
    protected String brand;
    protected Material material;
    protected UUID uuid;

    public Clothes(String type, Size size, double price, String brand, Material material) {
        this.uuid = UUID.randomUUID();
        setType(type);
        setSize(size);
        setPrice(price);
        setBrand(brand);
        setMaterial(material);
    }

    public Clothes(Clothes other) {
        this.uuid = UUID.randomUUID();
        this.type = other.type;
        this.size = other.size;
        this.price = other.price;
        this.brand = other.brand;
        this.material = other.material;
    }

    public String getType() { return type; }
    public Size getSize() { return size; }
    public double getPrice() { return price; }
    public String getBrand() { return brand; }
    public Material getMaterial() { return material; }
    public UUID getUuid() { return uuid; }

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

    public String getClassType() {
        return this.getClass().getSimpleName();
    }

    public String getShortInfo() {
        return type + " (" + brand + ") | UUID: " + uuid.toString().substring(0, 8) + "...";
    }

    @Override
    public int compareTo(Clothes other) {
        return this.type.compareToIgnoreCase(other.type);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s, бренд: %s, розмір: %s, матеріал: %s, ціна: %.2f грн, UUID: %s",
                this.getClass().getSimpleName(), type, brand, size.name(), material.getUkrainianName(), price, uuid.toString());
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