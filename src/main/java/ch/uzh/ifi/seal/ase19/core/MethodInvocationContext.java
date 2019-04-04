package ch.uzh.ifi.seal.ase19.core;

import cc.kave.commons.model.ssts.declarations.IMethodDeclaration;
import cc.kave.commons.model.ssts.expressions.assignable.IInvocationExpression;
import ch.uzh.ifi.seal.ase19.core.models.SurroundingExpression;

public class MethodInvocationContext {
    private IMethodDeclaration methodDeclaration;
    private SurroundingExpression surroundingType;
    private IInvocationExpression methodInvocation;

    public MethodInvocationContext() {
    }

    private MethodInvocationContext(IMethodDeclaration methodDeclaration, SurroundingExpression surroundingType, IInvocationExpression methodInvocation) {
        this.methodDeclaration = methodDeclaration;
        this.surroundingType = surroundingType;
        this.methodInvocation = methodInvocation;
    }

    private MethodInvocationContext getCopy() {
        return new MethodInvocationContext(methodDeclaration, surroundingType, methodInvocation);
    }

    public IMethodDeclaration getMethodDeclaration() {
        return methodDeclaration;
    }

    public MethodInvocationContext setMethodDeclaration(IMethodDeclaration methodDeclaration) {
        MethodInvocationContext copy = getCopy();
        copy.methodDeclaration = methodDeclaration;
        copy.surroundingType = SurroundingExpression.METHOD_BODY;
        return copy;
    }

    public SurroundingExpression getSurroundingType() {
        return surroundingType;
    }

    public MethodInvocationContext setSurroundingType(SurroundingExpression surroundingType) {
        MethodInvocationContext copy = getCopy();
        copy.surroundingType = surroundingType;
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
