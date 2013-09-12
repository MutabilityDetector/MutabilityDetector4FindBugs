package org.mutabilitydetector.findbugs.warningtypes;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.concurrent.Immutable;

@Immutable
public final class CopiesAndWrapsCollectionFieldOfMutableType {
    private final List<Date> myDates;

    public CopiesAndWrapsCollectionFieldOfMutableType(List<Date> myDates) {
        this.myDates = Collections.unmodifiableList(new LinkedList<Date>(myDates));
    }
    
    public Date firstDate() {
        return myDates.get(0);
    }
}
