package com.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть кількість елементів одягу: ");
        int n = scanner.nextInt();
        scanner.nextLine();

        Clothes[] wardrobe = new Clothes[n];
        for (int i = 0; i < n; i++) {
            System.out.println("Одяг #" + (i + 1));
            System.out.print("Тип: ");
            String type = scanner.nextLine();
            System.out.print("Розмір (S/M/L/XL): ");
            String size = scanner.nextLine();
            System.out.print("Ціна: ");
            double price = scanner.nextDouble();
            scanner.nextLine();
            wardrobe[i] = new Clothes(type, size, price);
        }

        System.out.println("\nСписок одягу:");
        for (Clothes c : wardrobe) {
            System.out.println(c);
        }
        scanner.close();
    }
}