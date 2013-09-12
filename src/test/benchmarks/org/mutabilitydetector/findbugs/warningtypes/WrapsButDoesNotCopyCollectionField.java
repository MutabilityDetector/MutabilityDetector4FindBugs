package org.mutabilitydetector.findbugs.warningtypes;

import java.util.Collections;
import java.util.List;

import javax.annotation.concurrent.Immutable;

@Immutable
public final class WrapsButDoesNotCopyCollectionField {
    private final List<String> myStrings;

    public WrapsButDoesNotCopyCollectionField(List<String> myStrings) {
        this.myStrings = Collections.unmodifiableList(myStrings);
    }
    
    public String firstString() {
        return myStrings.get(0);
    }
    
}
