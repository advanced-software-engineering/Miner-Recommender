package ch.uzh.ifi.seal.ase19.core;

import cc.kave.commons.model.naming.codeelements.IMemberName;
import cc.kave.commons.model.naming.codeelements.IMethodName;
import cc.kave.commons.model.naming.impl.v0.codeelements.MethodName;
import cc.kave.commons.model.naming.types.ITypeName;
import ch.uzh.ifi.seal.ase19.core.models.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

// TODO update
class PersistenceManagerTest {
    private PersistenceManager sut;

    @BeforeEach
    void before() {
        try {
            String dir = Files.createTempDirectory("ase").toAbsolutePath().toString();
            sut = new PersistenceManager(dir);
        } catch (IOException e) {
        }
    }

    @Test
    void insert() {
        String fullyQualifiedName = "com.myClass";
        ResultType rt = ResultType.METHOD_INVOCATION;

        QuerySelection qs = mock(QuerySelection.class);
        when(qs.getReceiverType()).thenReturn(fullyQualifiedName);
        when(qs.getResultType()).thenReturn(rt);
        sut.saveModel(qs);

        ReceiverTypeQueries res = sut.load(fullyQualifiedName, rt);
        Assertions.assertNotEquals(0, res.getItems().size());
    }

    @Test
    void loadEmpty() {
        ReceiverTypeQueries res = sut.load("invalid", ResultType.METHOD_INVOCATION);
        Assertions.assertEquals(0, res.getItems().size());
    }

    @Test
    void load() {
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
        sut.saveModel(qs);

        ReceiverTypeQueries res = sut.load(fullyQualifiedName, rt);
        QuerySelection item = res.getItems().get(0);

        Assertions.assertTrue(qs.same(item));
    }
}