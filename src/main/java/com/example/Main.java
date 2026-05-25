package com.example;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.UUID;

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
            int choice = readIntInput("Оберiть опцiю: ");

            switch (choice) {
                case 1:
                    createNewItem();
                    break;
                case 2:
                    store.displayAllItems();
                    break;
                case 3:
                    updateItem();
                    break;
                case 4:
                    deleteItem();
                    break;
                case 5:
                    searchMenu();
                    break;
                case 6:
                    sortMenu();
                    break;
                case 7:
                    searchByUuid();
                    break;
                case 8:
                    System.out.println("Зберiгаємо колекцiю та завершуємо роботу...");
                    saveToFile();
                    System.out.println("До побачення!");
                    running = false;
                    break;
                default:
                    System.out.println("Некоректний вибiр. Спробуйте ще раз.");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n=== МЕНЮ ===");
        System.out.println("1. Додати новий товар");
        System.out.println("2. Показати всi товари");
        System.out.println("3. Модифiкувати товар");
        System.out.println("4. Видалити товар");
        System.out.println("5. Пошук товарiв");
        System.out.println("6. Вивести вiдсортовану iнформацiю");
        System.out.println("7. Пошук за UUID");
        System.out.println("8. Завершити роботу (зберегти)");
        System.out.println(store);
    }

    private static void updateItem() {
        if (store.getAllItems().isEmpty()) {
            System.out.println("Магазин порожнiй. Немає що модифiкувати.");
            return;
        }

        store.displayAllItems();
        int index = readIntInput("Введiть номер товару для модифiкацiї: ");
        ArrayList<StoreItem> items = store.getAllItems();
        
        if (index < 1 || index > items.size()) {
            System.out.println("Некоректний номер товару.");
            return;
        }

        Clothes oldItem = items.get(index - 1).getClothes();
        
        System.out.println("\n--- Модифiкацiя товару ---");
        System.out.println("Поточнi данi: " + oldItem);
        
        System.out.println("Що бажаєте змiнити?");
        System.out.println("1. Тип одягу");
        System.out.println("2. Розмiр");
        System.out.println("3. Цiну");
        System.out.println("4. Бренд");
        System.out.println("5. Матерiал");
        System.out.println("0. Скасувати");
        
        int field = readIntInput("Ваш вибiр: ");
        if (field == 0) return;
        
        Clothes newItem = null;
        
        try {
            switch (field) {
                case 1:
                    String newType = readStringInput("Новий тип одягу: ");
                    newItem = createCopyWithNewType(oldItem, newType);
                    break;
                case 2:
                    Size newSize = readSizeFromUser();
                    newItem = createCopyWithNewSize(oldItem, newSize);
                    break;
                case 3:
                    double newPrice = readDoubleInput("Нова цiна: ");
                    newItem = createCopyWithNewPrice(oldItem, newPrice);
                    break;
                case 4:
                    String newBrand = readStringInput("Новий бренд: ");
                    newItem = createCopyWithNewBrand(oldItem, newBrand);
                    break;
                case 5:
                    Material newMaterial = readMaterialFromUser();
                    newItem = createCopyWithNewMaterial(oldItem, newMaterial);
                    break;
                default:
                    System.out.println("Некоректний вибiр.");
                    return;
            }
            
            if (newItem != null) {
                store.update(oldItem, newItem);
                System.out.println("Товар успiшно оновлено!");
            }
        } catch (InvalidFieldValueException e) {
            System.out.println("Помилка валiдацiї: " + e.getMessage());
        } catch (ObjectNotFoundException e) {
            System.out.println("Помилка: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    private static void deleteItem() {
        if (store.getAllItems().isEmpty()) {
            System.out.println("Магазин порожнiй. Немає що видаляти.");
            return;
        }

        store.displayAllItems();
        int index = readIntInput("Введiть номер товару для видалення: ");
        ArrayList<StoreItem> items = store.getAllItems();
        
        if (index < 1 || index > items.size()) {
            System.out.println("Некоректний номер товару.");
            return;
        }

        Clothes itemToDelete = items.get(index - 1).getClothes();
        
        String confirm = readStringInput("Ви впевненi, що хочете видалити \"" + itemToDelete.getType() + "\"? (так/нi): ");
        if (confirm.equalsIgnoreCase("так") || confirm.equalsIgnoreCase("yes") || confirm.equalsIgnoreCase("y")) {
            try {
                store.delete(itemToDelete);
                System.out.println("Товар успiшно видалено!");
            } catch (ObjectNotFoundException e) {
                System.out.println("Помилка: " + e.getMessage());
            }
        } else {
            System.out.println("Видалення скасовано.");
        }
    }

    private static void createNewItem() {
        System.out.println("\n--- Виберiть тип товару ---");
        System.out.println("1. Звичайний одяг (Clothes)");
        System.out.println("2. Сорочка (Shirt)");
        System.out.println("3. Штани (Pants)");
        System.out.println("4. Куртка (Jacket)");
        System.out.println("5. Светр (Sweater)");
        System.out.println("0. Повернутися до головного меню");

        int type = readIntInput("Ваш вибiр: ");
        if (type == 0) return;

        Clothes newItem = null;

        try {
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
                    System.out.println("Невiдомий тип.");
                    return;
            }
        } catch (InvalidFieldValueException e) {
            System.out.println("Помилка: " + e.getMessage());
            return;
        }

        if (newItem != null) {
            int quantity = readIntInput("Введiть кiлькiсть: ");
            try {
                store.addNewClothes(newItem, quantity);
            } catch (InvalidFieldValueException e) {
                System.out.println("Помилка: " + e.getMessage());
            }
        }
    }

    private static Clothes createCopyWithNewType(Clothes original, String newType) {
        if (original instanceof Shirt) {
            Shirt s = (Shirt) original;
            return new Shirt(newType, s.getSize(), s.getPrice(), s.getBrand(), s.getMaterial(), s.isHasPocket(), s.getCollarType());
        } else if (original instanceof Pants) {
            Pants p = (Pants) original;
            return new Pants(newType, p.getSize(), p.getPrice(), p.getBrand(), p.getMaterial(), p.getLength(), p.isHasSuspenders());
        } else if (original instanceof Jacket) {
            Jacket j = (Jacket) original;
            return new Jacket(newType, j.getSize(), j.getPrice(), j.getBrand(), j.getMaterial(), j.isHasHood(), j.isWaterproof());
        } else if (original instanceof Sweater) {
            Sweater sw = (Sweater) original;
            return new Sweater(newType, sw.getSize(), sw.getPrice(), sw.getBrand(), sw.getMaterial(), sw.isHasZip(), sw.getNeckType());
        } else {
            return new Clothes(newType, original.getSize(), original.getPrice(), original.getBrand(), original.getMaterial()) {};
        }
    }

    private static Clothes createCopyWithNewSize(Clothes original, Size newSize) {
        if (original instanceof Shirt) {
            Shirt s = (Shirt) original;
            return new Shirt(s.getType(), newSize, s.getPrice(), s.getBrand(), s.getMaterial(), s.isHasPocket(), s.getCollarType());
        } else if (original instanceof Pants) {
            Pants p = (Pants) original;
            return new Pants(p.getType(), newSize, p.getPrice(), p.getBrand(), p.getMaterial(), p.getLength(), p.isHasSuspenders());
        } else if (original instanceof Jacket) {
            Jacket j = (Jacket) original;
            return new Jacket(j.getType(), newSize, j.getPrice(), j.getBrand(), j.getMaterial(), j.isHasHood(), j.isWaterproof());
        } else if (original instanceof Sweater) {
            Sweater sw = (Sweater) original;
            return new Sweater(sw.getType(), newSize, sw.getPrice(), sw.getBrand(), sw.getMaterial(), sw.isHasZip(), sw.getNeckType());
        } else {
            return new Clothes(original.getType(), newSize, original.getPrice(), original.getBrand(), original.getMaterial()) {};
        }
    }

    private static Clothes createCopyWithNewPrice(Clothes original, double newPrice) {
        if (original instanceof Shirt) {
            Shirt s = (Shirt) original;
            return new Shirt(s.getType(), s.getSize(), newPrice, s.getBrand(), s.getMaterial(), s.isHasPocket(), s.getCollarType());
        } else if (original instanceof Pants) {
            Pants p = (Pants) original;
            return new Pants(p.getType(), p.getSize(), newPrice, p.getBrand(), p.getMaterial(), p.getLength(), p.isHasSuspenders());
        } else if (original instanceof Jacket) {
            Jacket j = (Jacket) original;
            return new Jacket(j.getType(), j.getSize(), newPrice, j.getBrand(), j.getMaterial(), j.isHasHood(), j.isWaterproof());
        } else if (original instanceof Sweater) {
            Sweater sw = (Sweater) original;
            return new Sweater(sw.getType(), sw.getSize(), newPrice, sw.getBrand(), sw.getMaterial(), sw.isHasZip(), sw.getNeckType());
        } else {
            return new Clothes(original.getType(), original.getSize(), newPrice, original.getBrand(), original.getMaterial()) {};
        }
    }

    private static Clothes createCopyWithNewBrand(Clothes original, String newBrand) {
        if (original instanceof Shirt) {
            Shirt s = (Shirt) original;
            return new Shirt(s.getType(), s.getSize(), s.getPrice(), newBrand, s.getMaterial(), s.isHasPocket(), s.getCollarType());
        } else if (original instanceof Pants) {
            Pants p = (Pants) original;
            return new Pants(p.getType(), p.getSize(), p.getPrice(), newBrand, p.getMaterial(), p.getLength(), p.isHasSuspenders());
        } else if (original instanceof Jacket) {
            Jacket j = (Jacket) original;
            return new Jacket(j.getType(), j.getSize(), j.getPrice(), newBrand, j.getMaterial(), j.isHasHood(), j.isWaterproof());
        } else if (original instanceof Sweater) {
            Sweater sw = (Sweater) original;
            return new Sweater(sw.getType(), sw.getSize(), sw.getPrice(), newBrand, sw.getMaterial(), sw.isHasZip(), sw.getNeckType());
        } else {
            return new Clothes(original.getType(), original.getSize(), original.getPrice(), newBrand, original.getMaterial()) {};
        }
    }

    private static Clothes createCopyWithNewMaterial(Clothes original, Material newMaterial) {
        if (original instanceof Shirt) {
            Shirt s = (Shirt) original;
            return new Shirt(s.getType(), s.getSize(), s.getPrice(), s.getBrand(), newMaterial, s.isHasPocket(), s.getCollarType());
        } else if (original instanceof Pants) {
            Pants p = (Pants) original;
            return new Pants(p.getType(), p.getSize(), p.getPrice(), p.getBrand(), newMaterial, p.getLength(), p.isHasSuspenders());
        } else if (original instanceof Jacket) {
            Jacket j = (Jacket) original;
            return new Jacket(j.getType(), j.getSize(), j.getPrice(), j.getBrand(), newMaterial, j.isHasHood(), j.isWaterproof());
        } else if (original instanceof Sweater) {
            Sweater sw = (Sweater) original;
            return new Sweater(sw.getType(), sw.getSize(), sw.getPrice(), sw.getBrand(), newMaterial, sw.isHasZip(), sw.getNeckType());
        } else {
            return new Clothes(original.getType(), original.getSize(), original.getPrice(), original.getBrand(), newMaterial) {};
        }
    }

    private static void searchByUuid() {
        String uuidStr = readStringInput("Введіть UUID для пошуку: ");
        try {
            UUID uuid = UUID.fromString(uuidStr);
            StoreItem found = store.findByUuid(uuid);
            if (found != null) {
                System.out.println("Знайдено: " + found.getClothes());
                System.out.println("Кiлькiсть: " + found.getQuantity());
            } else {
                System.out.println("Товар з UUID " + uuidStr + " не знайдено.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Некоректний формат UUID: " + uuidStr);
        }
    }

    private static void sortMenu() {
        if (store.getAllItems().isEmpty()) {
            System.out.println("Магазин порожнiй. Немає що сортувати.");
            return;
        }

        System.out.println("\n=== ВИБІР КРИТЕРІЮ СОРТУВАННЯ ===");
        System.out.println("1. Сортувати за типом одягу (алфавiт)");
        System.out.println("2. Сортувати за цiною (вiд дешевших до дорожчих)");
        System.out.println("3. Сортувати за брендом (алфавiт)");
        System.out.println("0. Повернутися до головного меню");

        int choice = readIntInput("Ваш вибiр: ");
        if (choice == 0) return;

        ArrayList<StoreItem> items = store.getAllItems();
        
        switch (choice) {
            case 1:
                items.sort((o1, o2) -> o1.getClothes().getType().compareToIgnoreCase(o2.getClothes().getType()));
                System.out.println("Вiдсортовано за типом одягу");
                break;
            case 2:
                items.sort((o1, o2) -> Double.compare(o1.getClothes().getPrice(), o2.getClothes().getPrice()));
                System.out.println("Вiдсортовано за цiною (вiд дешевших до дорожчих)");
                break;
            case 3:
                items.sort((o1, o2) -> o1.getClothes().getBrand().compareToIgnoreCase(o2.getClothes().getBrand()));
                System.out.println("Вiдсортовано за брендом");
                break;
            default:
                System.out.println("Некоректний вибiр.");
                return;
        }

        System.out.println("\n=== ВIДСОРТОВАНI ТОВАРИ ===");
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + items.get(i));
        }
    }

    private static void loadFromFile() {
        ArrayList<Clothes> loaded = FileManager.loadFromFile();
        if (loaded.isEmpty()) {
            System.out.println("Немає збережених товарiв.");
            return;
        }

        for (Clothes clothes : loaded) {
            try {
                store.addNewClothes(clothes, 1);
            } catch (InvalidFieldValueException e) {
                System.out.println("Помилка завантаження: " + e.getMessage());
            }
        }
        System.out.println("Завантажено " + loaded.size() + " товарiв у магазин.");
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
                if (item instanceof Shirt) {
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
                } else {
                    writer.println(item.getType());
                    writer.println(item.getSize().name());
                    writer.println(item.getPrice());
                    writer.println(item.getBrand());
                    writer.println(item.getMaterial().name());
                }
            }
            System.out.println("Колекцiю збережено у файл: wardrobe.txt");
        } catch (java.io.IOException e) {
            System.out.println("Помилка збереження: " + e.getMessage());
        }
    }

    private static void searchMenu() {
        System.out.println("\n=== ПОШУК ТОВАРIВ ===");
        System.out.println("1. Пошук за брендом");
        System.out.println("2. Пошук за матерiалом");
        System.out.println("3. Пошук за цiною (не бiльше заданої)");
        System.out.println("0. Повернутися до головного меню");

        int choice = readIntInput("Ваш вибiр: ");
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
                System.out.println("Некоректний вибiр.");
                return;
        }

        displaySearchResults(results);
    }

    private static ArrayList<StoreItem> searchByBrand() {
        String brand = readStringInput("Введiть бренд для пошуку: ");
        return store.searchByBrand(brand);
    }

    private static ArrayList<StoreItem> searchByMaterial() {
        System.out.println("Доступнi матерiали: COTTON, POLYESTER, WOOL, DENIM");
        String materialName = readStringInput("Введiть матерiал для пошуку: ");
        try {
            Material material = Material.fromString(materialName);
            return store.searchByMaterial(material);
        } catch (IllegalArgumentException e) {
            System.out.println("Невiдомий матерiал: " + materialName);
            return new ArrayList<>();
        }
    }

    private static ArrayList<StoreItem> searchByMaxPrice() {
        double maxPrice = readDoubleInput("Введiть максимальну цiну: ");
        if (maxPrice <= 0) {
            System.out.println("Цiна має бути додатною.");
            return new ArrayList<>();
        }
        return store.searchByMaxPrice(maxPrice);
    }

    private static void displaySearchResults(ArrayList<StoreItem> results) {
        System.out.println("\n=== РЕЗУЛЬТАТИ ПОШУКУ ===");
        if (results.isEmpty()) {
            System.out.println("Жоден товар не вiдповiдає критерiю пошуку.");
        } else {
            System.out.println("Знайдено " + results.size() + " товар(ів):");
            for (int i = 0; i < results.size(); i++) {
                System.out.println((i + 1) + ". " + results.get(i));
            }
        }
    }

    private static Clothes createClothes() {
        System.out.println("\n--- Створення звичайного одягу ---");
        String type = readStringInput("Тип одягу: ");
        Size size = readSizeFromUser();
        double price = readDoubleInput("Цiна: ");
        String brand = readStringInput("Бренд: ");
        Material material = readMaterialFromUser();
        return new Clothes(type, size, price, brand, material) {};
    }

    private static Clothes createShirt() {
        System.out.println("\n--- Створення сорочки ---");
        String type = readStringInput("Тип сорочки: ");
        Size size = readSizeFromUser();
        double price = readDoubleInput("Цiна: ");
        String brand = readStringInput("Бренд: ");
        Material material = readMaterialFromUser();
        boolean hasPocket = readBooleanInput("Наявнiсть кишенi (так/нi): ");
        String collarType = readStringInput("Тип комiра: ");
        return new Shirt(type, size, price, brand, material, hasPocket, collarType);
    }

    private static Clothes createPants() {
        System.out.println("\n--- Створення штанів ---");
        String type = readStringInput("Тип штанів: ");
        Size size = readSizeFromUser();
        double price = readDoubleInput("Цiна: ");
        String brand = readStringInput("Бренд: ");
        Material material = readMaterialFromUser();
        int length = readIntInput("Довжина штанів (30-120 см): ");
        boolean hasSuspenders = readBooleanInput("Наявнiсть пiдтяжок (так/нi): ");
        return new Pants(type, size, price, brand, material, length, hasSuspenders);
    }

    private static Clothes createJacket() {
        System.out.println("\n--- Створення куртки ---");
        String type = readStringInput("Тип куртки: ");
        Size size = readSizeFromUser();
        double price = readDoubleInput("Цiна: ");
        String brand = readStringInput("Бренд: ");
        Material material = readMaterialFromUser();
        boolean hasHood = readBooleanInput("Наявнiсть капюшона (так/нi): ");
        boolean waterproof = readBooleanInput("Водонепроникна (так/нi): ");
        return new Jacket(type, size, price, brand, material, hasHood, waterproof);
    }

    private static Clothes createSweater() {
        System.out.println("\n--- Створення светра ---");
        String type = readStringInput("Тип светра: ");
        Size size = readSizeFromUser();
        double price = readDoubleInput("Цiна: ");
        String brand = readStringInput("Бренд: ");
        Material material = readMaterialFromUser();
        boolean hasZip = readBooleanInput("Наявнiсть блискавки (так/нi): ");
        String neckType = readStringInput("Тип горловини: ");
        return new Sweater(type, size, price, brand, material, hasZip, neckType);
    }

    private static Size readSizeFromUser() {
        while (true) {
            try {
                String input = readStringInput("Розмiр (S/M/L/XL): ");
                return Size.fromString(input);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage() + ". Спробуйте ще раз.");
            }
        }
    }

    private static Material readMaterialFromUser() {
        while (true) {
            try {
                String input = readStringInput("Матерiал (COTTON/POLYESTER/WOOL/DENIM): ");
                return Material.fromString(input);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage() + ". Спробуйте ще раз.");
            }
        }
    }

    private static boolean readBooleanInput(String prompt) {
        while (true) {
            String input = readStringInput(prompt);
            if (input.equalsIgnoreCase("так") || input.equalsIgnoreCase("true") || input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y")) {
                return true;
            }
            if (input.equalsIgnoreCase("нi") || input.equalsIgnoreCase("false") || input.equalsIgnoreCase("no") || input.equalsIgnoreCase("n")) {
                return false;
            }
            System.out.println("Введiть 'так' або 'нi'");
        }
    }

    private static int readIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Помилка: введiть цiле число!");
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
                System.out.println("Помилка: введiть число!");
                scanner.next();
            }
        }
    }

    private static String readStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.next().trim();
    }
}