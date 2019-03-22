package ch.uzh.ifi.seal.ase19.core.models;


public class QuerySelection {
    private Query query;
    private String selection;
    private long frequency;

    public QuerySelection(Query query, String selection) {
        this.query = query;
        this.selection = selection;
        this.frequency = 1;
    }
}