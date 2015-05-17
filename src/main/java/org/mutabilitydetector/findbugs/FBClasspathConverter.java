package org.mutabilitydetector.findbugs;

import org.mutabilitydetector.internal.com.google.classpath.ClassPath;
import org.mutabilitydetector.internal.com.google.classpath.ClassPathFactory;

import java.io.File;
import java.util.List;

public class FBClasspathConverter {

    
    public ClassPath createClassPathForCodeBases(List<String> codeBasePaths) {
        StringBuilder allClassPathsInString = new StringBuilder();
        for (String classPathUrl : codeBasePaths) {
            allClassPathsInString.append(classPathUrl + File.pathSeparator);
        }
        
        return new ClassPathFactory().createFromPath(allClassPathsInString.toString());
    }

}
