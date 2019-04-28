package ch.uzh.ifi.seal.ase19.core;

import cc.kave.commons.model.naming.codeelements.IMemberName;
import ch.uzh.ifi.seal.ase19.core.models.Query;
import ch.uzh.ifi.seal.ase19.core.models.QuerySelection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

class ReceiverTypeQueriesTest {
    private ReceiverTypeQueries sut = new ReceiverTypeQueries();
    private QuerySelection item1 = new QuerySelection(mock(Query.class), mock(IMemberName.class));
    private QuerySelection item2 = new QuerySelection(mock(Query.class), mock(IMemberName.class), 2);

    @Test
    void empty() {
        Assertions.assertEquals(0, sut.getItems().size());
    }

    @Test
    void addItemSingleItem() {
        sut.addItem(item1);

        Assertions.assertEquals(1, sut.getItems().size());
        Assertions.assertEquals(1, sut.getItems().get(0).getFrequency());
    }

    @Test
    void addItemTwice() {
        sut.addItem(item1);
        sut.addItem(item1);

        Assertions.assertEquals(1, sut.getItems().size());
        Assertions.assertEquals(2, sut.getItems().get(0).getFrequency());
    }

    @Test
    void addModifiedItem() {
        sut.addItem(item1);
        item1.addToFrequence(5);
        sut.addItem(item1);

        Assertions.assertEquals(1, sut.getItems().size());
        Assertions.assertEquals(7, sut.getItems().get(0).getFrequency());
    }

    @Test
    void addDifferentItems() {
        sut.addItem(item1);
        sut.addItem(item2);

        Assertions.assertEquals(2, sut.getItems().size());
    }

    @Test
    void addComplex() {
        sut.addItem(item1);
        item1.addToFrequence(5);
        sut.addItem(item2);
        sut.addItem(item1);

        Assertions.assertEquals(2, sut.getItems().size());
        Assertions.assertEquals(7, sut.getItems().get(0).getFrequency());
        Assertions.assertEquals(2, sut.getItems().get(1).getFrequency());
    }
}