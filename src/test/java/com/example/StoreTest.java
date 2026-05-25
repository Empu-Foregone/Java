package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StoreTest {
    private Store store;
    private Clothes testClothes;

    @BeforeEach
    void setUp() {
        store = new Store("Test Store", "Test Address");
        testClothes = new Clothes("T-Shirt", Size.M, 100, "Nike", Material.COTTON) {};
        store.addNewClothes(testClothes, 5);
    }

    @Test
    void shouldThrowObjectNotFoundExceptionWhenUpdatingNonExistingObject() {
        Clothes nonExisting = new Clothes("NonExisting", Size.S, 50, "Unknown", Material.POLYESTER) {};
        
        assertThrows(ObjectNotFoundException.class, () -> {
            store.update(nonExisting, testClothes);
        });
    }

    @Test
    void shouldThrowObjectNotFoundExceptionWhenDeletingNonExistingObject() {
        Clothes nonExisting = new Clothes("NonExisting", Size.S, 50, "Unknown", Material.POLYESTER) {};
        
        assertThrows(ObjectNotFoundException.class, () -> {
            store.delete(nonExisting);
        });
    }

    @Test
    void shouldThrowInvalidFieldValueExceptionWhenAddingNullClothes() {
        assertThrows(InvalidFieldValueException.class, () -> {
            store.addNewClothes(null, 5);
        });
    }

    @Test
    void shouldThrowInvalidFieldValueExceptionWhenAddingZeroQuantity() {
        assertThrows(InvalidFieldValueException.class, () -> {
            store.addNewClothes(testClothes, 0);
        });
    }
}