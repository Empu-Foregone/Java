package com.example;

/**
 * Похідний клас для курток.
 * Додає поля: наявність капюшона, водонепроникність.
 */
public class Jacket extends Clothes {
    private boolean hasHood;
    private boolean waterproof;

    public Jacket(String type, Size size, double price, String brand, Material material,
                  boolean hasHood, boolean waterproof) {
        super(type, size, price, brand, material);
        setHasHood(hasHood);
        setWaterproof(waterproof);
    }

    public boolean isHasHood() { return hasHood; }
    public boolean isWaterproof() { return waterproof; }

    public void setHasHood(boolean hasHood) {
        this.hasHood = hasHood;
    }

    public void setWaterproof(boolean waterproof) {
        this.waterproof = waterproof;
    }

    @Override
    public String toString() {
        return String.format("[Куртка] %s, бренд: %s, розмір: %s, матеріал: %s, капюшон: %s, водонепроникна: %s, ціна: %.2f грн",
                type, brand, size.name(), material.getUkrainianName(),
                hasHood ? "є" : "немає",
                waterproof ? "так" : "ні",
                price);
    }
}