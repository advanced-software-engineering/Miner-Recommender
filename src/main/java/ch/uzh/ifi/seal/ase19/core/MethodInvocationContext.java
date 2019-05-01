package ch.uzh.ifi.seal.ase19.core;

import cc.kave.commons.model.ssts.declarations.IMethodDeclaration;
import cc.kave.commons.model.ssts.expressions.assignable.IInvocationExpression;
import ch.uzh.ifi.seal.ase19.core.models.SurroundingExpression;

public class MethodInvocationContext {
    private IMethodDeclaration methodDeclaration;
    private SurroundingExpression surroundingExpression;
    private IInvocationExpression methodInvocation;

    public MethodInvocationContext() {
    }

    private MethodInvocationContext(IMethodDeclaration methodDeclaration, SurroundingExpression surroundingExpression, IInvocationExpression methodInvocation) {
        this.methodDeclaration = methodDeclaration;
        this.surroundingExpression = surroundingExpression;
        this.methodInvocation = methodInvocation;
    }

    private MethodInvocationContext getCopy() {
        return new MethodInvocationContext(methodDeclaration, surroundingExpression, methodInvocation);
    }

    public IMethodDeclaration getMethodDeclaration() {
        return methodDeclaration;
    }

    public MethodInvocationContext setMethodDeclaration(IMethodDeclaration methodDeclaration) {
        MethodInvocationContext copy = getCopy();
        copy.methodDeclaration = methodDeclaration;
        copy.surroundingExpression = SurroundingExpression.METHOD_BODY;
        return copy;
    }

    public SurroundingExpression getSurroundingExpression() {
        return surroundingExpression;
    }

    public MethodInvocationContext setSurroundingExpression(SurroundingExpression surroundingExpression) {
        MethodInvocationContext copy = getCopy();
        copy.surroundingExpression = surroundingExpression;
        return copy;
    }

    public IInvocationExpression getMethodInvocation() {
        return methodInvocation;
    }

    public MethodInvocationContext setMethodInvocation(IInvocationExpression methodInvocation) {
        MethodInvocationContext copy = getCopy();
        copy.methodInvocation = methodInvocation;
        return copy;
    }
}
