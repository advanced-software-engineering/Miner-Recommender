package uzh.ch.ase19.miner;

import cc.kave.commons.model.events.completionevents.Context;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uzh.ch.ase19.core.IoHelper;
import uzh.ch.ase19.core.MethodInvocationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Miner {

    private static Logger logger = LogManager.getLogger(Miner.class);

    /*
        download the context data and set contextDirectory argument
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            logger.error("Not enough arguments provided! Syntax: contextDirectory modelDirectory");
        }

        String contextDirectory = args[0];
        String modelDirectory = args[1];

        logger.info("Context directory is: " + contextDirectory);
        logger.info("Model directory is: " + modelDirectory);

        readContextsFromDisk(contextDirectory);
    }


    private static void readContextsFromDisk(String contextDirectory) {
        SSTProcessor processor = new SSTProcessor();
        Set<String> contextList = IoHelper.findAllZips(contextDirectory);

        for (String zip : contextList) {
            List<Context> contexts = IoHelper.read(contextDirectory.concat(zip));
            List<MethodInvocationContext> all = new ArrayList<>();

            for (Context context : contexts) {
                all.addAll(processor.process(context.getSST()));
            }
        }
    }
}