package ch.uzh.ifi.seal.ase19.recommender;

import cc.kave.commons.model.events.completionevents.Context;
import cc.kave.commons.model.naming.codeelements.IMemberName;
import cc.kave.rsse.calls.AbstractCallsRecommender;
import ch.uzh.ifi.seal.ase19.core.models.Query;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Set;

public class MethodCallRecommender extends AbstractCallsRecommender<Query> {
    @Override
    public Set<Pair<IMemberName, Double>> query(Query query) {
        // TODO
        return null;
    }

    @Override
    public Set<Pair<IMemberName, Double>> query(Context context) {
        // TODO
        return null;
    }

    @Override
    public int getLastModelSize() {
        // TODO
        return 0;
    }
}
