package com.example;

/**
 * Похідний клас для сорочок.
 * Додає поле: наявність кишені, тип коміра.
 */
public class Shirt extends Clothes {
    private boolean hasPocket;
    private String collarType;

    public Shirt(String type, Size size, double price, String brand, Material material,
                 boolean hasPocket, String collarType) {
        super(type, size, price, brand, material);
        setHasPocket(hasPocket);
        setCollarType(collarType);
    }

    public boolean isHasPocket() { return hasPocket; }
    public String getCollarType() { return collarType; }

    public void setHasPocket(boolean hasPocket) {
        this.hasPocket = hasPocket;
    }

    public void setCollarType(String collarType) {
        if (collarType == null || collarType.trim().isEmpty()) {
            throw new IllegalArgumentException("Тип коміра не може бути порожнім");
        }
        this.collarType = collarType.trim();
    }

    @Override
    public String toString() {
        return String.format("[Сорочка] %s, бренд: %s, розмір: %s, матеріал: %s, кишеня: %s, комір: %s, ціна: %.2f грн",
                type, brand, size.name(), material.getUkrainianName(),
                hasPocket ? "є" : "немає", collarType, price);
    }
}