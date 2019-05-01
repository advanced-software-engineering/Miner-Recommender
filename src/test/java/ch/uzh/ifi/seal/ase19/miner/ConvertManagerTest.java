package ch.uzh.ifi.seal.ase19.miner;

import cc.kave.commons.model.naming.codeelements.IFieldName;
import cc.kave.commons.model.naming.codeelements.IMethodName;
import cc.kave.commons.model.naming.types.ITypeName;
import cc.kave.commons.model.ssts.declarations.IMethodDeclaration;
import cc.kave.commons.model.ssts.expressions.assignable.IInvocationExpression;
import cc.kave.commons.model.ssts.impl.declarations.MethodDeclaration;
import cc.kave.commons.model.ssts.references.IVariableReference;
import cc.kave.commons.model.typeshapes.IMemberHierarchy;
import ch.uzh.ifi.seal.ase19.core.MethodInvocationContext;
import ch.uzh.ifi.seal.ase19.core.models.QuerySelection;
import ch.uzh.ifi.seal.ase19.core.models.ResultType;
import ch.uzh.ifi.seal.ase19.core.models.SurroundingExpression;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ConvertManagerTest {

    @Test
    void toQuerySelection() {
        String fullyQualifiedName = "com.myClass";

        ITypeName tp = mock(ITypeName.class);
        when(tp.getFullName()).thenReturn(fullyQualifiedName);

        IMethodName mn = mock(IMethodName.class);
        when(mn.getDeclaringType()).thenReturn(tp);
        when(mn.getReturnType()).thenReturn(tp);

        IMethodDeclaration md = mock(MethodDeclaration.class);
        when(md.getName()).thenReturn(mn);

        IVariableReference vr = mock(IVariableReference.class);
        when(vr.getIdentifier()).thenReturn("myVariable");
        when(vr.isMissing()).thenReturn(false);

        IInvocationExpression mi = mock(IInvocationExpression.class);
        when(mi.getMethodName()).thenReturn(mn);
        when(mi.getReference()).thenReturn(vr);

        MethodInvocationContext mic = new MethodInvocationContext();
        mic = mic.setMethodDeclaration(md);
        mic = mic.setSurroundingExpression(SurroundingExpression.ASSIGNMENT);
        mic = mic.setMethodInvocation(mi);
        Set<IMemberHierarchy<IMethodName>> mh = new HashSet<>();
        Set<IFieldName> f = new HashSet<>();

        ConvertManager sut = new ConvertManager(mic, mh, f);
        QuerySelection qs = sut.toQuerySelection();

        Assertions.assertEquals(1, qs.getFrequency());
        Assertions.assertEquals(mn, qs.getSelection());
        Assertions.assertEquals(fullyQualifiedName, qs.getReceiverType());
        Assertions.assertEquals(ResultType.METHOD_INVOCATION, qs.getResultType());
    }
}