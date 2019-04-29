package ch.uzh.ifi.seal.ase19.core.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SSTUtilsTest {

    @Test
    void getFullyQualifiedNameWithoutGenerics1() {
        String fullyQualifiedName = "com.Other`?`";

        Assertions.assertEquals("com.Other", SSTUtils.getFullyQualifiedNameWithoutGenerics(fullyQualifiedName));
    }

    @Test
    void getFullyQualifiedNameWithoutGenerics2() {
        String fullyQualifiedName = "?";

        Assertions.assertEquals("", SSTUtils.getFullyQualifiedNameWithoutGenerics(fullyQualifiedName));
    }
}