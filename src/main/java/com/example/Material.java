package com.example;

/**
 * Перерахування матеріалів одягу.
 */
public enum Material {
    COTTON("Бавовна"),
    POLYESTER("Поліестер"),
    WOOL("Вовна"),
    DENIM("Денім");

    private final String ukrainianName;

    Material(String ukrainianName) {
        this.ukrainianName = ukrainianName;
    }

    public String getUkrainianName() {
        return ukrainianName;
    }

    public static boolean isValid(String value) {
        for (Material m : Material.values()) {
            if (m.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }

    public static Material fromString(String value) {
        for (Material m : Material.values()) {
            if (m.name().equalsIgnoreCase(value)) {
                return m;
            }
        }
        throw new IllegalArgumentException("Невідомий матеріал: " + value);
    }
}