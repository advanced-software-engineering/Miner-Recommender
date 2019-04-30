package ch.uzh.ifi.seal.ase19.recommender;

import cc.kave.commons.model.events.completionevents.Context;
import cc.kave.commons.model.naming.codeelements.IMemberName;
import cc.kave.commons.model.naming.codeelements.IMethodName;
import cc.kave.commons.model.naming.impl.v0.codeelements.MethodName;
import ch.uzh.ifi.seal.ase19.core.IPersistenceManager;
import ch.uzh.ifi.seal.ase19.core.InMemoryPersistenceManager;
import ch.uzh.ifi.seal.ase19.core.models.*;
import ch.uzh.ifi.seal.ase19.miner.ContextProcessor;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;
import java.util.SortedSet;

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
        Query query = new Query(ResultType.METHOD_INVOCATION, "System.IO.StreamReader", SurroundingExpression.ASSIGNMENT, ObjectOrigin.LOCAL, "System.String", null);
        Set<Pair<IMemberName, Double>> result = recommender.query(query);
        System.out.println(result);

    }
}