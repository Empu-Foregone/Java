package com.example;

import java.io.*;
import java.util.ArrayList;

/**
 * Клас для роботи з файлами: збереження та завантаження колекції.
 * Формат файлу: text (кожен об'єкт на кількох рядках, починається з типу)
 */
public class FileManager {
    private static final String FILE_NAME = "wardrobe.txt";

    /**
     * Зберігає колекцію одягу у файл.
     * @param wardrobe колекція для збереження
     */
    public static void saveToFile(ArrayList<Clothes> wardrobe) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Clothes item : wardrobe) {
                writer.println(item.getClassType());
                if (item instanceof Clothes && !(item instanceof Shirt) && !(item instanceof Pants) && !(item instanceof Jacket) && !(item instanceof Sweater)) {
                    // Звичайний Clothes
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
            System.out.println("✅ Колекцію збережено у файл: " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("❌ Помилка збереження: " + e.getMessage());
        }
    }

    /**
     * Завантажує колекцію одягу з файлу.
     * @return завантажена колекція (може бути порожньою)
     */
    public static ArrayList<Clothes> loadFromFile() {
        ArrayList<Clothes> wardrobe = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("ℹ️ Файл " + FILE_NAME + " не знайдено. Починаємо з порожньої колекції.");
            return wardrobe;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String classType = line.trim();
                try {
                    switch (classType) {
                        case "Clothes":
                            String type = reader.readLine();
                            Size size = Size.valueOf(reader.readLine());
                            double price = Double.parseDouble(reader.readLine());
                            String brand = reader.readLine();
                            Material material = Material.valueOf(reader.readLine());
                            wardrobe.add(new Clothes(type, size, price, brand, material));
                            break;
                        case "Shirt":
                            String sType = reader.readLine();
                            Size sSize = Size.valueOf(reader.readLine());
                            double sPrice = Double.parseDouble(reader.readLine());
                            String sBrand = reader.readLine();
                            Material sMaterial = Material.valueOf(reader.readLine());
                            boolean hasPocket = Boolean.parseBoolean(reader.readLine());
                            String collarType = reader.readLine();
                            wardrobe.add(new Shirt(sType, sSize, sPrice, sBrand, sMaterial, hasPocket, collarType));
                            break;
                        case "Pants":
                            String pType = reader.readLine();
                            Size pSize = Size.valueOf(reader.readLine());
                            double pPrice = Double.parseDouble(reader.readLine());
                            String pBrand = reader.readLine();
                            Material pMaterial = Material.valueOf(reader.readLine());
                            int length = Integer.parseInt(reader.readLine());
                            boolean hasSuspenders = Boolean.parseBoolean(reader.readLine());
                            wardrobe.add(new Pants(pType, pSize, pPrice, pBrand, pMaterial, length, hasSuspenders));
                            break;
                        case "Jacket":
                            String jType = reader.readLine();
                            Size jSize = Size.valueOf(reader.readLine());
                            double jPrice = Double.parseDouble(reader.readLine());
                            String jBrand = reader.readLine();
                            Material jMaterial = Material.valueOf(reader.readLine());
                            boolean hasHood = Boolean.parseBoolean(reader.readLine());
                            boolean waterproof = Boolean.parseBoolean(reader.readLine());
                            wardrobe.add(new Jacket(jType, jSize, jPrice, jBrand, jMaterial, hasHood, waterproof));
                            break;
                        case "Sweater":
                            String swType = reader.readLine();
                            Size swSize = Size.valueOf(reader.readLine());
                            double swPrice = Double.parseDouble(reader.readLine());
                            String swBrand = reader.readLine();
                            Material swMaterial = Material.valueOf(reader.readLine());
                            boolean hasZip = Boolean.parseBoolean(reader.readLine());
                            String neckType = reader.readLine();
                            wardrobe.add(new Sweater(swType, swSize, swPrice, swBrand, swMaterial, hasZip, neckType));
                            break;
                        default:
                            System.out.println("⚠️ Невідомий тип у файлі: " + classType);
                            // Пропускаємо рядки цього об'єкту (7 рядків)
                            for (int i = 0; i < 7; i++) reader.readLine();
                            break;
                    }
                } catch (Exception e) {
                    System.out.println("⚠️ Помилка читання об'єкта типу " + classType + ": " + e.getMessage());
                }
            }
            System.out.println("✅ Завантажено " + wardrobe.size() + " об'єктів з файлу " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("❌ Помилка читання файлу: " + e.getMessage());
        }
        return wardrobe;
    }
}