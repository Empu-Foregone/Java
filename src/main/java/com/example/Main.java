package com.example;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Драйвер програми.
 * Демонструє: статичний лічильник, конструктор копіювання, агрегацію, enum.
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static Wardrobe wardrobe = null;

    public static void main(String[] args) {
        System.out.println("=== Система управління гардеробом ===");
        initWardrobe();

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readIntInput("Оберіть опцію: ");

            switch (choice) {
                case 1:
                    createAndAddClothes();
                    break;
                case 2:
                    displayAllClothes();
                    break;
                case 3:
                    displayStatistics();
                    break;
                case 4:
                    demonstrateCopyConstructor();
                    break;
                case 5:
                    System.out.println("До побачення!");
                    running = false;
                    break;
                default:
                    System.out.println("❌ Некоректний вибір. Спробуйте ще раз.");
            }
        }
        scanner.close();
    }

    private static void initWardrobe() {
        int capacity = readIntInput("Введіть місткість шафи (1-20): ");
        while (capacity < 1 || capacity > 20) {
            System.out.println("❌ Місткість має бути від 1 до 20");
            capacity = readIntInput("Введіть місткість шафи (1-20): ");
        }
        wardrobe = new Wardrobe(capacity);
        System.out.println("✅ Шафу створено на " + capacity + " предметів");
    }

    private static void printMenu() {
        System.out.println("\n=== МЕНЮ ===");
        System.out.println("1. Додати новий одяг до шафи");
        System.out.println("2. Показати весь одяг у шафі");
        System.out.println("3. Статистика (всього створено, заповненість)");
        System.out.println("4. Продемонструвати конструктор копіювання");
        System.out.println("5. Завершити роботу");
    }

    private static void createAndAddClothes() {
        if (wardrobe.isFull()) {
            System.out.println("❌ Шафа повна! Неможливо додати новий предмет.");
            return;
        }

        System.out.println("\n--- Створення нового одягу ---");
        System.out.println("Доступні розміри: S, M, L, XL");
        System.out.println("Доступні матеріали: COTTON, POLYESTER, WOOL, DENIM");

        String type = readStringInput("Тип одягу: ");
        Size size = readSizeFromUser();
        double price = readDoubleInput("Ціна: ");
        String brand = readStringInput("Бренд: ");
        Material material = readMaterialFromUser();

        try {
            Clothes item = new Clothes(type, size, price, brand, material);
            if (wardrobe.addItem(item)) {
                System.out.println("✅ Одяг успішно додано до шафи!");
                System.out.println("Всього створено об'єктів Clothes: " + Clothes.getTotalCreated());
                System.out.println(item);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Помилка створення: " + e.getMessage());
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

    private static void displayAllClothes() {
        if (wardrobe.isEmpty()) {
            System.out.println("📭 Шафа порожня.");
            return;
        }

        System.out.println("\n=== Вміст шафи ===");
        Clothes[] items = wardrobe.getAllItems();
        for (int i = 0; i < items.length; i++) {
            System.out.println((i + 1) + ". " + items[i]);
        }
        System.out.println("Заповнено: " + wardrobe.getCount() + "/" + wardrobe.getCapacity());
    }

    private static void displayStatistics() {
        System.out.println("\n=== СТАТИСТИКА ===");
        System.out.println("Всього створено об'єктів Clothes: " + Clothes.getTotalCreated());
        System.out.println("Поточна шафа: " + wardrobe.getCount() + "/" + wardrobe.getCapacity());
        System.out.println("Шафа заповнена: " + (wardrobe.isFull() ? "Так" : "Ні"));
    }

    private static void demonstrateCopyConstructor() {
        if (wardrobe.isEmpty()) {
            System.out.println("📭 Шафа порожня. Додайте хоча б один одяг перед демонстрацією.");
            return;
        }

        Clothes original = wardrobe.getItem(0);
        Clothes copy = new Clothes(original);

        System.out.println("\n=== Демонстрація конструктора копіювання ===");
        System.out.println("Оригінал: " + original);
        System.out.println("Копія:    " + copy);
        System.out.println("Копія є окремим об'єктом: " + (original != copy));
        System.out.println("Але вміст однаковий: " + original.equals(copy));
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

    private static String readStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.next().trim();
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
}