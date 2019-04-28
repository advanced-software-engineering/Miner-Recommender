package ch.uzh.ifi.seal.ase19.core.models;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;

class QueryTest {

    @Test
    void testEquals() {
        EnclosingMethodSignature ems = mock(EnclosingMethodSignature.class);
        Query q1 = new Query(ResultType.METHOD_INVOCATION, "com.Other", SurroundingExpression.ASSIGNMENT, ObjectOrigin.PARAMETER, "com.Type", ems);
        Query q2 = new Query(ResultType.METHOD_INVOCATION, "com.Other", SurroundingExpression.ASSIGNMENT, ObjectOrigin.PARAMETER, "com.Type", ems);

        Assertions.assertEquals(q1, q2);
    }

    @Test
    void notEquals() {
        EnclosingMethodSignature ems = mock(EnclosingMethodSignature.class);
        Query q1 = new Query(ResultType.METHOD_INVOCATION, "com.Other", SurroundingExpression.ASSIGNMENT, ObjectOrigin.PARAMETER, "com.Type", ems);
        Query q2 = new Query(ResultType.METHOD_INVOCATION, "com.Other", SurroundingExpression.LOOP, ObjectOrigin.PARAMETER, "com.Type", ems);

        Assertions.assertNotEquals(q1, q2);
    }

    @Test
    void testHashCode() {
        EnclosingMethodSignature ems = mock(EnclosingMethodSignature.class);
        Query q1 = new Query(ResultType.METHOD_INVOCATION, "com.Other", SurroundingExpression.ASSIGNMENT, ObjectOrigin.PARAMETER, "com.Type", ems);
        Query q2 = new Query(ResultType.METHOD_INVOCATION, "com.Other", SurroundingExpression.ASSIGNMENT, ObjectOrigin.PARAMETER, "com.Type", ems);

        Assertions.assertEquals(q1.hashCode(), q2.hashCode());
    }

    @Test
    void notSameHashCode() {
        EnclosingMethodSignature ems = mock(EnclosingMethodSignature.class);
        Query q1 = new Query(ResultType.METHOD_INVOCATION, "com.Other", SurroundingExpression.ASSIGNMENT, ObjectOrigin.PARAMETER, "com.Type", ems);
        Query q2 = new Query(ResultType.METHOD_INVOCATION, "com.Other", SurroundingExpression.LOOP, ObjectOrigin.PARAMETER, "com.Type", ems);

        Assertions.assertNotEquals(q1.hashCode(), q2.hashCode());
    }
}