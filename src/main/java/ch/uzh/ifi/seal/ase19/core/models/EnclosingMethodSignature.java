package ch.uzh.ifi.seal.ase19.core.models;

import cc.kave.commons.model.naming.codeelements.IMethodName;
import cc.kave.commons.model.naming.codeelements.IParameterName;

import java.util.ArrayList;
import java.util.List;

public class EnclosingMethodSignature {
    private String fullyQualifiedClassName;
    private String methodName;
    private String fullyQualifiedReturnType;
    private List<MethodParameter> parameters = new ArrayList<>();
    private EnclosingMethodSignature superMethodSignature;

    public EnclosingMethodSignature(IMethodName methodName) {
        fullyQualifiedClassName = methodName.getDeclaringType().getFullName();
        this.methodName = methodName.getFullName();
        fullyQualifiedReturnType = methodName.getReturnType().getFullName();

        for (IParameterName parameter : methodName.getParameters()) {
            String name = parameter.getName();
            String type = parameter.getValueType().getFullName();
            parameters.add(new MethodParameter(name, type));
        }
    }

    public String getMethodName() {
        return methodName;
    }

    public String getFullyQualifiedReturnType() {
        return fullyQualifiedReturnType;
    }

    public List<MethodParameter> getParameters() {
        return parameters;
    }

    public EnclosingMethodSignature getSuperMethodSignature() {
        return superMethodSignature;
    }

    public void setSuperMethodSignature(EnclosingMethodSignature superMethodSignature) {
        this.superMethodSignature = superMethodSignature;
    }

    public boolean isSuperType(EnclosingMethodSignature other) {
        boolean isParameterListEqual = true;

        if (parameters.size() == other.parameters.size()) {
            for (int i = 0; i < parameters.size(); i++) {
                MethodParameter myMethodParameter = parameters.get(i);
                MethodParameter otherMethodParameter = other.parameters.get(i);
                if (!myMethodParameter.equals(otherMethodParameter)) {
                    isParameterListEqual = false;
                    break;
                }
            }
        }

        return fullyQualifiedClassName.equals(other.fullyQualifiedClassName)
                && methodName.equals(other.methodName)
                && fullyQualifiedReturnType.equals(other.fullyQualifiedReturnType)
                && isParameterListEqual;
    }
}
