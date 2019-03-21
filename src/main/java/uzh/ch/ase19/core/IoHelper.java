package uzh.ch.ase19.core;

import cc.kave.commons.model.events.completionevents.Context;
import cc.kave.commons.utils.io.Directory;
import cc.kave.commons.utils.io.IReadingArchive;
import cc.kave.commons.utils.io.ReadingArchive;
import com.google.common.collect.Lists;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class IoHelper {
    public static List<Context> read(String zipFile) {
        LinkedList<Context> res = Lists.newLinkedList();
        try {
            IReadingArchive ra = new ReadingArchive(new File(zipFile));
            while (ra.hasNext()) {
                res.add(ra.getNext(Context.class));
            }
            ra.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /*
     * will recursively search for all .zip files in the "dir". The paths that are
     * returned are relative to "dir".
     */
    public static Set<String> findAllZips(String dir) {
        return new Directory(dir).findFiles(s -> s.endsWith(".zip"));
    }
}