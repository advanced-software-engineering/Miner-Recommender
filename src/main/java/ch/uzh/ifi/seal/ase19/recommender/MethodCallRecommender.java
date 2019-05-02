package ch.uzh.ifi.seal.ase19.recommender;

import cc.kave.commons.model.events.completionevents.Context;
import cc.kave.commons.model.naming.codeelements.IMemberName;
import cc.kave.rsse.calls.AbstractCallsRecommender;
import ch.uzh.ifi.seal.ase19.core.IPersistenceManager;
import ch.uzh.ifi.seal.ase19.core.ReceiverTypeQueries;
import ch.uzh.ifi.seal.ase19.core.models.Query;
import ch.uzh.ifi.seal.ase19.core.models.QuerySelection;
import ch.uzh.ifi.seal.ase19.miner.ContextProcessor;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class MethodCallRecommender extends AbstractCallsRecommender<Query> {
    private ContextProcessor processor;
    private IPersistenceManager pm;

    public MethodCallRecommender(ContextProcessor processor, IPersistenceManager pm) {
        this.processor = processor;
        this.pm = pm;
    }

    @Override
    public Set<Pair<IMemberName, Double>> query(Query query) {
        TreeSet<Pair<IMemberName, Double>> result = new TreeSet<>(new Comparator<Pair<IMemberName, Double>>() {
            @Override
            public int compare(Pair<IMemberName, Double> o1, Pair<IMemberName, Double> o2) {
                return -1 * o1.getRight().compareTo(o2.getRight());
            }
        });

        ReceiverTypeQueries rtq = pm.load(query.getReceiverType(), query.getResultType());
        for (QuerySelection querySelection : rtq.getItems()) {
            double a = new Similarity(querySelection.getQuery(), query).calculate();
            result.add(new ImmutablePair<>(querySelection.getSelection(), new Similarity(querySelection.getQuery(), query).calculate()));
        }

        return result;
    }

    @Override
    public Set<Pair<IMemberName, Double>> query(Context context) {
        List<QuerySelection> res = processor.run(context);

        if (res.isEmpty()) {
            throw new IllegalArgumentException("No method invocation context was found");
        }

        return query(res.get(0).getQuery());
    }

    @Override
    public int getLastModelSize() {
        // TODO
        throw new UnsupportedOperationException("Last model size cannot be returned");
    }
}
