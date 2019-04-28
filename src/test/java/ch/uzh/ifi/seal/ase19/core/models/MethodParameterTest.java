package ch.uzh.ifi.seal.ase19.core.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MethodParameterTest {

    @Test
    void testEquals() {
        MethodParameter mp1 = new MethodParameter("name", "com.Other");
        MethodParameter mp2 = new MethodParameter("name", "com.Other");

        Assertions.assertEquals(mp1, mp2);
    }

    @Test
    void notEquals() {
        MethodParameter mp1 = new MethodParameter("name", "com.Other");
        MethodParameter mp2 = new MethodParameter("otherName", "com.Other");

        Assertions.assertNotEquals(mp1, mp2);
    }

    @Test
    void testHashCode() {
        MethodParameter mp1 = new MethodParameter("name", "com.Other");
        MethodParameter mp2 = new MethodParameter("name", "com.Other");

        Assertions.assertEquals(mp1.hashCode(), mp2.hashCode());
    }

    @Test
    void notSameHashCode() {
        MethodParameter mp1 = new MethodParameter("name", "com.Other");
        MethodParameter mp2 = new MethodParameter("otherName", "com.Other");

        Assertions.assertNotEquals(mp1.hashCode(), mp2.hashCode());
    }
}