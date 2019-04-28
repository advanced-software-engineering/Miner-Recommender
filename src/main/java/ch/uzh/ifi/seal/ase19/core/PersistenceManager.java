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
        ReceiverTypeQueries storageFile = load(model.getReceiverType(), model.getResultType());
        storageFile.addItem(model);
        JsonUtils.toJson(storageFile, file);
    }

    @Override
    public ReceiverTypeQueries load(String receiverType, ResultType type) {
        File file = FileUtils.getPersistenceFile(modelDirectory, receiverType, type);

        if (file.exists()) {
            return JsonUtils.fromJson(file, ReceiverTypeQueries.class);
        } else {
            return new ReceiverTypeQueries();
        }
    }
}