package ch.uzh.ifi.seal.ase19.core.utils;

import ch.uzh.ifi.seal.ase19.core.models.ResultType;

import java.io.File;
import java.nio.file.Paths;

public class FileUtils {
    private final static String suffix = ".json";

    public static File getPersistenceFile(String modelDirectory, String receiverType, ResultType type) {
        return Paths.get(modelDirectory, type.toString(), receiverType + suffix).toFile();
    }
}
