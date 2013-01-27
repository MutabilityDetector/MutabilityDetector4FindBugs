/* 
 *   Copyright (c) 2008-2013 Graham Allan
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package org.mutabilitydetector.findbugs;

import static org.mutabilitydetector.locations.Dotted.dotted;

import java.util.Map;

import org.mutabilitydetector.AnalysisResult;
import org.mutabilitydetector.IsImmutable;
import org.mutabilitydetector.MutableReasonDetail;
import org.mutabilitydetector.findbugs.ThisPluginDetector.AnalysisSessionHolder;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.Detector;
import edu.umd.cs.findbugs.Priorities;
import edu.umd.cs.findbugs.ba.ClassContext;
import edu.umd.cs.findbugs.bcel.AnnotationDetector;

public class MutabilityDetectorFindBugsPlugin extends AnnotationDetector {

    private static final int PRIORITY_TO_REPORT = Priorities.NORMAL_PRIORITY;
    private final BugReporter bugReporter;
    private final Detector pluginToRegisterBugsWith;
    private final AnalysisSessionHolder analysisSessionHolder;
    
    private boolean doMutabilityDetectionOnCurrentClass;

    public MutabilityDetectorFindBugsPlugin(Detector pluginToRegisterBugsWith, BugReporter bugReporter, AnalysisSessionHolder analysisSessionHolder) {
        this.pluginToRegisterBugsWith = pluginToRegisterBugsWith;
        this.bugReporter = bugReporter;
        this.analysisSessionHolder = analysisSessionHolder;
    }

    
    @Override
    public void visitAnnotation(String annotationClass, Map<String, Object> map, boolean runtimeVisible) {
        super.visitAnnotation(annotationClass, map, runtimeVisible);
        
        doMutabilityDetectionOnCurrentClass = annotationClass.equals("Immutable") || annotationClass.endsWith(".Immutable");
    }
   
    public void visitClassContext(ClassContext classContext) {
        doMutabilityDetectionOnCurrentClass = false;

        super.visitClassContext(classContext);

        if (doMutabilityDetectionOnCurrentClass) {
            doMutabilityAnalysis(classContext);
        }
    }
    
    private void doMutabilityAnalysis(ClassContext classContext) {
        String toAnalyse = classContext.getClassDescriptor().getDottedClassName();
        AnalysisResult result = analysisSessionHolder.lazyGet().resultFor(dotted(toAnalyse));
        
        if (result.isImmutable != IsImmutable.IMMUTABLE) {
            
            for (MutableReasonDetail reasonDetail : result.reasons) {
                BugInstance bugInstance = new BugInstance(pluginToRegisterBugsWith, 
                                                          "MUTDEC_"+ reasonDetail.reason().code(), 
                                                          PRIORITY_TO_REPORT)
                    .addClass(classContext.getClassDescriptor());
                
                bugReporter.reportBug(bugInstance);
            }
        }
    }
   
    
    public void report() { }
    
}