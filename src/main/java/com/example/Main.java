package com.example;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static ArrayList<Clothes> wardrobe;

    public static void main(String[] args) {
        System.out.println("=== Система управління гардеробом (з пошуком) ===");

        wardrobe = FileManager.loadFromFile();

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readIntInput("Оберіть опцію: ");

            switch (choice) {
                case 1:
                    createNewItem();
                    break;
                case 2:
                    displayAllItems();
                    break;
                case 3:
                    searchMenu();
                    break;
                case 4:
                    System.out.println("Зберігаємо колекцію та завершуємо роботу...");
                    FileManager.saveToFile(wardrobe);
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
        System.out.println("1. Створити новий об'єкт");
        System.out.println("2. Вивести інформацію про всі об'єкти");
        System.out.println("3. Пошук об'єкта");
        System.out.println("4. Завершити роботу (зберегти)");
        System.out.println("Всього предметів: " + wardrobe.size());
    }


    private static void searchMenu() {
        System.out.println("\n=== ПОШУК ОБ'ЄКТІВ ===");
        System.out.println("1. Пошук за брендом");
        System.out.println("2. Пошук за матеріалом");
        System.out.println("3. Пошук за ціною (не більше заданої)");
        System.out.println("0. Повернутися до головного меню");

        int choice = readIntInput("Ваш вибір: ");
        if (choice == 0) return;

        switch (choice) {
            case 1:
                searchByBrand();
                break;
            case 2:
                searchByMaterial();
                break;
            case 3:
                searchByMaxPrice();
                break;
            default:
                System.out.println("❌ Некоректний вибір.");
        }
    }


    /**
     * Пошук об'єктів за брендом (точний збіг, нечутливий до регістру).
     */
    private static void searchByBrand() {
        if (wardrobe.isEmpty()) {
            System.out.println("📭 Гардероб порожній. Немає що шукати.");
            return;
        }

        String brand = readStringInput("Введіть бренд для пошуку: ");
        ArrayList<Clothes> results = new ArrayList<>();

        for (Clothes item : wardrobe) {
            if (item.getBrand().equalsIgnoreCase(brand)) {
                results.add(item);
            }
        }

        displaySearchResults(results, "брендом \"" + brand + "\"");
    }

    /**
     * Пошук об'єктів за матеріалом (точний збіг).
     */
    private static void searchByMaterial() {
        if (wardrobe.isEmpty()) {
            System.out.println("📭 Гардероб порожній. Немає що шукати.");
            return;
        }

        System.out.println("Доступні матеріали: COTTON, POLYESTER, WOOL, DENIM");
        String materialName = readStringInput("Введіть матеріал для пошуку: ");
        
        Material material;
        try {
            material = Material.fromString(materialName);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Невідомий матеріал: " + materialName);
            return;
        }

        ArrayList<Clothes> results = new ArrayList<>();
        for (Clothes item : wardrobe) {
            if (item.getMaterial() == material) {
                results.add(item);
            }
        }

        displaySearchResults(results, "матеріалом \"" + material.getUkrainianName() + "\"");
    }

    /**
     * Пошук об'єктів за ціною (ціна <= заданої).
     */
    private static void searchByMaxPrice() {
        if (wardrobe.isEmpty()) {
            System.out.println("📭 Гардероб порожній. Немає що шукати.");
            return;
        }

        double maxPrice = readDoubleInput("Введіть максимальну ціну: ");
        if (maxPrice <= 0) {
            System.out.println("❌ Ціна має бути додатною.");
            return;
        }

        ArrayList<Clothes> results = new ArrayList<>();
        for (Clothes item : wardrobe) {
            if (item.getPrice() <= maxPrice) {
                results.add(item);
            }
        }

        displaySearchResults(results, "ціною не більше " + maxPrice + " грн");
    }

    /**
     * Виводить результати пошуку.
     * @param results знайдені об'єкти
     * @param criterion опис критерію для виведення
     */
    private static void displaySearchResults(ArrayList<Clothes> results, String criterion) {
        System.out.println("\n=== РЕЗУЛЬТАТИ ПОШУКУ ===");
        System.out.println("Критерій: " + criterion);
        
        if (results.isEmpty()) {
            System.out.println("❌ Жоден об'єкт не відповідає критерію пошуку.");
        } else {
            System.out.println("Знайдено " + results.size() + " об'єкт(ів):");
            for (int i = 0; i < results.size(); i++) {
                System.out.println((i + 1) + ". " + results.get(i));
            }
        }
    }


    private static void createNewItem() {
        System.out.println("\n--- Виберіть тип об'єкта ---");
        System.out.println("1. Звичайний одяг (Clothes)");
        System.out.println("2. Сорочка (Shirt)");
        System.out.println("3. Штани (Pants)");
        System.out.println("4. Куртка (Jacket)");
        System.out.println("5. Светр (Sweater)");
        System.out.println("0. Повернутися до головного меню");

        int type = readIntInput("Ваш вибір: ");
        if (type == 0) return;

        switch (type) {
            case 1:
                createClothes();
                break;
            case 2:
                createShirt();
                break;
            case 3:
                createPants();
                break;
            case 4:
                createJacket();
                break;
            case 5:
                createSweater();
                break;
            default:
                System.out.println("❌ Невідомий тип. Спробуйте ще раз.");
        }
    }

    private static void createClothes() {
        System.out.println("\n--- Створення звичайного одягу ---");
        try {
            String type = readStringInput("Тип одягу: ");
            Size size = readSizeFromUser();
            double price = readDoubleInput("Ціна: ");
            String brand = readStringInput("Бренд: ");
            Material material = readMaterialFromUser();

            Clothes item = new Clothes(type, size, price, brand, material);
            wardrobe.add(item);
            System.out.println("✅ Одяг додано до гардеробу!");
            System.out.println(item);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Помилка: " + e.getMessage());
        }
    }

    private static void createShirt() {
        System.out.println("\n--- Створення сорочки ---");
        try {
            String type = readStringInput("Тип сорочки: ");
            Size size = readSizeFromUser();
            double price = readDoubleInput("Ціна: ");
            String brand = readStringInput("Бренд: ");
            Material material = readMaterialFromUser();
            boolean hasPocket = readBooleanInput("Наявність кишені (так/ні): ");
            String collarType = readStringInput("Тип коміра: ");

            Shirt shirt = new Shirt(type, size, price, brand, material, hasPocket, collarType);
            wardrobe.add(shirt);
            System.out.println("✅ Сорочку додано до гардеробу!");
            System.out.println(shirt);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Помилка: " + e.getMessage());
        }
    }

    private static void createPants() {
        System.out.println("\n--- Створення штанів ---");
        try {
            String type = readStringInput("Тип штанів: ");
            Size size = readSizeFromUser();
            double price = readDoubleInput("Ціна: ");
            String brand = readStringInput("Бренд: ");
            Material material = readMaterialFromUser();
            int length = readIntInput("Довжина штанів (30-120 см): ");
            boolean hasSuspenders = readBooleanInput("Наявність підтяжок (так/ні): ");

            Pants pants = new Pants(type, size, price, brand, material, length, hasSuspenders);
            wardrobe.add(pants);
            System.out.println("✅ Штани додано до гардеробу!");
            System.out.println(pants);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Помилка: " + e.getMessage());
        }
    }

    private static void createJacket() {
        System.out.println("\n--- Створення куртки ---");
        try {
            String type = readStringInput("Тип куртки: ");
            Size size = readSizeFromUser();
            double price = readDoubleInput("Ціна: ");
            String brand = readStringInput("Бренд: ");
            Material material = readMaterialFromUser();
            boolean hasHood = readBooleanInput("Наявність капюшона (так/ні): ");
            boolean waterproof = readBooleanInput("Водонепроникна (так/ні): ");

            Jacket jacket = new Jacket(type, size, price, brand, material, hasHood, waterproof);
            wardrobe.add(jacket);
            System.out.println("✅ Куртку додано до гардеробу!");
            System.out.println(jacket);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Помилка: " + e.getMessage());
        }
    }

    private static void createSweater() {
        System.out.println("\n--- Створення светра ---");
        try {
            String type = readStringInput("Тип светра: ");
            Size size = readSizeFromUser();
            double price = readDoubleInput("Ціна: ");
            String brand = readStringInput("Бренд: ");
            Material material = readMaterialFromUser();
            boolean hasZip = readBooleanInput("Наявність блискавки (так/ні): ");
            String neckType = readStringInput("Тип горловини (круглий/човник/стійка): ");

            Sweater sweater = new Sweater(type, size, price, brand, material, hasZip, neckType);
            wardrobe.add(sweater);
            System.out.println("✅ Светр додано до гардеробу!");
            System.out.println(sweater);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Помилка: " + e.getMessage());
        }
    }


    private static void displayAllItems() {
        if (wardrobe.isEmpty()) {
            System.out.println("📭 Гардероб порожній.");
            return;
        }

        System.out.println("\n=== ВМІСТ ГАРДЕРОБУ ===");
        for (int i = 0; i < wardrobe.size(); i++) {
            System.out.println((i + 1) + ". " + wardrobe.get(i));
        }
        System.out.println("Всього предметів: " + wardrobe.size());
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