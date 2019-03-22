package ch.uzh.ifi.seal.ase19.core.models;


import java.util.Objects;

public class QuerySelection {
    private Query query;
    private String selection;
    private long frequency;

    public QuerySelection(Query query, String selection) {
        this.query = query;
        this.selection = selection;
        this.frequency = 1;
    }

    public String getReceiverType() {
        return query.getReceiverType();
    }

    public boolean same(QuerySelection other) {
        return Objects.equals(query, other.query) && Objects.equals(selection, other.selection);
    }
}