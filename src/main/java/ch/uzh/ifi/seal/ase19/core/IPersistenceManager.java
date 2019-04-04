package ch.uzh.ifi.seal.ase19.core;

import ch.uzh.ifi.seal.ase19.core.models.QuerySelection;
import ch.uzh.ifi.seal.ase19.core.models.ResultType;

public interface IPersistenceManager {

    void save(QuerySelection model);

    ReceiverTypeQueries load(String identifier, ResultType type);
}
