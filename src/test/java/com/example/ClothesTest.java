package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClothesTest {

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
    void testFileSaveLoad() {
    ArrayList<Clothes> original = new ArrayList<>();
    original.add(new Clothes("Test", Size.M, 100, "Nike", Material.COTTON));
    original.add(new Shirt("Polo", Size.L, 150, "Adidas", Material.COTTON, true, "класичний"));
    
    FileManager.saveToFile(original);
    ArrayList<Clothes> loaded = FileManager.loadFromFile();
    
    assertEquals(original.size(), loaded.size());
    assertEquals(original.get(0).getType(), loaded.get(0).getType());
    }

    @Test
    void testPolymorphismWithAllTypes() {
        Clothes c = new Clothes("Basic", Size.M, 100, "Nike", Material.COTTON);
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
}