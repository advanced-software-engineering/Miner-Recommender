package ch.uzh.ifi.seal.ase19.recommender;

import ch.uzh.ifi.seal.ase19.core.IPersistenceManager;
import ch.uzh.ifi.seal.ase19.core.InMemoryPersistenceManager;
import ch.uzh.ifi.seal.ase19.core.PersistenceManager;
import ch.uzh.ifi.seal.ase19.core.ReceiverTypeQueries;
import ch.uzh.ifi.seal.ase19.core.models.QuerySelection;
import ch.uzh.ifi.seal.ase19.core.models.ResultType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Demo {
    private static Logger logger = LogManager.getLogger(Demo.class);

    public static void main(String[] args) {
        if (args.length < 1) {
            logger.error("Not enough arguments provided! Syntax: modelDirectory");
            System.exit(1);
        }

        String modelDirectory = args[0];

        logger.info("Model directory is: " + modelDirectory);

        IPersistenceManager pm = new InMemoryPersistenceManager(modelDirectory);

        String fullyQualifiedReceiverObject = "System.IO.StreamReader";
        ReceiverTypeQueries rtq = pm.load(fullyQualifiedReceiverObject, ResultType.METHOD_INVOCATION);

        for (QuerySelection item : rtq.getItems()) {
            System.out.println(item.toString());
        }
    }
}
