package ch.uzh.ifi.seal.ase19.recommender;

import ch.uzh.ifi.seal.ase19.core.models.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SimilarityTest {

    @Test
    public void testSimilarity(){
        Query q1 = new Query(ResultType.METHOD_INVOCATION, "System.IO.StreamReader", SurroundingExpression.ASSIGNMENT, ObjectOrigin.LOCAL, "System.String", null);
        Query q2 = new Query(ResultType.METHOD_INVOCATION, "System.IO.StreamReader", SurroundingExpression.ASSIGNMENT, ObjectOrigin.LOCAL, "System.String", null);

        double similarity = Similarity.calculateSimilarity(q1,q2);
        Assertions.assertEquals(1, similarity);
    }


    @Test
    public void testSimilarity2(){
        Query q1 = new Query(ResultType.METHOD_INVOCATION, "System.IO.StreamReader", SurroundingExpression.ASSIGNMENT, ObjectOrigin.LOCAL, "System.String", null);
        Query q2 = new Query(ResultType.METHOD_INVOCATION, "System.IO.StreamReader", SurroundingExpression.LOOP, ObjectOrigin.LOCAL, "System.String", null);

        double similarity = Similarity.calculateSimilarity(q1,q2);
        // 5 out of 6 things are similar, should return, (mapped to a range of 0 to 1), the value 0.83333333 -> rounded 0.83
        Assertions.assertEquals(0.83, similarity);
    }

    @Test
    public void testSimilarity3(){
        Query q1 = new Query(ResultType.METHOD_INVOCATION, "System.IO.StreamReader", SurroundingExpression.ASSIGNMENT, ObjectOrigin.LOCAL, "System.String", null);
        Query q2 = new Query(ResultType.METHOD_INVOCATION, "SomethingElse", SurroundingExpression.LOOP, ObjectOrigin.LOCAL, "System.String", null);

        double similarity = Similarity.calculateSimilarity(q1,q2);
        // 4 out of 6 things are similar, should return, (mapped to a range of 0 to 1), the value 0.666666 -> rounded 0.67
        Assertions.assertEquals(0.67, similarity);
    }


    @Test
    public void testSimilarity4(){
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

        double similarity = Similarity.calculateSimilarity(q1,q2);
        // EncosingMethodSignature has the same amount of params in q1 and q2
        Assertions.assertEquals(1, similarity);
    }

    @Test
    public void testSimilarity5(){
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

        double similarity = Similarity.calculateSimilarity(q1,q2);
        // EncosingMethodSignature m2 has one parameter more, 10 out of 11 things are the same, the value 0.9090909090909091 is rounded to 0.91
        Assertions.assertEquals(0.91, similarity);
    }

    @Test
    public void testSimilarity6(){
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

        double similarity = Similarity.calculateSimilarity(q1,q2);
        double similarityTransitive = Similarity.calculateSimilarity(q2,q1);
        // EncosingMethodSignature m2 has one parameter more, 9 out of 11 things are the same, the value 0.8181818181818182 is rounded to 0.82
        Assertions.assertEquals(0.82, similarity);
        //order of comparison should not matter!
        Assertions.assertTrue(similarity == similarityTransitive);
    }

    @Test
    public void testSimilarity7(){
       Query q1 = new Query(null,null,null,null,null,null);
        Query q2 = new Query(null,null,null,null,null,null);

        double similarity = Similarity.calculateSimilarity(q1,q2);
        Assertions.assertEquals(1, similarity);
    }

    @Test
    public void testSimilarity8(){
        Query q1 = new Query(null,null,SurroundingExpression.LOOP,null,null,null);
        Query q2 = new Query(null,null,null,null,null,null);

        double similarity = Similarity.calculateSimilarity(q1,q2);
        // 5 out of 6 are similar
        Assertions.assertEquals(0.83, similarity);
    }


    @Test
    public void testSimilarity9(){

        Query q1 = null;
        Query q2 = null;
        EnclosingMethodSignature enclosingMethodSignature = mock(EnclosingMethodSignature.class);
        Query q3 = new Query(ResultType.METHOD_INVOCATION, "System.IO.StreamReader", SurroundingExpression.ASSIGNMENT, ObjectOrigin.LOCAL, "System.String", enclosingMethodSignature);
        double similarity = Similarity.calculateSimilarity(q1,q2);
        Assertions.assertEquals(1, similarity);
        double similarity2 = Similarity.calculateSimilarity(q1,q3);
        Assertions.assertEquals(0, similarity2);
        double similarity3 = Similarity.calculateSimilarity(q3,q1);
        Assertions.assertEquals(0, similarity3);
    }
}
