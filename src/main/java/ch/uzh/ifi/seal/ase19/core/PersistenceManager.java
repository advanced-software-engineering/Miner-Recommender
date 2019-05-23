package ch.uzh.ifi.seal.ase19.core;

import cc.kave.commons.utils.io.json.JsonUtils;
import ch.uzh.ifi.seal.ase19.core.models.QuerySelection;
import ch.uzh.ifi.seal.ase19.core.models.ResultType;
import ch.uzh.ifi.seal.ase19.core.utils.FileUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class PersistenceManager implements IPersistenceManager {
    private String modelDirectory;

    public PersistenceManager(String modelDirectory) {
        this.modelDirectory = modelDirectory;
    }

    @Override
    public void saveModel(QuerySelection model) {
        File file = FileUtils.getPersistenceFile(modelDirectory, model.getReceiverType(), model.getResultType());
        ReceiverTypeQueries storageFile = load(model.getReceiverType(), model.getResultType());
        storageFile.addItem(model);
        JsonUtils.toJson(storageFile, file);
    }

    @Override
    public void replaceModels(ReceiverTypeQueries queries) {
        QuerySelection firstModel = queries.getItems().get(0);
        File file = FileUtils.getPersistenceFile(modelDirectory, firstModel.getReceiverType(), firstModel.getResultType());
        JsonUtils.toJson(queries, file);
    }

    @Override
    public ReceiverTypeQueries load(String receiverType, ResultType resultType) {
        File file = FileUtils.getPersistenceFile(modelDirectory, receiverType, resultType);

        if (file.exists()) {
            return JsonUtils.fromJson(file, ReceiverTypeQueries.class);
        } else {
            return new ReceiverTypeQueries();
        }
    }

    @Override
    public Set<Pair<String, ResultType>> getAllModels() {
        HashSet<Pair<String, ResultType>> ret = new HashSet<>();

        for (ResultType resultType : ResultType.values()) {
            File directory = Paths.get(modelDirectory, resultType.toString()).toFile();

            if (directory.exists()) {
                File[] files = directory.listFiles();
                if(files != null) {
                    for (File fileEntry : files) {
                        ret.add(Pair.of(fileEntry.getName(), resultType));
                    }
                }
            }
        }

        return ret;
    }
}