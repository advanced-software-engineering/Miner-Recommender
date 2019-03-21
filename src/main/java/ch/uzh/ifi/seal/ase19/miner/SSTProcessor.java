package ch.uzh.ifi.seal.ase19.miner;

import cc.kave.commons.model.ssts.ISST;
import ch.uzh.ifi.seal.ase19.core.MethodInvocationContext;

import java.util.ArrayList;
import java.util.List;

class SSTProcessor {

    List<MethodInvocationContext> process(ISST sst) {
        try {
            MethodInvocationContextVisitor visitor = new MethodInvocationContextVisitor();
            sst.accept(visitor, new MethodInvocationContext());
            return visitor.getFound();
        } catch (Exception e) {
        }

        return new ArrayList<>();
    }
}