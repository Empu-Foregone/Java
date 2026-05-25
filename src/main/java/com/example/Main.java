package com.example;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static Store store;

    public static void main(String[] args) {
        System.out.println("=== Система управління магазином одягу ===");

        store = new Store("Clothes Paradise", "вул. Хрещатик, 15");
        loadFromFile();

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readIntInput("Оберіть опцію: ");

            switch (choice) {
                case 1:
                    createNewItem();
                    break;
                case 2:
                    store.displayAllItems();
                    break;
                case 3:
                    searchMenu();
                    break;
                case 4:
                    displaySortedItems();
                    break;
                case 5:
                    System.out.println("Зберігаємо колекцію та завершуємо роботу...");
                    saveToFile();
                    System.out.println("До побачення!");
                    running = false;
                    break;
                default:
                    System.out.println("❌ Некоректний вибір. Спробуйте ще раз.");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n=== МЕНЮ ===");
        System.out.println("1. Додати новий товар");
        System.out.println("2. Показати всі товари");
        System.out.println("3. Пошук товарів");
        System.out.println("4. Вивести відсортовану інформацію (за типом)");
        System.out.println("5. Завершити роботу (зберегти)");
        System.out.println(store);
    }

    private static void displaySortedItems() {
        ArrayList<StoreItem> sorted = store.getSortedItems();
        
        if (sorted.isEmpty()) {
            System.out.println("📭 Гардероб порожній. Немає що сортувати.");
            return;
        }

        System.out.println("\n=== ВІДСОРТОВАНІ ТОВАРИ (за типом) ===");
        for (int i = 0; i < sorted.size(); i++) {
            System.out.println((i + 1) + ". " + sorted.get(i));
        }
        System.out.println("Всього найменувань: " + sorted.size());
    }


    private static void loadFromFile() {
        ArrayList<Clothes> loaded = FileManager.loadFromFile();
        if (loaded.isEmpty()) {
            System.out.println("ℹ️ Немає збережених товарів.");
            return;
        }

        for (Clothes clothes : loaded) {
            store.addNewClothes(clothes, 1);
        }
        System.out.println("📦 Завантажено " + loaded.size() + " товарів у магазин.");
    }

    private static void saveToFile() {
        ArrayList<Clothes> toSave = new ArrayList<>();
        for (StoreItem item : store.getAllItems()) {
            for (int i = 0; i < item.getQuantity(); i++) {
                toSave.add(item.getClothes());
            }
        }
        saveToFileDirect(toSave);
    }

    private static void saveToFileDirect(ArrayList<Clothes> wardrobe) {
        try (java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.FileWriter("wardrobe.txt"))) {
            for (Clothes item : wardrobe) {
                writer.println(item.getClass().getSimpleName());
                if (item instanceof Clothes && !(item instanceof Shirt) && !(item instanceof Pants) && !(item instanceof Jacket) && !(item instanceof Sweater)) {
                    writer.println(item.getType());
                    writer.println(item.getSize().name());
                    writer.println(item.getPrice());
                    writer.println(item.getBrand());
                    writer.println(item.getMaterial().name());
                } else if (item instanceof Shirt) {
                    Shirt shirt = (Shirt) item;
                    writer.println(shirt.getType());
                    writer.println(shirt.getSize().name());
                    writer.println(shirt.getPrice());
                    writer.println(shirt.getBrand());
                    writer.println(shirt.getMaterial().name());
                    writer.println(shirt.isHasPocket());
                    writer.println(shirt.getCollarType());
                } else if (item instanceof Pants) {
                    Pants pants = (Pants) item;
                    writer.println(pants.getType());
                    writer.println(pants.getSize().name());
                    writer.println(pants.getPrice());
                    writer.println(pants.getBrand());
                    writer.println(pants.getMaterial().name());
                    writer.println(pants.getLength());
                    writer.println(pants.isHasSuspenders());
                } else if (item instanceof Jacket) {
                    Jacket jacket = (Jacket) item;
                    writer.println(jacket.getType());
                    writer.println(jacket.getSize().name());
                    writer.println(jacket.getPrice());
                    writer.println(jacket.getBrand());
                    writer.println(jacket.getMaterial().name());
                    writer.println(jacket.isHasHood());
                    writer.println(jacket.isWaterproof());
                } else if (item instanceof Sweater) {
                    Sweater sweater = (Sweater) item;
                    writer.println(sweater.getType());
                    writer.println(sweater.getSize().name());
                    writer.println(sweater.getPrice());
                    writer.println(sweater.getBrand());
                    writer.println(sweater.getMaterial().name());
                    writer.println(sweater.isHasZip());
                    writer.println(sweater.getNeckType());
                }
            }
            System.out.println("✅ Колекцію збережено у файл: wardrobe.txt");
        } catch (java.io.IOException e) {
            System.out.println("❌ Помилка збереження: " + e.getMessage());
        }
    }


    private static void searchMenu() {
        System.out.println("\n=== ПОШУК ТОВАРІВ ===");
        System.out.println("1. Пошук за брендом");
        System.out.println("2. Пошук за матеріалом");
        System.out.println("3. Пошук за ціною (не більше заданої)");
        System.out.println("0. Повернутися до головного меню");

        int choice = readIntInput("Ваш вибір: ");
        if (choice == 0) return;

        ArrayList<StoreItem> results = new ArrayList<>();

        switch (choice) {
            case 1:
                results = searchByBrand();
                break;
            case 2:
                results = searchByMaterial();
                break;
            case 3:
                results = searchByMaxPrice();
                break;
            default:
                System.out.println("❌ Некоректний вибір.");
                return;
        }

        displaySearchResults(results);
    }

    private static ArrayList<StoreItem> searchByBrand() {
        String brand = readStringInput("Введіть бренд для пошуку: ");
        return store.searchByBrand(brand);
    }

    private static ArrayList<StoreItem> searchByMaterial() {
        System.out.println("Доступні матеріали: COTTON, POLYESTER, WOOL, DENIM");
        String materialName = readStringInput("Введіть матеріал для пошуку: ");
        try {
            Material material = Material.fromString(materialName);
            return store.searchByMaterial(material);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Невідомий матеріал: " + materialName);
            return new ArrayList<>();
        }
    }

    private static ArrayList<StoreItem> searchByMaxPrice() {
        double maxPrice = readDoubleInput("Введіть максимальну ціну: ");
        if (maxPrice <= 0) {
            System.out.println("❌ Ціна має бути додатною.");
            return new ArrayList<>();
        }
        return store.searchByMaxPrice(maxPrice);
    }

    private static void displaySearchResults(ArrayList<StoreItem> results) {
        System.out.println("\n=== РЕЗУЛЬТАТИ ПОШУКУ ===");
        if (results.isEmpty()) {
            System.out.println("❌ Жоден товар не відповідає критерію пошуку.");
        } else {
            System.out.println("Знайдено " + results.size() + " товар(ів):");
            for (int i = 0; i < results.size(); i++) {
                System.out.println((i + 1) + ". " + results.get(i));
            }
        }
    }


    private static void createNewItem() {
        System.out.println("\n--- Виберіть тип товару ---");
        System.out.println("1. Звичайний одяг (Clothes)");
        System.out.println("2. Сорочка (Shirt)");
        System.out.println("3. Штани (Pants)");
        System.out.println("4. Куртка (Jacket)");
        System.out.println("5. Светр (Sweater)");
        System.out.println("0. Повернутися до головного меню");

        int type = readIntInput("Ваш вибір: ");
        if (type == 0) return;

        Clothes newItem = null;

        switch (type) {
            case 1:
                newItem = createClothes();
                break;
            case 2:
                newItem = createShirt();
                break;
            case 3:
                newItem = createPants();
                break;
            case 4:
                newItem = createJacket();
                break;
            case 5:
                newItem = createSweater();
                break;
            default:
                System.out.println("❌ Невідомий тип.");
                return;
        }

        if (newItem != null) {
            int quantity = readIntInput("Введіть кількість: ");
            store.addNewClothes(newItem, quantity);
        }
    }

    private static Clothes createClothes() {
        System.out.println("\n--- Створення звичайного одягу ---");
        try {
            String type = readStringInput("Тип одягу: ");
            Size size = readSizeFromUser();
            double price = readDoubleInput("Ціна: ");
            String brand = readStringInput("Бренд: ");
            Material material = readMaterialFromUser();
            return new Clothes(type, size, price, brand, material) {};
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Помилка: " + e.getMessage());
            return null;
        }
    }

    private static Clothes createShirt() {
        System.out.println("\n--- Створення сорочки ---");
        try {
            String type = readStringInput("Тип сорочки: ");
            Size size = readSizeFromUser();
            double price = readDoubleInput("Ціна: ");
            String brand = readStringInput("Бренд: ");
            Material material = readMaterialFromUser();
            boolean hasPocket = readBooleanInput("Наявність кишені (так/ні): ");
            String collarType = readStringInput("Тип коміра: ");
            return new Shirt(type, size, price, brand, material, hasPocket, collarType);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Помилка: " + e.getMessage());
            return null;
        }
    }

    private static Clothes createPants() {
        System.out.println("\n--- Створення штанів ---");
        try {
            String type = readStringInput("Тип штанів: ");
            Size size = readSizeFromUser();
            double price = readDoubleInput("Ціна: ");
            String brand = readStringInput("Бренд: ");
            Material material = readMaterialFromUser();
            int length = readIntInput("Довжина штанів (30-120 см): ");
            boolean hasSuspenders = readBooleanInput("Наявність підтяжок (так/ні): ");
            return new Pants(type, size, price, brand, material, length, hasSuspenders);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Помилка: " + e.getMessage());
            return null;
        }
    }

    private static Clothes createJacket() {
        System.out.println("\n--- Створення куртки ---");
        try {
            String type = readStringInput("Тип куртки: ");
            Size size = readSizeFromUser();
            double price = readDoubleInput("Ціна: ");
            String brand = readStringInput("Бренд: ");
            Material material = readMaterialFromUser();
            boolean hasHood = readBooleanInput("Наявність капюшона (так/ні): ");
            boolean waterproof = readBooleanInput("Водонепроникна (так/ні): ");
            return new Jacket(type, size, price, brand, material, hasHood, waterproof);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Помилка: " + e.getMessage());
            return null;
        }
    }

    private static Clothes createSweater() {
        System.out.println("\n--- Створення светра ---");
        try {
            String type = readStringInput("Тип светра: ");
            Size size = readSizeFromUser();
            double price = readDoubleInput("Ціна: ");
            String brand = readStringInput("Бренд: ");
            Material material = readMaterialFromUser();
            boolean hasZip = readBooleanInput("Наявність блискавки (так/ні): ");
            String neckType = readStringInput("Тип горловини: ");
            return new Sweater(type, size, price, brand, material, hasZip, neckType);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Помилка: " + e.getMessage());
            return null;
        }
    }


    private static Size readSizeFromUser() {
        while (true) {
            try {
                String input = readStringInput("Розмір (S/M/L/XL): ");
                return Size.fromString(input);
            } catch (IllegalArgumentException e) {
                System.out.println("❌ " + e.getMessage() + ". Спробуйте ще раз.");
            }
        }
    }

    private static Material readMaterialFromUser() {
        while (true) {
            try {
                String input = readStringInput("Матеріал (COTTON/POLYESTER/WOOL/DENIM): ");
                return Material.fromString(input);
            } catch (IllegalArgumentException e) {
                System.out.println("❌ " + e.getMessage() + ". Спробуйте ще раз.");
            }
        }
    }

    private static boolean readBooleanInput(String prompt) {
        while (true) {
            String input = readStringInput(prompt);
            if (input.equalsIgnoreCase("так") || input.equalsIgnoreCase("true") || input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y")) {
                return true;
            }
            if (input.equalsIgnoreCase("ні") || input.equalsIgnoreCase("false") || input.equalsIgnoreCase("no") || input.equalsIgnoreCase("n")) {
                return false;
            }
            System.out.println("❌ Введіть 'так' або 'ні'");
        }
    }

    private static int readIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("❌ Помилка: введіть ціле число!");
                scanner.next();
            }
        }
    }

    private static double readDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("❌ Помилка: введіть число!");
                scanner.next();
            }
        }
    }

    private static String readStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.next().trim();
    }
}