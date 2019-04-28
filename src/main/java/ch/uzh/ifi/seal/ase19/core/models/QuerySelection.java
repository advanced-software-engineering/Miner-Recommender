package ch.uzh.ifi.seal.ase19.core.models;


import cc.kave.commons.model.naming.codeelements.IMemberName;

import java.util.Objects;

public class QuerySelection {
    private Query query;
    private IMemberName selection;
    private long frequency;

    public QuerySelection(Query query, IMemberName selection, long frequency) {
        this.query = query;
        this.selection = selection;
        this.frequency = frequency;
    }

    public QuerySelection(Query query, IMemberName selection) {
        this.query = query;
        this.selection = selection;
        this.frequency = 1;
    }

    public ResultType getResultType() {
        return query.getResultType();
    }

    public String getReceiverType() {
        return query.getReceiverType();
    }

    public IMemberName getSelection() {
        return selection;
    }

    public void addToFrequence(long add) {
        frequency += add;
    }

    public long getFrequency() {
        return frequency;
    }

    public boolean same(QuerySelection other) {
        return Objects.equals(query, other.query) && Objects.equals(selection, other.selection);
    }

    public QuerySelection getCopy() {
        return new QuerySelection(query, selection, frequency);
    }

    @Override
    public String toString() {
        return "QuerySelection{" +
                query.toString() +
                ", selection=" + selection +
                ", frequency=" + frequency +
                '}';
    }
}