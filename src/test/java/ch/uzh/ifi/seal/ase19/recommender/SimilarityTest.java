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


    /*
        There are up to 8 things to compare
         1 resultType
         2 receiverType
         3 surroundingType
         4 objectOrigin
         5 requiredType
           from enclosingMethodSignature:
         6 fullyQualifiedReturnType;
         7 number of parameters
         8 parameterSimilarity (for each parameter look at name and type)

         All 8 contribute equally to similarity, except resultType
         If resultType is unequal similarity is 0

     */

    @Test
    public void testSimilarity(){
        Query q1 = new Query(ResultType.METHOD_INVOCATION, "System.IO.StreamReader", SurroundingExpression.ASSIGNMENT, ObjectOrigin.LOCAL, "System.String", null);
        Query q2 = new Query(ResultType.METHOD_INVOCATION, "System.IO.StreamReader", SurroundingExpression.ASSIGNMENT, ObjectOrigin.LOCAL, "System.String", null);

        double similarity = Similarity.calculateSimilarity(q1,q2);
        // 8 out of 8 things are similar
        Assertions.assertEquals(1, similarity);
    }


    @Test
    public void testSimilarity2(){
        Query q1 = new Query(ResultType.METHOD_INVOCATION, "System.IO.StreamReader", SurroundingExpression.ASSIGNMENT, ObjectOrigin.LOCAL, "System.String", null);
        Query q2 = new Query(ResultType.METHOD_INVOCATION, "System.IO.StreamReader", SurroundingExpression.LOOP, ObjectOrigin.LOCAL, "System.String", null);

        double similarity = Similarity.calculateSimilarity(q1,q2);
        // 7 out of 8 things are similar, should return, (mapped to a range of 0 to 1), the value 0.875 -> rounded 0.88
        Assertions.assertEquals(0.88, similarity);
    }

    @Test
    public void testSimilarity3(){
        Query q1 = new Query(ResultType.METHOD_INVOCATION, "System.IO.StreamReader", SurroundingExpression.ASSIGNMENT, ObjectOrigin.LOCAL, "System.String", null);
        Query q2 = new Query(ResultType.METHOD_INVOCATION, "SomethingElse", SurroundingExpression.LOOP, ObjectOrigin.LOCAL, "System.String", null);

        double similarity = Similarity.calculateSimilarity(q1,q2);
        // 6 out of 8 things are similar, should return, (mapped to a range of 0 to 1), the value 0.75 -> rounded 0.75
        Assertions.assertEquals(0.75, similarity);
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
        // 8 out of 8 things are similar
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
        // 7 out of 8 things are similar 0.875 rounded -> 0.88
        Assertions.assertEquals(0.88, similarity);
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
        // EnclosingMethodSignature m2 has one parameter more (with not null name), 6.875 out of 8  things are the same, the value 0.859375 is rounded to 0.86
        // Param Name is weighted *0.5, Param Type is weighted *1.5
        // Everyting is the same except ParameterListLength 8-1 = 7,
        // all params are the same except one has different name --> (1/paramListLenght*2) * weight = (1/2*2) * 0.5 = 0.125
        // 7 - 0.125 = 6.875
        Assertions.assertEquals(0.86, similarity);
        //order of comparison should not matter!
        Assertions.assertTrue(similarity == similarityTransitive);
    }

    @Test
    public void testSimilarity7(){
       Query q1 = new Query(null,null,null,null,null,null);
        Query q2 = new Query(null,null,null,null,null,null);

        double similarity = Similarity.calculateSimilarity(q1,q2);
        // 8 out of 8 things are similar
        Assertions.assertEquals(1, similarity);
    }

    @Test
    public void testSimilarity8(){
        Query q1 = new Query(null,null,SurroundingExpression.LOOP,null,null,null);
        Query q2 = new Query(null,null,null,null,null,null);

        double similarity = Similarity.calculateSimilarity(q1,q2);
        // 7 out of 8 are similar 0.875 --> 0.88
        Assertions.assertEquals(0.88, similarity);
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

    @Test
    public void testSimilarity10(){




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

        double similarity = Similarity.calculateSimilarity(q1,q2);
        double similarity1 = Similarity.calculateSimilarity(q2,q3);

        System.out.println(similarity +"   "+similarity1);
        // q2 and q3 should be more similar than q1 and q2
        Assertions.assertTrue(similarity < similarity1);

    }

    public void testSimilarity11(){
        Query q1 = new Query(ResultType.METHOD_INVOCATION, "System.IO.StreamReader", SurroundingExpression.ASSIGNMENT, ObjectOrigin.LOCAL, "System.String", null);
        Query q2 = new Query(null, "System.IO.StreamReader", SurroundingExpression.ASSIGNMENT, ObjectOrigin.LOCAL, "System.String", null);

        double similarity = Similarity.calculateSimilarity(q1,q2);
        // a ReceiverType is null --> 0 similarity
        Assertions.assertEquals(0, similarity);
    }
}
