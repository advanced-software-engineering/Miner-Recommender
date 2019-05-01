package ch.uzh.ifi.seal.ase19.miner;

import cc.kave.commons.model.events.completionevents.Context;
import cc.kave.commons.model.naming.codeelements.IFieldName;
import cc.kave.commons.model.naming.codeelements.IMethodName;
import cc.kave.commons.model.ssts.ISST;
import cc.kave.commons.model.typeshapes.IMemberHierarchy;
import ch.uzh.ifi.seal.ase19.core.IPersistenceManager;
import ch.uzh.ifi.seal.ase19.core.MethodInvocationContext;
import ch.uzh.ifi.seal.ase19.core.models.QuerySelection;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ContextProcessor {

    private IPersistenceManager persistenceManager;

    public ContextProcessor(IPersistenceManager persistenceManager) {
        this.persistenceManager = persistenceManager;
    }

    void runAndPersist(Context context) {
        List<QuerySelection> querySelections = run(context);

        for (QuerySelection querySelection : querySelections) {
            if (querySelection != null) {
                persistenceManager.saveModel(querySelection);
            }
        }
    }

    public List<QuerySelection> run(Context context) {
        List<MethodInvocationContext> methodInvocationContexts = getMethodContext(context.getSST());
        Set<IMemberHierarchy<IMethodName>> methodHierarchies = context.getTypeShape().getMethodHierarchies();
        Set<IFieldName> fields = context.getTypeShape().getFields();

        List<QuerySelection> ret = new ArrayList<>();
        for (MethodInvocationContext methodInvocationContext : methodInvocationContexts) {
            QuerySelection querySelection = new ConvertManager(methodInvocationContext, methodHierarchies, fields).toQuerySelection();
            ret.add(querySelection);
        }

        return ret;
    }

    private List<MethodInvocationContext> getMethodContext(ISST sst) {
        try {
            MethodInvocationContextVisitor visitor = new MethodInvocationContextVisitor();
            sst.accept(visitor, new MethodInvocationContext());
            return visitor.getFound();
        } catch (Exception e) {
        }

        return new ArrayList<>();
    }
}