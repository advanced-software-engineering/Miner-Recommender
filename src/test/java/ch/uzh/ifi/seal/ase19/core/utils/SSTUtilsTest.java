package ch.uzh.ifi.seal.ase19.core.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SSTUtilsTest {

    @Test
    void getFullyQualifiedNameWithoutGenerics() {
        String fullyQualifiedName = "com.Other`?`";

        Assertions.assertEquals("com.Other", SSTUtils.getFullyQualifiedNameWithoutGenerics(fullyQualifiedName));
    }
}