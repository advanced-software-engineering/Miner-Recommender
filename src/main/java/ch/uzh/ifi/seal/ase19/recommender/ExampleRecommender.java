package ch.uzh.ifi.seal.ase19.recommender;

import cc.kave.commons.model.naming.codeelements.IMemberName;
import ch.uzh.ifi.seal.ase19.core.IPersistenceManager;
import ch.uzh.ifi.seal.ase19.core.InMemoryPersistenceManager;
import ch.uzh.ifi.seal.ase19.core.models.ObjectOrigin;
import ch.uzh.ifi.seal.ase19.core.models.Query;
import ch.uzh.ifi.seal.ase19.core.models.ResultType;
import ch.uzh.ifi.seal.ase19.core.models.SurroundingExpression;
import ch.uzh.ifi.seal.ase19.miner.ContextProcessor;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

public class ExampleRecommender {

    private static Logger logger = LogManager.getLogger(ExampleRecommender.class);

    public static void main(String[] args) {
        if (args.length < 1) {
            logger.error("Not enough arguments provided! Syntax: modelDirectory\n");
            System.exit(1);
        }

        String modelDirectory = args[0];

        logger.info("Model directory is: " + modelDirectory + "\n");

        IPersistenceManager persistence = new InMemoryPersistenceManager(modelDirectory);
        ContextProcessor processor = new ContextProcessor(persistence);

        MethodCallRecommender recommender = new MethodCallRecommender(processor, persistence);
        Query query = new Query(ResultType.METHOD_INVOCATION, "System.IO.StreamReader", SurroundingExpression.ASSIGNMENT, ObjectOrigin.LOCAL, "System.String", null);
        Set<Pair<IMemberName, Double>> result = recommender.query(query);
        System.out.printf("%-30s%-30s\n", "Method name", "Similarity measure");
        System.out.printf("%-30s%-30s\n", "------------", "--------------------");
        result.forEach(p -> {
            System.out.printf("%-30s%-30s\n",p.getLeft().getName(),p.getRight());
        });
    }
}