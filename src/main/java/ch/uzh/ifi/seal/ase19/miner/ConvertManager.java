package ch.uzh.ifi.seal.ase19.miner;

import cc.kave.commons.model.naming.codeelements.IFieldName;
import cc.kave.commons.model.naming.codeelements.IMethodName;
import cc.kave.commons.model.ssts.expressions.assignable.IInvocationExpression;
import cc.kave.commons.model.ssts.references.IVariableReference;
import cc.kave.commons.model.typeshapes.IMemberHierarchy;
import ch.uzh.ifi.seal.ase19.core.MethodInvocationContext;
import ch.uzh.ifi.seal.ase19.core.models.*;
import ch.uzh.ifi.seal.ase19.core.utils.SSTUtils;

import java.util.Set;

class ConvertManager {
    private MethodInvocationContext methodInvocationContext;
    private Set<IMemberHierarchy<IMethodName>> methodHierarchy;
    private Set<IFieldName> fields;

    ConvertManager(MethodInvocationContext methodInvocationContext, Set<IMemberHierarchy<IMethodName>> methodHierarchy, Set<IFieldName> fields) {
        this.methodInvocationContext = methodInvocationContext;
        this.methodHierarchy = methodHierarchy;
        this.fields = fields;
    }

    QuerySelection toQuerySelection() {
        IInvocationExpression methodInvocation = methodInvocationContext.getMethodInvocation();

        IMethodName method = methodInvocation.getMethodName();
        String receiverType = SSTUtils.getFullyQualifiedNameWithoutGenerics(method.getDeclaringType().getFullName());
        String requiredType = SSTUtils.getFullyQualifiedNameWithoutGenerics(method.getReturnType().getFullName());
        SurroundingExpression surroundingExpression = methodInvocationContext.getSurroundingExpression();

        EnclosingMethodSignature enclosingMethodSignature = null;
        if (methodInvocationContext.getMethodDeclaration() != null) {
            enclosingMethodSignature = new EnclosingMethodSignature(methodInvocationContext.getMethodDeclaration().getName());
            setSuperMethodSignature(enclosingMethodSignature);
        }

        ObjectOrigin objectOrigin = null;
        IVariableReference reference = methodInvocation.getReference();
        if (reference != null && !reference.isMissing()) {
            String referenceIdentifier = reference.getIdentifier();
            objectOrigin = getObjectOrigin(referenceIdentifier, enclosingMethodSignature);
        }

        Query query = new Query(ResultType.METHOD_INVOCATION, receiverType, surroundingExpression, objectOrigin, requiredType, enclosingMethodSignature);
        return new QuerySelection(query, method);
    }

    private ObjectOrigin getObjectOrigin(String variableName, EnclosingMethodSignature enclosingMethodSignature) {
        if (variableName.equals("this")) {
            return ObjectOrigin.CLASS;
        } else if (isParameter(variableName, enclosingMethodSignature)) {
            return ObjectOrigin.PARAMETER;
        } else {
            return ObjectOrigin.LOCAL;
        }
    }

    private boolean isParameter(String variableName, EnclosingMethodSignature enclosingMethodSignature) {
        if (enclosingMethodSignature != null) {
            for (MethodParameter parameter : enclosingMethodSignature.getParameters()) {
                if (parameter.getName().equals(variableName)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isClassVariable(String variableName, EnclosingMethodSignature enclosingMethodSignature) {
        return isVariable(variableName, enclosingMethodSignature, true);
    }

    private boolean isInstanceVariable(String variableName, EnclosingMethodSignature enclosingMethodSignature) {
        return isVariable(variableName, enclosingMethodSignature, false);
    }

    private boolean isVariable(String variableName, EnclosingMethodSignature enclosingMethodSignature, boolean isStatic) {
        if (enclosingMethodSignature != null) {

            for (IFieldName field : fields) {
                if (field.isStatic() == isStatic && field.getFullName().equals(variableName)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void setSuperMethodSignature(EnclosingMethodSignature enclosingMethodSignature) {
        for (IMemberHierarchy<IMethodName> hierarchy : methodHierarchy) {
            EnclosingMethodSignature element = new EnclosingMethodSignature(hierarchy.getElement());

            if (element.isSuperType(enclosingMethodSignature)) {
                if (hierarchy.getFirst() != null) {
                    EnclosingMethodSignature first = new EnclosingMethodSignature(hierarchy.getFirst());
                    enclosingMethodSignature.setSuperMethodSignature(first);
                }
                break;
            }
        }
    }
}