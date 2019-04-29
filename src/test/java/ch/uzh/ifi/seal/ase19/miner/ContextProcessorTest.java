package ch.uzh.ifi.seal.ase19.miner;

import cc.kave.commons.model.events.completionevents.Context;
import cc.kave.commons.model.typeshapes.ITypeShape;
import ch.uzh.ifi.seal.ase19.core.PersistenceManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class ContextProcessorTest {

    private PersistenceManager pm;
    private ContextProcessor sut;

    @BeforeEach
    void before() {
        pm = mock(PersistenceManager.class);
        sut = new ContextProcessor(pm);
    }

    @Test
    void runAndPersist() {
        Context c = mock(Context.class);
        ITypeShape ts = mock(ITypeShape.class);
        when(ts.getMethodHierarchies()).thenReturn(null);
        when(ts.getFields()).thenReturn(null);
        when(c.getTypeShape()).thenReturn(ts);

        sut.runAndPersist(c);

        verify(pm, times(0)).save(any());
    }
}