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
import java.util.stream.Collectors;

public class MethodCallRecommender extends AbstractCallsRecommender<Query> {
    private ContextProcessor processor;
    private IPersistenceManager pm;

    private int lastModelSize = 0;

    public MethodCallRecommender(ContextProcessor processor, IPersistenceManager pm) {
        this.processor = processor;
        this.pm = pm;
    }

    @Override
    public TreeSet<Pair<IMemberName, Double>> query(Query query) {
        Set<Pair<IMemberName, SimilarityDto>> res = queryWithDetails(query);
        return res.parallelStream().map(it -> Pair.of(it.getLeft(), it.getRight().similarity)).collect(
                Collectors.toCollection(() -> new TreeSet<>(new Comparator<Pair<IMemberName, Double>>() {
                    @Override
                    public int compare(Pair<IMemberName, Double> o1, Pair<IMemberName, Double> o2) {
                        return -1 * o1.getRight().compareTo(o2.getRight());
                    }
                }))
        );
    }

    public Set<Pair<IMemberName, SimilarityDto>> queryWithDetails(Query query) {
        TreeSet<Pair<IMemberName, SimilarityDto>> recommendations = new TreeSet<>(new Comparator<Pair<IMemberName, SimilarityDto>>() {
            @Override
            public int compare(Pair<IMemberName, SimilarityDto> o1, Pair<IMemberName, SimilarityDto> o2) {
                return -1 * o1.getRight().similarity.compareTo(o2.getRight().similarity);
            }
        });

        ReceiverTypeQueries rtq = pm.load(query.getReceiverType(), query.getResultType());
        for (QuerySelection querySelection : rtq.getItems()) {
            Similarity similarity = new Similarity(querySelection.getQuery(), query);
            recommendations.add(new ImmutablePair<>(querySelection.getSelection(), similarity.calculateWithDetails()));
        }

        Map<String, Pair<IMemberName, SimilarityDto>> map = new HashMap<>();

        /*
            if multiple recommendations have the same name but different similarities, only the include the pair with the, highest similarity.
         */
        for (Pair<IMemberName, SimilarityDto> pair : recommendations) {
            String fullName = pair.getLeft().getFullName();
            if (map.get(fullName) == null) {
                map.put(fullName, pair);
            } else {
                // update the map to have the pair with the higher similarity in it
                if (map.get(fullName).getRight().similarity < pair.getRight().similarity) {
                    map.put(fullName, pair);
                }
            }
        }

        //clear the recommendations which may have duplicates method names
        recommendations.clear();
        recommendations.addAll(map.values());

        return recommendations;
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
        return lastModelSize;
    }

    public void persist(QuerySelection querySelection){
        pm.saveModel(querySelection);
    }
}
