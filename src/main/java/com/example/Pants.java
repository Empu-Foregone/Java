package com.example;

/**
 * Похідний клас для штанів.
 * Додає поле: довжина (length), наявність підтяжок.
 */
public class Pants extends Clothes {
    private int length;
    private boolean hasSuspenders;

    public Pants(String type, Size size, double price, String brand, Material material,
                 int length, boolean hasSuspenders) {
        super(type, size, price, brand, material);
        setLength(length);
        setHasSuspenders(hasSuspenders);
    }

    public int getLength() { return length; }
    public boolean isHasSuspenders() { return hasSuspenders; }

    public void setLength(int length) {
        if (length < 30 || length > 120) {
            throw new IllegalArgumentException("Довжина штанів має бути від 30 до 120 см");
        }
        this.length = length;
    }

    public void setHasSuspenders(boolean hasSuspenders) {
        this.hasSuspenders = hasSuspenders;
    }

    @Override
    public String toString() {
        return String.format("[Штани] %s, бренд: %s, розмір: %s, матеріал: %s, довжина: %d см, підтяжки: %s, ціна: %.2f грн",
                type, brand, size.name(), material.getUkrainianName(), length,
                hasSuspenders ? "є" : "немає", price);
    }
}