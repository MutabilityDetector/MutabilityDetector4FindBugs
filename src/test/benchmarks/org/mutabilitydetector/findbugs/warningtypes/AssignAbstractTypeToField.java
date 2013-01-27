package org.mutabilitydetector.findbugs.warningtypes;

import java.awt.event.MouseAdapter;

import javax.annotation.concurrent.Immutable;

@Immutable
public final class AssignAbstractTypeToField {

    private final MouseAdapter couldBeAnyImplementation;

    public AssignAbstractTypeToField(MouseAdapter couldBeAnyImplementation) {
        this.couldBeAnyImplementation = couldBeAnyImplementation;
    }

    public String firstString() {
        return couldBeAnyImplementation.toString();
    }
    
}
