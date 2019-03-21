package uzh.ch.ase19.miner;

import cc.kave.commons.model.ssts.ISST;
import uzh.ch.ase19.core.MethodInvocationContext;

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