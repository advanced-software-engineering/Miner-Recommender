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

import java.util.*;

public class MethodCallRecommender extends AbstractCallsRecommender<Query> {
    private ContextProcessor processor;
    private IPersistenceManager pm;

    public MethodCallRecommender(ContextProcessor processor, IPersistenceManager pm) {
        this.processor = processor;
        this.pm = pm;
    }

    @Override
    public Set<Pair<IMemberName, Double>> query(Query query) {
        try {
            TreeSet<Pair<IMemberName, Double>> result = new TreeSet<>(new Comparator<Pair<IMemberName, Double>>() {
                @Override
                public int compare(Pair<IMemberName, Double> o1, Pair<IMemberName, Double> o2) {
                    if (o1.getRight() < o2.getRight()) {
                        return 1;
                    } else if (o1.getRight() > o2.getRight()) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });
            ReceiverTypeQueries rtq = pm.load(query.getReceiverType(), query.getResultType());
            List<QuerySelection> querySelections = rtq.getItems();
            double totalFrequency = 0;
            List<QuerySelection> filteredSelections = new ArrayList<>();
            for (QuerySelection querySelection : querySelections) {
                if (querySelection.getQuery().equals(query)) {
                    filteredSelections.add(querySelection);
                    totalFrequency += querySelection.getFrequency();
                }
            }

            for (QuerySelection querySelection : filteredSelections
            ) {

                result.add(new ImmutablePair<IMemberName, Double>(querySelection.getSelection(), (double) Math.round((querySelection.getFrequency() / totalFrequency) * 100) / 100.00));

            }

            return result;


        } catch (RuntimeException e) {
            System.out.println(e);
            return null;
        }
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
