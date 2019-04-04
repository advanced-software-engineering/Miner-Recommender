package ch.uzh.ifi.seal.ase19.core;

import cc.kave.commons.utils.io.json.JsonUtils;
import ch.uzh.ifi.seal.ase19.core.models.QuerySelection;
import ch.uzh.ifi.seal.ase19.core.models.ResultType;
import ch.uzh.ifi.seal.ase19.core.utils.FileUtils;

import java.io.File;

// TODO cache model because same models are often used (OnDemand and InMemory storage)
public class PersistenceManager implements IPersistenceManager {
    private String modelDirectory;

    public PersistenceManager(String modelDirectory) {
        this.modelDirectory = modelDirectory;
    }

    @Override
    public void save(QuerySelection model) {
        File file = FileUtils.getPersistenceFile(modelDirectory, model.getReceiverType(), model.getResultType());
        JsonUtils.toJson(model, file);
    }

    @Override
    public QuerySelection load(String receiverType, ResultType type) {
        try {
            File file = FileUtils.getPersistenceFile(modelDirectory, receiverType, type);
            return JsonUtils.fromJson(file, QuerySelection.class);
        } catch (RuntimeException e) {
            return null;
        }
    }
}