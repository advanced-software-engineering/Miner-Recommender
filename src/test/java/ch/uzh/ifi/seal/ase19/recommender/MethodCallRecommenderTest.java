package ch.uzh.ifi.seal.ase19.recommender;

import cc.kave.commons.model.naming.codeelements.IMemberName;
import cc.kave.commons.model.naming.codeelements.IMethodName;
import cc.kave.commons.model.naming.impl.v0.codeelements.MethodName;
import cc.kave.commons.model.naming.types.ITypeName;
import ch.uzh.ifi.seal.ase19.core.PersistenceManager;
import ch.uzh.ifi.seal.ase19.core.models.*;
import ch.uzh.ifi.seal.ase19.miner.ContextProcessor;
import com.google.common.io.Files;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MethodCallRecommenderTest {

    private MethodCallRecommender sut;

    @BeforeEach
    void before() {
        String modelDirectory = Files.createTempDir().getAbsolutePath();
        PersistenceManager pm = new PersistenceManager(modelDirectory);
        ContextProcessor contextProcessor = new ContextProcessor(pm);
        sut = new MethodCallRecommender(contextProcessor, pm);
    }

    @Test
    void query() {
        Query query = mock(Query.class);
        when(query.getResultType()).thenReturn(ResultType.METHOD_INVOCATION);
        when(query.getReceiverType()).thenReturn("com.test");

        TreeSet<Pair<IMemberName, Double>> res = sut.query(query);
        Assertions.assertEquals(0, res.size());
    }

    @Test
    void queryWithDetails() {
        Query query = mock(Query.class);
        when(query.getResultType()).thenReturn(ResultType.METHOD_INVOCATION);
        when(query.getReceiverType()).thenReturn("com.other");

        Set<Pair<IMemberName, SimilarityDto>> res = sut.queryWithDetails(query);
        Assertions.assertEquals(0, res.size());
    }

    @Test
    void getLastModelSize() {
        Assertions.assertEquals(0, sut.getLastModelSize());
    }

    @Test
    void persist() {
        String fullyQualifiedName = "com.myClass";
        ResultType rt = ResultType.METHOD_INVOCATION;

        ITypeName tn = mock(ITypeName.class);
        when(tn.getFullName()).thenReturn(fullyQualifiedName);

        IMethodName m = mock(IMethodName.class);
        when(m.getDeclaringType()).thenReturn(tn);
        when(m.getFullName()).thenReturn(fullyQualifiedName);
        when(m.getReturnType()).thenReturn(tn);
        when(m.getParameters()).thenReturn(new ArrayList<>());

        EnclosingMethodSignature ems = new EnclosingMethodSignature(m);

        Query q = new Query(ResultType.METHOD_INVOCATION, fullyQualifiedName, SurroundingExpression.RETURN_STATEMENT, ObjectOrigin.CLASS, fullyQualifiedName, ems);

        IMemberName mn = new MethodName("myMethod");
        QuerySelection qs = new QuerySelection(q, mn);

        assertDoesNotThrow(() -> sut.persist(qs));
    }
}