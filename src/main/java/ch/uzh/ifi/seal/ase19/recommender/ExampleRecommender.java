package ch.uzh.ifi.seal.ase19.recommender;

import ch.uzh.ifi.seal.ase19.core.IPersistenceManager;
import ch.uzh.ifi.seal.ase19.core.InMemoryPersistenceManager;
import ch.uzh.ifi.seal.ase19.miner.ContextProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExampleRecommender {

    private static Logger logger = LogManager.getLogger(ExampleRecommender.class);

    /*
        download the context data and set contextDirectory argument
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            logger.error("Not enough arguments provided! Syntax: modelDirectory");
            System.exit(1);
        }

        String modelDirectory = args[0];

        logger.info("Model directory is: " + modelDirectory);

        IPersistenceManager persistence = new InMemoryPersistenceManager(modelDirectory);
        ContextProcessor processor = new ContextProcessor(persistence);

        MethodCallRecommender recommender = new MethodCallRecommender(processor);
        //Query query = new Query(ResultType.METHOD_INVOCATION, "com.Other", SurroundingExpression.ASSIGNMENT, ObjectOrigin.PARAMETER, "com.Type", ems);
        //recommender.query(query);
    }
}