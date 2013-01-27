package org.mutabilitydetector.findbugs.warningtypes;

import javax.annotation.concurrent.Immutable;

@Immutable
public final class HasStringField {
    public final String myString;

    public HasStringField(String myString) {
        this.myString = myString;
    }
    
}
