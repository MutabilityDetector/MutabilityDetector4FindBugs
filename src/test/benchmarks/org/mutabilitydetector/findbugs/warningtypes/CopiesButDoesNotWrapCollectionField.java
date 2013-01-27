package org.mutabilitydetector.findbugs.warningtypes;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.concurrent.Immutable;

@Immutable
public final class CopiesButDoesNotWrapCollectionField {

    private final List<String> myStrings;

    public CopiesButDoesNotWrapCollectionField(List<String> myStrings) {
        this.myStrings = new ArrayList<String>(myStrings);
    }
    
    public String firstString() {
        return myStrings.get(0);
    }
   
}
