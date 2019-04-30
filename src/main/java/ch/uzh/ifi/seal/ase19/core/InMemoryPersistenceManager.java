package ch.uzh.ifi.seal.ase19.core;

import ch.uzh.ifi.seal.ase19.core.models.QuerySelection;
import ch.uzh.ifi.seal.ase19.core.models.ResultType;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Set;

public class InMemoryPersistenceManager implements IPersistenceManager {
    private PersistenceManager persistenceManager;
    private HashMap<Pair<String, ResultType>, ReceiverTypeQueries> db = new HashMap<>();

    public InMemoryPersistenceManager(String modelDirectory) {
        this.persistenceManager = new PersistenceManager(modelDirectory);
    }

    @Override
    public void saveModel(QuerySelection model) {
        ReceiverTypeQueries entry = load(model.getReceiverType(), model.getResultType());
        entry.addItem(model);
    }

    @Override
    public void replaceModels(ReceiverTypeQueries queries) {
        QuerySelection firstModel = queries.getItems().get(0);
        Pair<String, ResultType> key = Pair.of(firstModel.getReceiverType(), firstModel.getResultType());
        db.put(key, queries);
    }

    @Override
    public ReceiverTypeQueries load(String receiverType, ResultType resultType) {
        Pair<String, ResultType> key = Pair.of(receiverType, resultType);
        ReceiverTypeQueries value = db.get(key);

        if (value == null) {
            value = persistenceManager.load(receiverType, resultType);
            db.put(key, value);
        }

        return value;
    }

    @Override
    public Set<Pair<String, ResultType>> getAllModels() {
        return db.keySet();
    }

    public void saveOnFileSystem() {
        for (Pair<String, ResultType> key : getAllModels()) {
            persistenceManager.replaceModels(db.get(key));
        }

        db = new HashMap<>();
    }
}