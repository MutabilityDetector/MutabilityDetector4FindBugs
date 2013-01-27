package org.mutabilitydetector.findbugs.warningtypes;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.concurrent.Immutable;

@Immutable
public final class SafelyWrapsAndCopiesCollectionField {
    private final List<String> myStrings;

    public SafelyWrapsAndCopiesCollectionField(List<String> myStrings) {
        this.myStrings = Collections.unmodifiableList(new LinkedList<String>(myStrings));
    }
    
    public String firstString() {
        return myStrings.get(0);
    }
}
