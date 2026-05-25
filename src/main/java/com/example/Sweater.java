package com.example;

/**
 * Похідний клас для светрів.
 * Додає поля: наявність блискавки, тип горловини.
 */
public class Sweater extends Clothes {
    private boolean hasZip;
    private String neckType;

    public Sweater(String type, Size size, double price, String brand, Material material,
                   boolean hasZip, String neckType) {
        super(type, size, price, brand, material);
        setHasZip(hasZip);
        setNeckType(neckType);
    }

    public boolean isHasZip() { return hasZip; }
    public String getNeckType() { return neckType; }

    public void setHasZip(boolean hasZip) {
        this.hasZip = hasZip;
    }

    public void setNeckType(String neckType) {
        if (neckType == null || neckType.trim().isEmpty()) {
            throw new IllegalArgumentException("Тип горловини не може бути порожнім");
        }
        this.neckType = neckType.trim();
    }

    @Override
    public String toString() {
        return String.format("[Светр] %s, бренд: %s, розмір: %s, матеріал: %s, блискавка: %s, горловина: %s, ціна: %.2f грн",
                type, brand, size.name(), material.getUkrainianName(),
                hasZip ? "є" : "немає",
                neckType,
                price);
    }
}