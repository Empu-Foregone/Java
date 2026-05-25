package com.example;

import java.util.ArrayList;

/**
 * Клас-контейнер для зберігання товарів у магазині.
 * Агрегує колекцію StoreItem та забезпечує операції додавання та пошуку.
 */
public class Store {
    private ArrayList<StoreItem> items;
    private String storeName;
    private String address;

    public Store(String storeName, String address) {
        this.storeName = storeName;
        this.address = address;
        this.items = new ArrayList<>();
    }

    public String getStoreName() {
        return storeName;
    }

    public String getAddress() {
        return address;
    }

    public int getTotalItems() {
        return items.size();
    }

    public int getTotalQuantity() {
        int total = 0;
        for (StoreItem item : items) {
            total += item.getQuantity();
        }
        return total;
    }

    /**
     * Додає одяг до магазину з вказаною кількістю.
     * Якщо такий самий одяг вже існує, кількість збільшується.
     * @param clothes одяг для додавання
     * @param quantity кількість
     */
    public void addNewClothes(Clothes clothes, int quantity) {
        if (clothes == null) {
            throw new IllegalArgumentException("Одяг не може бути null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Кількість має бути більшою за 0");
        }

        for (StoreItem item : items) {
            if (item.getClothes().equals(clothes)) {
                item.addQuantity(quantity);
                System.out.println("✅ Оновлено кількість для: " + clothes.getType());
                return;
            }
        }

        items.add(new StoreItem(clothes, quantity));
        System.out.println("✅ Додано новий товар: " + clothes.getType());
    }

    /**
     * Пошук товарів за брендом.
     * @param brand бренд для пошуку
     * @return список StoreItem, що відповідають критерію
     */
    public ArrayList<StoreItem> searchByBrand(String brand) {
        ArrayList<StoreItem> results = new ArrayList<>();
        for (StoreItem item : items) {
            if (item.getClothes().getBrand().equalsIgnoreCase(brand)) {
                results.add(item);
            }
        }
        return results;
    }

    /**
     * Пошук товарів за матеріалом.
     * @param material матеріал для пошуку
     * @return список StoreItem, що відповідають критерію
     */
    public ArrayList<StoreItem> searchByMaterial(Material material) {
        ArrayList<StoreItem> results = new ArrayList<>();
        for (StoreItem item : items) {
            if (item.getClothes().getMaterial() == material) {
                results.add(item);
            }
        }
        return results;
    }
   /**
    * Повертає відсортований список усіх товарів (за типом одягу).
    * @return відсортований ArrayList товарів
    */
    public ArrayList<StoreItem> getSortedItems() {
    ArrayList<StoreItem> sorted = new ArrayList<>(items);
    sorted.sort((item1, item2) -> item1.getClothes().compareTo(item2.getClothes()));
    return sorted;
    }


    /**
     * Пошук товарів за максимальною ціною.
     * @param maxPrice максимальна ціна
     * @return список StoreItem, що відповідають критерію
     */
    public ArrayList<StoreItem> searchByMaxPrice(double maxPrice) {
        ArrayList<StoreItem> results = new ArrayList<>();
        for (StoreItem item : items) {
            if (item.getClothes().getPrice() <= maxPrice) {
                results.add(item);
            }
        }
        return results;
    }

    /**
     * Повертає всі товари в магазині.
     */
    public ArrayList<StoreItem> getAllItems() {
        return new ArrayList<>(items);
    }

    /**
     * Виводить інформацію про всі товари в магазині.
     */
    public void displayAllItems() {
        if (items.isEmpty()) {
            System.out.println("📭 Магазин порожній.");
            return;
        }

        System.out.println("\n=== ТОВАРИ В МАГАЗИНІ ===");
        System.out.println("Магазин: " + storeName + ", Адреса: " + address);
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + items.get(i));
        }
        System.out.println("Всього найменувань: " + items.size());
        System.out.println("Загальна кількість одиниць: " + getTotalQuantity());
    }

    @Override
    public String toString() {
        return "Магазин: " + storeName + ", Адреса: " + address + 
               ", Найменувань: " + items.size() + ", Одиниць: " + getTotalQuantity();
    }
}