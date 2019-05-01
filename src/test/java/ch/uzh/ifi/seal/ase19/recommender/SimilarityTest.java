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

        double similarity = new Similarity(q1,q2).calculate();
        Assertions.assertEquals(1, similarity);
    }


    @Test
    public void testSimilarity2(){
        Query q1 = new Query(ResultType.METHOD_INVOCATION, "System.IO.StreamReader", SurroundingExpression.ASSIGNMENT, ObjectOrigin.LOCAL, "System.String", null);
        Query q2 = new Query(ResultType.METHOD_INVOCATION, "System.IO.StreamReader", SurroundingExpression.LOOP, ObjectOrigin.LOCAL, "System.String", null);

        double similarity = new Similarity(q1,q2).calculate();
        // 4 out of 5 things are similar, should return, (mapped to a range of 0 to 1), the value 0.8
        Assertions.assertEquals(0.8, similarity);
    }

    @Test
    public void testSimilarity3(){
        Query q1 = new Query(ResultType.METHOD_INVOCATION, "System.IO.StreamReader", SurroundingExpression.ASSIGNMENT, ObjectOrigin.LOCAL, "System.String", null);
        Query q2 = new Query(ResultType.METHOD_INVOCATION, "SomethingElse", SurroundingExpression.LOOP, ObjectOrigin.LOCAL, "System.String", null);

        double similarity = new Similarity(q1,q2).calculate();
        // 4 out of 5 things are similar, should return, (mapped to a range of 0 to 1), the value 0.6
        Assertions.assertEquals(0.6, similarity);
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

        double similarity = new Similarity(q1,q2).calculate();
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

        double similarity = new Similarity(q1,q2).calculate();
        // EncosingMethodSignature m2 has one parameter more, 9 out of 10 things are the same, the value 0.9
        Assertions.assertEquals(0.9, similarity);
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

        double similarity = new Similarity(q1,q2).calculate();
        double similarityTransitive = new Similarity(q2,q1).calculate();
        // EncosingMethodSignature m2 has one parameter more, 8 out of 10 things are the same, the value 0.8
        Assertions.assertEquals(0.8, similarity);
        //order of comparison should not matter!
        Assertions.assertEquals(similarity, similarityTransitive);
    }

    @Test
    public void testSimilarity7(){
       Query q1 = new Query(ResultType.METHOD_INVOCATION,null,null,null,null,null);
        Query q2 = new Query(ResultType.METHOD_INVOCATION,null,null,null,null,null);

        double similarity = new Similarity(q1,q2).calculate();
        Assertions.assertEquals(1, similarity);
    }

    @Test
    public void testSimilarity8(){
        Query q1 = new Query(null,null,SurroundingExpression.LOOP,null,null,null);
        Query q2 = new Query(ResultType.METHOD_INVOCATION,null,null,null,null,null);

        double similarity = new Similarity(q1,q2).calculate();
        // not same result type
        Assertions.assertEquals(0.0, similarity);
    }


    @Test
    public void testSimilarity9(){

        Query q1 = null;
        Query q2 = null;
        EnclosingMethodSignature enclosingMethodSignature = mock(EnclosingMethodSignature.class);
        Query q3 = new Query(ResultType.METHOD_INVOCATION, "System.IO.StreamReader", SurroundingExpression.ASSIGNMENT, ObjectOrigin.LOCAL, "System.String", enclosingMethodSignature);
        double similarity = new Similarity(q1,q2).calculate();
        Assertions.assertEquals(1, similarity);
        double similarity2 = new Similarity(q1,q3).calculate();
        Assertions.assertEquals(0, similarity2);
        double similarity3 = new Similarity(q3,q1).calculate();
        Assertions.assertEquals(0, similarity3);
    }
}
