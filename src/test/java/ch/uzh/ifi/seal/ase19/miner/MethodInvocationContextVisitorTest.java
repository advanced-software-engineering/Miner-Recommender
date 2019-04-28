package ch.uzh.ifi.seal.ase19.miner;

import cc.kave.commons.model.naming.codeelements.IMethodName;
import cc.kave.commons.model.naming.impl.v0.codeelements.MethodName;
import cc.kave.commons.model.ssts.ISST;
import cc.kave.commons.model.ssts.IStatement;
import cc.kave.commons.model.ssts.declarations.IMethodDeclaration;
import cc.kave.commons.model.ssts.impl.SST;
import cc.kave.commons.model.ssts.impl.declarations.MethodDeclaration;
import ch.uzh.ifi.seal.ase19.core.MethodInvocationContext;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import static org.mockito.Mockito.*;

class MethodInvocationContextVisitorTest {
    @Test
    public void testVisitor() {
        MethodName methodName = mock(MethodName.class);
        ISST sst = createSSTWithMethod(methodName);

        MethodInvocationContextVisitor sut = mock(MethodInvocationContextVisitor.class);
        when(sut.visit(ArgumentMatchers.isA(ISST.class), ArgumentMatchers.any())).thenCallRealMethod();
        when(sut.visit(ArgumentMatchers.isA(IMethodDeclaration.class), ArgumentMatchers.any())).thenCallRealMethod();

        sst.accept(sut, new MethodInvocationContext());

        verify(sut, times(1)).visit(ArgumentMatchers.isA(ISST.class), ArgumentMatchers.any());
        verify(sut, times(1)).visit(ArgumentMatchers.isA(IMethodDeclaration.class), ArgumentMatchers.any());
    }

    private static SST createSSTWithMethod(IMethodName methodName, IStatement... methodBody) {
        MethodDeclaration md = new MethodDeclaration();
        if (methodName != null) {
            md.setName(methodName);
        }

        md.getBody().addAll(Lists.newArrayList(methodBody));

        SST sst = new SST();
        sst.getMethods().add(md);

        return sst;
    }
}