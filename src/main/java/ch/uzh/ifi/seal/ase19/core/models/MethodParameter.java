package ch.uzh.ifi.seal.ase19.core.models;

import ch.uzh.ifi.seal.ase19.core.utils.SSTUtils;

import java.util.Objects;

public class MethodParameter {
    private String name;
    private String type;

    public MethodParameter(String name, String type) {
        this.name = name;
        this.type = SSTUtils.getFullyQualifiedNameWithoutGenerics(type);
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MethodParameter that = (MethodParameter) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(type, that.type);
    }
}
