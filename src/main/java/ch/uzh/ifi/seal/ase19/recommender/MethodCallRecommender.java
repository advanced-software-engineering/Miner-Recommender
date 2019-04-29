package ch.uzh.ifi.seal.ase19.recommender;

import cc.kave.commons.model.events.completionevents.Context;
import cc.kave.commons.model.naming.codeelements.IMemberName;
import cc.kave.rsse.calls.AbstractCallsRecommender;
import ch.uzh.ifi.seal.ase19.core.models.Query;
import ch.uzh.ifi.seal.ase19.core.models.QuerySelection;
import ch.uzh.ifi.seal.ase19.miner.ContextProcessor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Set;

public class MethodCallRecommender extends AbstractCallsRecommender<Query> {
    private ContextProcessor processor;

    public MethodCallRecommender(ContextProcessor processor) {
        this.processor = processor;
    }

    @Override
    public Set<Pair<IMemberName, Double>> query(Query query) {
        // TODO
        return null;
    }

    @Override
    public Set<Pair<IMemberName, Double>> query(Context context) {
        List<QuerySelection> res = processor.run(context);

        if(res.isEmpty()){
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
