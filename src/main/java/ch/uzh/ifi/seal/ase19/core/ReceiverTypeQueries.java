package ch.uzh.ifi.seal.ase19.core;

import ch.uzh.ifi.seal.ase19.core.models.QuerySelection;

import java.util.ArrayList;
import java.util.List;

public class ReceiverTypeQueries {
    private List<QuerySelection> items;

    public ReceiverTypeQueries(){
        items = new ArrayList<>();
    }

    public void addItem(QuerySelection qs){
        boolean found = false;

        for(QuerySelection item : items){
            if(item.same(qs)){
                item.addToFrequence(qs.getFrequency());
                found = true;
                break;
            }
        }

        if(!found){
            items.add(qs);
        }
    }

    public List<QuerySelection> getItems() {
        return items;
    }
}
