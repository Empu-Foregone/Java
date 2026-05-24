package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClothesTest {

    @Test
    void testValidObjectCreation() {
        Clothes item = new Clothes("Shirt", "M", 100, "Nike");
        assertEquals("Shirt", item.getType());
        assertEquals("M", item.getSize());
        assertEquals(100, item.getPrice());
        assertEquals("Nike", item.getBrand());
    }

    @Test
    void testNegativePriceThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Clothes("Shirt", "M", -50, "Nike");
        });
    }

    @Test
    void testInvalidSizeThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Clothes("Shirt", "XXL", 100, "Nike");
        });
    }
}