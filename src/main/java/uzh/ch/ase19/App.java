package uzh.ch.ase19;

import cc.kave.commons.model.events.completionevents.Context;

import java.io.File;
import java.util.List;
import java.util.Set;

/**
 * Hello world!
 *
 */
public class App 
{
    public static String dirEvents = "./events/";

    /*
     * download the context data and follow the same instructions as before.
     */
    public static String dirContexts = "./contexts/";
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        readContextsFromDisk();

    }



    /**
     * 1: read contexts
     */
    public static void readContextsFromDisk() {

        Set<String> contextList = IoHelper.findAllZips(dirContexts);
        for (String zip: contextList){
            List<Context>  context = IoHelper.read(dirContexts.concat(zip));
            System.out.println(context);

        }

    }
}
