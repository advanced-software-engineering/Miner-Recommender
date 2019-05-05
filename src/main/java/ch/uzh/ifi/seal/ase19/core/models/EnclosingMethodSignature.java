package ch.uzh.ifi.seal.ase19.core.models;

import cc.kave.commons.model.naming.codeelements.IMethodName;
import cc.kave.commons.model.naming.codeelements.IParameterName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public EnclosingMethodSignature(String fullyQualifiedClassName, String methodName, String fullyQualifiedReturnType, List<MethodParameter> parameters, EnclosingMethodSignature superMethodSignature) {
        this.fullyQualifiedClassName = fullyQualifiedClassName;
        this.methodName = methodName;
        this.fullyQualifiedReturnType = fullyQualifiedReturnType;
        this.parameters = parameters;
        this.superMethodSignature = superMethodSignature;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnclosingMethodSignature that = (EnclosingMethodSignature) o;
        return Objects.equals(fullyQualifiedClassName, that.fullyQualifiedClassName) &&
                Objects.equals(methodName, that.methodName) &&
                Objects.equals(fullyQualifiedReturnType, that.fullyQualifiedReturnType) &&
                Objects.equals(parameters, that.parameters) &&
                Objects.equals(superMethodSignature, that.superMethodSignature);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullyQualifiedClassName, methodName, fullyQualifiedReturnType, parameters, superMethodSignature);
    }
}
