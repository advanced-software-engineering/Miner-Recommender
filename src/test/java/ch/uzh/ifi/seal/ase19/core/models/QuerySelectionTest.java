package ch.uzh.ifi.seal.ase19.core.models;

import cc.kave.commons.model.naming.codeelements.IMemberName;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

class QuerySelectionTest {

    @Test
    void addToFrequency() {
        QuerySelection qs = new QuerySelection(mock(Query.class), mock(IMemberName.class), 0);
        long frequency = 10;
        qs.addToFrequency(frequency);
        Assertions.assertEquals(frequency, qs.getFrequency());
    }

    @Test
    void same() {
        Query q = mock(Query.class);
        IMemberName mn = mock(IMemberName.class);
        QuerySelection qs1 = new QuerySelection(q, mn);
        QuerySelection qs2 = new QuerySelection(q, mn);

        Assertions.assertTrue(qs1.same(qs2));
    }

    @Test
    void sameWithDifferentFrequency() {
        Query q = mock(Query.class);
        IMemberName mn = mock(IMemberName.class);
        QuerySelection qs1 = new QuerySelection(q, mn, 1);
        QuerySelection qs2 = new QuerySelection(q, mn, 2);

        Assertions.assertTrue(qs1.same(qs2));
    }

    @Test
    void notSame() {
        IMemberName mn = mock(IMemberName.class);
        QuerySelection qs1 = new QuerySelection(mock(Query.class), mn);
        QuerySelection qs2 = new QuerySelection(mock(Query.class), mn);

        Assertions.assertFalse(qs1.same(qs2));
    }

    @Test
    void getCopy() {
        QuerySelection qs = new QuerySelection(mock(Query.class), mock(IMemberName.class));
        QuerySelection copy = qs.getCopy();

        Assertions.assertEquals(qs, copy);
        Assertions.assertFalse(qs == copy);
    }

    @Test
    void testEquals() {
        Query q = mock(Query.class);
        IMemberName mn = mock(IMemberName.class);
        QuerySelection qs1 = new QuerySelection(q, mn);
        QuerySelection qs2 = new QuerySelection(q, mn);

        Assertions.assertEquals(qs1, qs2);
    }

    @Test
    void notEquals() {
        IMemberName mn = mock(IMemberName.class);
        QuerySelection qs1 = new QuerySelection(mock(Query.class), mn);
        QuerySelection qs2 = new QuerySelection(mock(Query.class), mn);

        Assertions.assertNotEquals(qs1, qs2);
    }

    @Test
    void testHashCode() {
        Query q = mock(Query.class);
        IMemberName mn = mock(IMemberName.class);
        QuerySelection qs1 = new QuerySelection(q, mn);
        QuerySelection qs2 = new QuerySelection(q, mn);

        Assertions.assertEquals(qs1.hashCode(), qs2.hashCode());
    }

    @Test
    void notSameHashCode() {
        IMemberName mn = mock(IMemberName.class);
        QuerySelection qs1 = new QuerySelection(mock(Query.class), mn);
        QuerySelection qs2 = new QuerySelection(mock(Query.class), mn);

        Assertions.assertNotEquals(qs1.hashCode(), qs2.hashCode());
    }
}