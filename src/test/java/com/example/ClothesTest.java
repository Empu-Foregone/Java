package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClothesTest {

    @Test
    void testConstructorAndStaticCounter() {
        int before = Clothes.getTotalCreated();
        Clothes c1 = new Clothes("Shirt", Size.M, 100, "Nike", Material.COTTON);
        Clothes c2 = new Clothes("Jeans", Size.L, 200, "Levi's", Material.DENIM);
        
        assertEquals(before + 2, Clothes.getTotalCreated());
    }

    @Test
    void testCopyConstructor() {
        Clothes original = new Clothes("Shirt", Size.M, 100, "Nike", Material.COTTON);
        Clothes copy = new Clothes(original);
        
        assertNotSame(original, copy);
        assertEquals(original.getType(), copy.getType());
        assertEquals(original.getSize(), copy.getSize());
        assertEquals(original.getPrice(), copy.getPrice());
        assertEquals(original.getBrand(), copy.getBrand());
        assertEquals(original.getMaterial(), copy.getMaterial());
    }

    @Test
    void testNegativePriceThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Clothes("Shirt", Size.M, -50, "Nike", Material.COTTON);
        });
    }

    @Test
    void testWardrobeAggregation() {
        Wardrobe wardrobe = new Wardrobe(3);
        Clothes c1 = new Clothes("Shirt", Size.M, 100, "Nike", Material.COTTON);
        
        assertTrue(wardrobe.addItem(c1));
        assertEquals(1, wardrobe.getCount());
        assertEquals(3, wardrobe.getCapacity());
        assertFalse(wardrobe.isFull());
    }
}