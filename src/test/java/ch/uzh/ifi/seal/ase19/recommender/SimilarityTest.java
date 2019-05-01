package ch.uzh.ifi.seal.ase19.recommender;

import ch.uzh.ifi.seal.ase19.core.models.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SimilarityTest {

    /*
        There are up to 7 things to compare
         1 receiverType
         2 surroundingType
         3 objectOrigin
         4 requiredType
           from enclosingMethodSignature:
         5 fullyQualifiedReturnType;
         6 number of parameters
         7 parameterSimilarity (for each parameter look at name and type)
         If resultType is unequal similarity is 0
    */

    @Test
    public void testSimilarity() {
        Query q1 = new Query(ResultType.METHOD_INVOCATION, "System.IO.StreamReader", SurroundingExpression.ASSIGNMENT, ObjectOrigin.LOCAL, "System.String", null);
        Query q2 = new Query(ResultType.METHOD_INVOCATION, "System.IO.StreamReader", SurroundingExpression.ASSIGNMENT, ObjectOrigin.LOCAL, "System.String", null);

        double similarity = new Similarity(q1, q2).calculate();
        // 7 out of 7 things are similar
        Assertions.assertEquals(1, similarity);
    }

    @Test
    public void testSimilarity2() {
        Query q1 = new Query(ResultType.METHOD_INVOCATION, "System.IO.StreamReader", SurroundingExpression.ASSIGNMENT, ObjectOrigin.LOCAL, "System.String", null);
        Query q2 = new Query(ResultType.METHOD_INVOCATION, "System.IO.StreamReader", SurroundingExpression.LOOP, ObjectOrigin.LOCAL, "System.String", null);

        double similarity = new Similarity(q1, q2).calculate();
        // 6 out of 7 things are similar, should return
        Assertions.assertEquals(0.85, similarity, 0.01);
    }

    @Test
    public void testSimilarity3() {
        Query q1 = new Query(ResultType.METHOD_INVOCATION, "System.IO.StreamReader", SurroundingExpression.ASSIGNMENT, ObjectOrigin.LOCAL, "System.String", null);
        Query q2 = new Query(ResultType.METHOD_INVOCATION, "SomethingElse", SurroundingExpression.LOOP, ObjectOrigin.LOCAL, "System.String", null);

        double similarity = new Similarity(q1, q2).calculate();
        // 5 out of 7 things are similar, should return
        Assertions.assertEquals(0.71, similarity, 0.01);
    }

    @Test
    public void testSimilarity4() {
        EnclosingMethodSignature enclosingMethodSignature1 = mock(EnclosingMethodSignature.class);
        MethodParameter methodParameter1 = mock(MethodParameter.class);
        when(enclosingMethodSignature1.getParameters()).thenReturn(new ArrayList<MethodParameter>(
                Arrays.asList(methodParameter1)));

        EnclosingMethodSignature enclosingMethodSignature2 = mock(EnclosingMethodSignature.class);
        MethodParameter methodParameter2 = mock(MethodParameter.class);
        when(enclosingMethodSignature2.getParameters()).thenReturn(new ArrayList<MethodParameter>(
                Arrays.asList(methodParameter2)));

        Query q1 = new Query(ResultType.METHOD_INVOCATION, "System.IO.StreamReader", SurroundingExpression.ASSIGNMENT, ObjectOrigin.LOCAL, "System.String", enclosingMethodSignature1);
        Query q2 = new Query(ResultType.METHOD_INVOCATION, "System.IO.StreamReader", SurroundingExpression.ASSIGNMENT, ObjectOrigin.LOCAL, "System.String", enclosingMethodSignature2);

        double similarity = new Similarity(q1, q2).calculate();
        // EncosingMethodSignature has the same amount of params in q1 and q2
        // 7 out of 7 things are similar
        Assertions.assertEquals(1, similarity);
    }

    @Test
    public void testSimilarity5() {
        EnclosingMethodSignature enclosingMethodSignature1 = mock(EnclosingMethodSignature.class);
        MethodParameter methodParameter1 = mock(MethodParameter.class);
        when(enclosingMethodSignature1.getParameters()).thenReturn(new ArrayList<MethodParameter>(
                Arrays.asList(methodParameter1)));

        EnclosingMethodSignature enclosingMethodSignature2 = mock(EnclosingMethodSignature.class);
        MethodParameter methodParameter2 = mock(MethodParameter.class);
        when(enclosingMethodSignature2.getParameters()).thenReturn(new ArrayList<MethodParameter>(
                Arrays.asList(methodParameter2, methodParameter2)));

        Query q1 = new Query(ResultType.METHOD_INVOCATION, "System.IO.StreamReader", SurroundingExpression.ASSIGNMENT, ObjectOrigin.LOCAL, "System.String", enclosingMethodSignature1);
        Query q2 = new Query(ResultType.METHOD_INVOCATION, "System.IO.StreamReader", SurroundingExpression.ASSIGNMENT, ObjectOrigin.LOCAL, "System.String", enclosingMethodSignature2);

        double similarity = new Similarity(q1, q2).calculate();
        // EncosingMethodSignature m2 has one parameter more, 6 out of 7 (parameter length not same)
        Assertions.assertEquals(0.85, similarity, 0.01);
    }

    @Test
    public void testSimilarity6() {
        EnclosingMethodSignature enclosingMethodSignature1 = mock(EnclosingMethodSignature.class);
        MethodParameter methodParameter1 = mock(MethodParameter.class);
        when(enclosingMethodSignature1.getParameters()).thenReturn(new ArrayList<MethodParameter>(
                Arrays.asList(methodParameter1)));

        EnclosingMethodSignature enclosingMethodSignature2 = mock(EnclosingMethodSignature.class);
        MethodParameter methodParameter2 = mock(MethodParameter.class);
        MethodParameter methodParameter3 = mock(MethodParameter.class);
        when(methodParameter2.getName()).thenReturn("MyTestName");
        when(enclosingMethodSignature2.getParameters()).thenReturn(new ArrayList<MethodParameter>(
                Arrays.asList(methodParameter2, methodParameter3)));

        Query q1 = new Query(ResultType.METHOD_INVOCATION, "System.IO.StreamReader", SurroundingExpression.ASSIGNMENT, ObjectOrigin.LOCAL, "System.String", enclosingMethodSignature1);
        Query q2 = new Query(ResultType.METHOD_INVOCATION, "System.IO.StreamReader", SurroundingExpression.ASSIGNMENT, ObjectOrigin.LOCAL, "System.String", enclosingMethodSignature2);

        double similarity = new Similarity(q1, q2).calculate();
        double similarityTransitive = new Similarity(q2, q1).calculate();
        /*
            same: receiverType, surroundingType, objectOrigin, requiredType (total +4)
            same fullyQualifiedReturnType of enclosingMethodSignature (+1)
            not same parameter length
            + 0.125 for same parameter name
            + 0.75 for same parameter type
         */
        // Everyting is the same except ParameterListLength 8-1 = 7,
        // all params are the same except one has different name --> (1/paramListLenght*2) * weight = (1/2*2) * 0.5 = 0.125
        // 7 - 1 - 0.125 = 5.875 (-1 for parameter length)
        Assertions.assertEquals(0.83, similarity, 0.01);
        //order of comparison should not matter!
        Assertions.assertEquals(similarity, similarityTransitive, 0.01);
    }

    @Test
    public void testSimilarity7() {
        Query q1 = new Query(ResultType.METHOD_INVOCATION, null, null, null, null, null);
        Query q2 = new Query(ResultType.METHOD_INVOCATION, null, null, null, null, null);

        double similarity = new Similarity(q1, q2).calculate();
        // 7 out of 7 things are similar
        Assertions.assertEquals(1, similarity);
    }

    @Test
    public void testSimilarity8() {
        Query q1 = new Query(null, null, SurroundingExpression.LOOP, null, null, null);
        Query q2 = new Query(ResultType.METHOD_INVOCATION, null, null, null, null, null);

        double similarity = new Similarity(q1, q2).calculate();
        // not same result type
        Assertions.assertEquals(0.0, similarity);
    }

    @Test
    public void testSimilarity9() {
        Query q1 = null;
        Query q2 = null;
        EnclosingMethodSignature enclosingMethodSignature = mock(EnclosingMethodSignature.class);
        Query q3 = new Query(ResultType.METHOD_INVOCATION, "System.IO.StreamReader", SurroundingExpression.ASSIGNMENT, ObjectOrigin.LOCAL, "System.String", enclosingMethodSignature);
        double similarity = new Similarity(q1, q2).calculate();
        Assertions.assertEquals(1, similarity);
        double similarity2 = new Similarity(q1, q3).calculate();
        Assertions.assertEquals(0, similarity2);
        double similarity3 = new Similarity(q3, q1).calculate();
        Assertions.assertEquals(0, similarity3);
    }

    @Test
    public void testSimilarity10() {
        EnclosingMethodSignature enclosingMethodSignature2 = mock(EnclosingMethodSignature.class);
        MethodParameter methodParameter2 = mock(MethodParameter.class);
        when(methodParameter2.getName()).thenReturn("MyTestName");
        when(enclosingMethodSignature2.getParameters()).thenReturn(new ArrayList<>(
                Arrays.asList(methodParameter2)));

        EnclosingMethodSignature enclosingMethodSignature3 = mock(EnclosingMethodSignature.class);
        MethodParameter methodParameter3 = mock(MethodParameter.class);
        when(methodParameter3.getName()).thenReturn("SomethingElse");
        when(enclosingMethodSignature3.getParameters()).thenReturn(new ArrayList<>(
                Arrays.asList(methodParameter3)));

        Query q1 = new Query(ResultType.METHOD_INVOCATION, "System.IO.StreamReader", SurroundingExpression.ASSIGNMENT, ObjectOrigin.LOCAL, "System.String", null);
        Query q2 = new Query(ResultType.METHOD_INVOCATION, "System.IO.StreamReader", SurroundingExpression.ASSIGNMENT, ObjectOrigin.LOCAL, "System.String", enclosingMethodSignature2);
        Query q3 = new Query(ResultType.METHOD_INVOCATION, "System.IO.StreamReader", SurroundingExpression.ASSIGNMENT, ObjectOrigin.LOCAL, "System.String", enclosingMethodSignature3);

        double similarity = new Similarity(q1, q2).calculate();
        double similarity1 = new Similarity(q2, q3).calculate();

        System.out.println(similarity + "   " + similarity1);
        // q2 and q3 should be more similar than q1 and q2
        Assertions.assertTrue(similarity < similarity1);

    }
}