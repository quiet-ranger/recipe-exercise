package com.example.sfgrecipe.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    Category catA, catB;

    @BeforeEach
    public void setUp() {
        catA = new Category();
        catA.setDescription("Class A");
        catB = new Category();
        catB.setDescription("Class B");
    }

    @Test
    void testEquals() {
        assertTrue(catA.equals(catA),"Objects differ");
        assertFalse(catA.equals(catB), "Objects do not differ");
    }

    @Test
    void testHashCode() {
        int aHash = catA.hashCode();
        int bHash = catB.hashCode();
        assertNotEquals(aHash, bHash, "Codes should be different");
    }

}