package ch.uzh.ifi.seal.ase19.core;

import cc.kave.commons.model.ssts.declarations.IMethodDeclaration;
import cc.kave.commons.model.ssts.expressions.assignable.IInvocationExpression;
import ch.uzh.ifi.seal.ase19.core.models.SurroundingExpression;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

class MethodInvocationContextTest {
    @Test
    void defensiveCopy() {
        MethodInvocationContext sut = new MethodInvocationContext();
        MethodInvocationContext c1 = sut.setMethodDeclaration(mock(IMethodDeclaration.class));
        MethodInvocationContext c2 = sut.setSurroundingExpression(SurroundingExpression.RETURN_STATEMENT);
        MethodInvocationContext c3 = sut.setMethodInvocation(mock(IInvocationExpression.class));

        Assertions.assertNotEquals(sut, c1);
        Assertions.assertNotEquals(c1, c2);
        Assertions.assertNotEquals(c2, c3);
    }
}