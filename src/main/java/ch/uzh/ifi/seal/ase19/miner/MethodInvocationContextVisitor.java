package ch.uzh.ifi.seal.ase19.miner;

import cc.kave.commons.model.ssts.blocks.*;
import cc.kave.commons.model.ssts.declarations.IMethodDeclaration;
import cc.kave.commons.model.ssts.expressions.assignable.IInvocationExpression;
import cc.kave.commons.model.ssts.expressions.assignable.ILambdaExpression;
import cc.kave.commons.model.ssts.impl.visitor.AbstractTraversingNodeVisitor;
import cc.kave.commons.model.ssts.statements.IAssignment;
import cc.kave.commons.model.ssts.statements.IReturnStatement;
import ch.uzh.ifi.seal.ase19.core.MethodInvocationContext;
import ch.uzh.ifi.seal.ase19.core.models.SurroundingExpression;

import java.util.ArrayList;
import java.util.List;

public class MethodInvocationContextVisitor extends AbstractTraversingNodeVisitor<MethodInvocationContext, Void> {
    private List<MethodInvocationContext> found = new ArrayList<>();

    @Override
    public Void visit(IMethodDeclaration decl, MethodInvocationContext context) {
        return super.visit(decl, context.setMethodDeclaration(decl));
    }

    @Override
    public Void visit(IIfElseBlock block, MethodInvocationContext context) {
        return super.visit(block, context.setSurroundingExpression(SurroundingExpression.BRANCHING_CONDITION));
    }

    @Override
    public Void visit(IForLoop block, MethodInvocationContext context) {
        return super.visit(block, context.setSurroundingExpression(SurroundingExpression.LOOP));
    }

    @Override
    public Void visit(IForEachLoop block, MethodInvocationContext context) {
        return super.visit(block, context.setSurroundingExpression(SurroundingExpression.LOOP));
    }

    @Override
    public Void visit(IWhileLoop block, MethodInvocationContext context) {
        return super.visit(block, context.setSurroundingExpression(SurroundingExpression.LOOP));
    }

    @Override
    public Void visit(IDoLoop block, MethodInvocationContext context) {
        return super.visit(block, context.setSurroundingExpression(SurroundingExpression.LOOP));
    }

    @Override
    public Void visit(ISwitchBlock block, MethodInvocationContext context) {
        return super.visit(block, context.setSurroundingExpression(SurroundingExpression.BRANCHING_CONDITION));
    }

    @Override
    public Void visit(IInvocationExpression expr, MethodInvocationContext context) {
        MethodInvocationContext newContext = context.setMethodInvocation(expr);
        found.add(newContext);
        return super.visit(expr, newContext);
    }

    @Override
    public Void visit(IAssignment stmt, MethodInvocationContext context) {
        return super.visit(stmt, context.setSurroundingExpression(SurroundingExpression.ASSIGNMENT));
    }

    @Override
    public Void visit(IReturnStatement stmt, MethodInvocationContext context) {
        return super.visit(stmt, context.setSurroundingExpression(SurroundingExpression.RETURN_STATEMENT));
    }

    @Override
    public Void visit(ILambdaExpression expr, MethodInvocationContext context) {
        return super.visit(expr, context.setSurroundingExpression(SurroundingExpression.LAMBDA));
    }

    @Override
    public Void visit(ITryBlock block, MethodInvocationContext context) {
        return super.visit(block, context.setSurroundingExpression(SurroundingExpression.TRY));
    }

    List<MethodInvocationContext> getFound() {
        return found;
    }
}