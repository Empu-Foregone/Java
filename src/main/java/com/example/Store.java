package com.example;

import java.util.ArrayList;
import java.util.UUID;

public class Store {
    private ArrayList<StoreItem> items;
    private String storeName;
    private String address;

    public Store(String storeName, String address) {
        this.storeName = storeName;
        this.address = address;
        this.items = new ArrayList<>();
    }

    public String getStoreName() { return storeName; }
    public String getAddress() { return address; }
    public int getTotalItems() { return items.size(); }

    public int getTotalQuantity() {
        int total = 0;
        for (StoreItem item : items) {
            total += item.getQuantity();
        }
        return total;
    }

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
                System.out.println("Оновлено кількість для: " + clothes.getType());
                return;
            }
        }

        items.add(new StoreItem(clothes, quantity));
        System.out.println("Додано новий товар: " + clothes.getType());
    }

    public boolean update(Clothes existingObject, Clothes newObject) {
        for (int i = 0; i < items.size(); i++) {
            StoreItem item = items.get(i);
            if (item.getClothes().equals(existingObject)) {
                items.set(i, new StoreItem(newObject, item.getQuantity()));
                System.out.println("Оновлено товар: " + existingObject.getType() + " -> " + newObject.getType());
                return true;
            }
        }
        System.out.println("Товар не знайдено для оновлення");
        return false;
    }

    public boolean delete(Clothes existingObject) {
        for (int i = 0; i < items.size(); i++) {
            StoreItem item = items.get(i);
            if (item.getClothes().equals(existingObject)) {
                items.remove(i);
                System.out.println("Видалено товар: " + existingObject.getType());
                return true;
            }
        }
        System.out.println("Товар не знайдено для видалення");
        return false;
    }

    public StoreItem findByUuid(UUID uuid) {
        for (StoreItem item : items) {
            if (item.getClothes().getUuid().equals(uuid)) {
                return item;
            }
        }
        return null;
    }

    public ArrayList<StoreItem> searchByBrand(String brand) {
        ArrayList<StoreItem> results = new ArrayList<>();
        for (StoreItem item : items) {
            if (item.getClothes().getBrand().equalsIgnoreCase(brand)) {
                results.add(item);
            }
        }
        return results;
    }

    public ArrayList<StoreItem> searchByMaterial(Material material) {
        ArrayList<StoreItem> results = new ArrayList<>();
        for (StoreItem item : items) {
            if (item.getClothes().getMaterial() == material) {
                results.add(item);
            }
        }
        return results;
    }

    public ArrayList<StoreItem> searchByMaxPrice(double maxPrice) {
        ArrayList<StoreItem> results = new ArrayList<>();
        for (StoreItem item : items) {
            if (item.getClothes().getPrice() <= maxPrice) {
                results.add(item);
            }
        }
        return results;
    }

    public ArrayList<StoreItem> getAllItems() {
        return new ArrayList<>(items);
    }

    public void displayAllItems() {
        if (items.isEmpty()) {
            System.out.println("Магазин порожнiй.");
            return;
        }

        System.out.println("\n=== ТОВАРИ В МАГАЗИНI ===");
        System.out.println("Магазин: " + storeName + ", Адреса: " + address);
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + items.get(i));
        }
        System.out.println("Всього найменувань: " + items.size());
        System.out.println("Загальна кiлькiсть одиниць: " + getTotalQuantity());
    }

    @Override
    public String toString() {
        return "Магазин: " + storeName + ", Адреса: " + address + 
               ", Найменувань: " + items.size() + ", Одиниць: " + getTotalQuantity();
    }
}