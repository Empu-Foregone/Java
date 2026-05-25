package com.example;

/**
 * Клас-агрегатор, який містить масив об'єктів Clothes.
 * Демонструє принцип агрегації.
 */
public class Wardrobe {
    private Clothes[] items;
    private int count;

    /**
     * Конструктор створює шафу з максимальною місткістю.
     * @param capacity максимальна кількість одягу в шафі
     */
    public Wardrobe(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Місткість шафи має бути більшою за 0");
        }
        this.items = new Clothes[capacity];
        this.count = 0;
    }

    /**
     * Додає предмет одягу до шафи.
     * @param item предмет одягу
     * @return true якщо додано успішно, false якщо шафа заповнена
     */
    public boolean addItem(Clothes item) {
        if (item == null) {
            throw new IllegalArgumentException("Не можна додати null");
        }
        if (count < items.length) {
            items[count] = item;
            count++;
            return true;
        }
        return false;
    }

    /**
     * Повертає предмет одягу за індексом.
     * @param index індекс (0-based)
     * @return предмет одягу
     */
    public Clothes getItem(int index) {
        if (index < 0 || index >= count) {
            throw new IllegalArgumentException("Індекс поза межами");
        }
        return items[index];
    }

    /**
     * Повертає всі предмети одягу в шафі.
     * @return масив одягу (тільки заповнені елементи)
     */
    public Clothes[] getAllItems() {
        Clothes[] result = new Clothes[count];
        System.arraycopy(items, 0, result, 0, count);
        return result;
    }

    public int getCount() { return count; }
    public int getCapacity() { return items.length; }
    public boolean isFull() { return count == items.length; }
    public boolean isEmpty() { return count == 0; }
}