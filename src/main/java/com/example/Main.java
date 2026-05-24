package com.example;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Драйвер програми з консольним меню.
 * Дозволяє створювати об'єкти Clothes та виводити їх список.
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ArrayList<Clothes> wardrobe = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("=== Система управління гардеробом ===");
        
        boolean running = true;
        while (running) {
            printMenu();
            int choice = readIntInput("Оберіть опцію: ");
            
            switch (choice) {
                case 1:
                    createClothes();
                    break;
                case 2:
                    displayAllClothes();
                    break;
                case 3:
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
        System.out.println("1. Створити новий об'єкт одягу");
        System.out.println("2. Вивести інформацію про всі об'єкти");
        System.out.println("3. Завершити роботу");
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
                System.out.println("❌ Помилка: введіть число (наприклад, 599.99)!");
                scanner.next();
            }
        }
    }

    private static void createClothes() {
        System.out.println("\n--- Створення нового одягу ---");
        
        String type = readStringInput("Тип одягу (футболка/джинси/куртка): ");
        String size = readStringInput("Розмір (S/M/L/XL): ");
        double price = readDoubleInput("Ціна: ");
        String brand = readStringInput("Бренд: ");

        try {
            Clothes item = new Clothes(type, size, price, brand);
            wardrobe.add(item);
            System.out.println("✅ Одяг успішно додано!");
            System.out.println(item);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Помилка створення: " + e.getMessage());
        }
    }

    private static void displayAllClothes() {
        if (wardrobe.isEmpty()) {
            System.out.println("📭 Список одягу порожній. Додайте хоча б один об'єкт.");
            return;
        }
        
        System.out.println("\n=== Список одягу ===");
        for (int i = 0; i < wardrobe.size(); i++) {
            System.out.println((i + 1) + ". " + wardrobe.get(i));
        }
        System.out.println("Всього об'єктів: " + wardrobe.size());
    }
}