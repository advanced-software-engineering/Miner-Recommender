package ch.uzh.ifi.seal.ase19.core;

import ch.uzh.ifi.seal.ase19.core.models.QuerySelection;
import ch.uzh.ifi.seal.ase19.core.models.ResultType;

public interface IPersistenceManager {

    void save(QuerySelection model);

    QuerySelection load(String identifier, ResultType type);
}
