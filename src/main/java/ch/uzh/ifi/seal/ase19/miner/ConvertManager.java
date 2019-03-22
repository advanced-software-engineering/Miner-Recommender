package ch.uzh.ifi.seal.ase19.miner;

import cc.kave.commons.model.naming.codeelements.IFieldName;
import cc.kave.commons.model.naming.codeelements.IMethodName;
import cc.kave.commons.model.ssts.expressions.assignable.IInvocationExpression;
import cc.kave.commons.model.ssts.references.IVariableReference;
import cc.kave.commons.model.typeshapes.IMemberHierarchy;
import ch.uzh.ifi.seal.ase19.core.MethodInvocationContext;
import ch.uzh.ifi.seal.ase19.core.models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

class ConvertManager {
    private List<MethodInvocationContext> methodInvocationContexts;
    private Set<IMemberHierarchy<IMethodName>> methodHierarchy;
    private Set<IFieldName> fields;

    ConvertManager(List<MethodInvocationContext> methodInvocationContexts, Set<IMemberHierarchy<IMethodName>> methodHierarchy, Set<IFieldName> fields) {
        this.methodInvocationContexts = methodInvocationContexts;
        this.methodHierarchy = methodHierarchy;
        this.fields = fields;
    }

    List<QuerySelection> run() {
        List<QuerySelection> ret = new ArrayList<>();
        for (MethodInvocationContext methodInvocationContext : methodInvocationContexts) {
            QuerySelection query = toQuerySelection(methodInvocationContext);
            if (query != null) {
                ret.add(query);
            }

        }
        return ret;
    }

    private QuerySelection toQuerySelection(MethodInvocationContext methodInvocationContext) {
        IInvocationExpression methodInvocation = methodInvocationContext.getMethodInvocation();

        IMethodName method = methodInvocation.getMethodName();
        String receiverType = method.getDeclaringType().getFullName();
        String selectedMethodName = method.getFullName();
        String requiredType = method.getReturnType().getFullName();
        SurroundingType surroundingType = methodInvocationContext.getSurroundingType();

        EnclosingMethodSignature enclosingMethodSignature = null;
        if (methodInvocationContext.getMethodDeclaration() != null) {
            enclosingMethodSignature = new EnclosingMethodSignature(methodInvocationContext.getMethodDeclaration().getName());
            setSuperMethodSignature(enclosingMethodSignature);
        }

        ObjectOrigin objectOrigin;
        IVariableReference reference = methodInvocation.getReference();
        if (reference.isMissing()) {
            return null;
        } else {
            String referenceIdentifier = reference.getIdentifier();
            objectOrigin = getObjectOrigin(referenceIdentifier, enclosingMethodSignature);
        }

        Query query = new Query(receiverType, surroundingType, objectOrigin, requiredType, enclosingMethodSignature);
        return new QuerySelection(query, selectedMethodName);
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