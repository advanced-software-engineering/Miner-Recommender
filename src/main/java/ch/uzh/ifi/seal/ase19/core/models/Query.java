package ch.uzh.ifi.seal.ase19.core.models;

import java.util.Objects;

public class Query {
    private ResultType resultType;
    private String receiverType;
    private SurroundingExpression surroundingExpression;
    private ObjectOrigin objectOrigin;
    private String requiredType;
    private EnclosingMethodSignature enclosingMethodSignature;

    public Query(ResultType resultType, String receiverType, SurroundingExpression surroundingExpression, ObjectOrigin objectOrigin, String requiredType, EnclosingMethodSignature enclosingMethodSignature) {
        this.resultType = resultType;
        this.receiverType = receiverType;
        this.surroundingExpression = surroundingExpression;
        this.objectOrigin = objectOrigin;
        this.requiredType = requiredType;
        this.enclosingMethodSignature = enclosingMethodSignature;
    }

    public ResultType getResultType() {
        return resultType;
    }

    public String getReceiverType() {
        return receiverType;
    }

    public SurroundingExpression getSurroundingExpression() {
        return surroundingExpression;
    }

    public ObjectOrigin getObjectOrigin() {
        return objectOrigin;
    }

    public String getRequiredType() {
        return requiredType;
    }

    public EnclosingMethodSignature getEnclosingMethodSignature() {
        return enclosingMethodSignature;
    }

    public void setReceiverType(String receiverType) {
        this.receiverType = receiverType;
    }

    public void setRequiredType(String requiredType) {
        this.requiredType = requiredType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Query query = (Query) o;
        return Objects.equals(receiverType, query.receiverType) &&
                surroundingExpression == query.surroundingExpression &&
                objectOrigin == query.objectOrigin &&
                Objects.equals(requiredType, query.requiredType) &&
                Objects.equals(enclosingMethodSignature, query.enclosingMethodSignature);
    }

    @Override
    public String toString() {
        return "Query{" +
                "resultType=" + resultType +
                ", receiverType='" + receiverType + '\'' +
                ", surroundingExpression=" + surroundingExpression +
                ", objectOrigin=" + objectOrigin +
                ", requiredType='" + requiredType + '\'' +
                ", enclosingMethodSignature=" + enclosingMethodSignature +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(resultType, receiverType, surroundingExpression, objectOrigin, requiredType, enclosingMethodSignature);
    }
}