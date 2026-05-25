package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Collections;

class ClothesTest {

    // Допоміжний метод для створення анонімного Clothes (бо він абстрактний)
    private Clothes createClothes(String type, Size size, double price, String brand, Material material) {
        return new Clothes(type, size, price, brand, material) {};
    }

    @Test
    void testClothesConstructor() {
        Clothes c = createClothes("Shirt", Size.M, 100, "Nike", Material.COTTON);
        assertEquals("Shirt", c.getType());
        assertEquals(Size.M, c.getSize());
        assertEquals(100, c.getPrice());
        assertEquals("Nike", c.getBrand());
        assertEquals(Material.COTTON, c.getMaterial());
    }

    @Test
    void testShirtConstructorAndToString() {
        Shirt s = new Shirt("Polo", Size.L, 150, "Adidas", Material.COTTON, true, "класичний");
        assertTrue(s.isHasPocket());
        assertEquals("класичний", s.getCollarType());
        assertTrue(s.toString().contains("Сорочка"));
    }

    @Test
    void testPantsConstructorAndToString() {
        Pants p = new Pants("Jeans", Size.XL, 200, "Levi's", Material.DENIM, 100, false);
        assertEquals(100, p.getLength());
        assertFalse(p.isHasSuspenders());
        assertTrue(p.toString().contains("Штани"));
    }

    @Test
    void testJacketCreation() {
        Jacket j = new Jacket("Winter", Size.L, 1500, "Nike", Material.POLYESTER, true, true);
        assertTrue(j.isHasHood());
        assertTrue(j.isWaterproof());
        assertTrue(j.toString().contains("Куртка"));
    }

    @Test
    void testSweaterCreation() {
        Sweater s = new Sweater("Warm", Size.M, 800, "Adidas", Material.WOOL, false, "круглий");
        assertFalse(s.isHasZip());
        assertEquals("круглий", s.getNeckType());
        assertTrue(s.toString().contains("Светр"));
    }

    @Test
    void testPolymorphismCollection() {
        Clothes c = createClothes("Basic", Size.M, 100, "Nike", Material.COTTON);
        Shirt s = new Shirt("Polo", Size.L, 150, "Adidas", Material.COTTON, true, "класичний");
        Pants p = new Pants("Jeans", Size.XL, 200, "Levi's", Material.DENIM, 100, false);
        Jacket j = new Jacket("Winter", Size.L, 1500, "Nike", Material.POLYESTER, true, true);
        Sweater sw = new Sweater("Warm", Size.M, 800, "Adidas", Material.WOOL, false, "круглий");

        assertTrue(c instanceof Clothes);
        assertTrue(s instanceof Clothes);
        assertTrue(p instanceof Clothes);
        assertTrue(j instanceof Clothes);
        assertTrue(sw instanceof Clothes);
    }

    @Test
    void testCompareToSorting() {
        Clothes c1 = createClothes("Shirt", Size.M, 100, "Nike", Material.COTTON);
        Clothes c2 = createClothes("Jacket", Size.L, 200, "Adidas", Material.POLYESTER);
        Clothes c3 = createClothes("Pants", Size.S, 150, "Puma", Material.DENIM);
        
        ArrayList<Clothes> list = new ArrayList<>();
        list.add(c1);
        list.add(c2);
        list.add(c3);
        
        Collections.sort(list);
        
        assertEquals("Jacket", list.get(0).getType());
        assertEquals("Pants", list.get(1).getType());
        assertEquals("Shirt", list.get(2).getType());
    }

    @Test
    void testNegativePriceThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            createClothes("Shirt", Size.M, -50, "Nike", Material.COTTON);
        });
    }
}