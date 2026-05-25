package com.example;

/**
 * Перерахування розмірів одягу.
 */
public enum Size {
    S("Small"),
    M("Medium"),
    L("Large"),
    XL("Extra Large");

    private final String description;

    Size(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Перевіряє, чи є рядок валідним розміром.
     * @param value рядок для перевірки
     * @return true якщо рядок відповідає одному з розмірів
     */
    public static boolean isValid(String value) {
        for (Size s : Size.values()) {
            if (s.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Отримує enum Size з рядка (нечутливо до регістру).
     * @param value рядок-значення (наприклад, "xl" або "XL")
     * @return відповідний Size
     * @throws IllegalArgumentException якщо розмір не валідний
     */
    public static Size fromString(String value) {
        for (Size s : Size.values()) {
            if (s.name().equalsIgnoreCase(value)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Невідомий розмір: " + value);
    }
}