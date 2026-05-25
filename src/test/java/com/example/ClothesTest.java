package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClothesTest {

    @Test
    void testClothesConstructor() {
        Clothes c = new Clothes("Shirt", Size.M, 100, "Nike", Material.COTTON);
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
    void testPolymorphismCollection() {
        Clothes c = new Clothes("Basic", Size.M, 100, "Nike", Material.COTTON);
        Shirt s = new Shirt("Polo", Size.L, 150, "Adidas", Material.COTTON, true, "класичний");
        Pants p = new Pants("Jeans", Size.XL, 200, "Levi's", Material.DENIM, 100, false);
        
        assertTrue(c instanceof Clothes);
        assertTrue(s instanceof Clothes);
        assertTrue(p instanceof Clothes);
        assertTrue(s instanceof Shirt);
        assertTrue(p instanceof Pants);
    }

    @Test
    void testNegativePriceThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Clothes("Shirt", Size.M, -50, "Nike", Material.COTTON);
        });
    }
}