package ch.uzh.ifi.seal.ase19.core.models;

import java.util.ArrayList;
import java.util.List;

public class Query {
    // TODO hierarchy?
    private String receiverType;
    private SurroundingType surroundingType;
    private ObjectOrigin objectOrigin;
    private List<String> requiredType;
    private EnclosingMethodSignature enclosingMethodSignature;

    public Query(String receiverType, SurroundingType surroundingType, ObjectOrigin objectOrigin, List<String> requiredType, EnclosingMethodSignature enclosingMethodSignature) {
        this.receiverType = receiverType;
        this.surroundingType = surroundingType;
        this.objectOrigin = objectOrigin;
        this.requiredType = requiredType;
        this.enclosingMethodSignature = enclosingMethodSignature;
    }

    public Query(String receiverType, SurroundingType surroundingType, ObjectOrigin objectOrigin, String requiredType, EnclosingMethodSignature enclosingMethodSignature) {
        List<String> requiredTypeAsList = new ArrayList() {{
            add(requiredType);
        }};

        this.receiverType = receiverType;
        this.surroundingType = surroundingType;
        this.objectOrigin = objectOrigin;
        this.requiredType = requiredTypeAsList;
        this.enclosingMethodSignature = enclosingMethodSignature;
    }

    public String getReceiverType() {
        return receiverType;
    }

    public SurroundingType getSurroundingType() {
        return surroundingType;
    }

    public ObjectOrigin getObjectOrigin() {
        return objectOrigin;
    }

    public List<String> getRequiredType() {
        return requiredType;
    }

    public EnclosingMethodSignature getEnclosingMethodSignature() {
        return enclosingMethodSignature;
    }
}