package ch.uzh.ifi.seal.ase19.core;

import ch.uzh.ifi.seal.ase19.core.models.QuerySelection;
import ch.uzh.ifi.seal.ase19.core.models.ResultType;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Set;


public interface IPersistenceManager {

    void saveModel(QuerySelection model);

    void replaceModels(ReceiverTypeQueries queries);

    ReceiverTypeQueries load(String identifier, ResultType type);

    Set<Pair<String, ResultType>> getAllModels();
}
