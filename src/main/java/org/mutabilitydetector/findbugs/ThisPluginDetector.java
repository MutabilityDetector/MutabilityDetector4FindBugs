package org.mutabilitydetector.findbugs;


import static org.mutabilitydetector.Configurations.OUT_OF_THE_BOX_CONFIGURATION;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import org.mutabilitydetector.AnalysisSession;
import org.mutabilitydetector.ThreadUnsafeAnalysisSession;
import org.mutabilitydetector.asmoverride.AsmVerifierFactory;
import org.mutabilitydetector.asmoverride.ClassLoadingVerifierFactory;
import org.mutabilitydetector.checkers.CheckerRunner.ExceptionPolicy;
import org.mutabilitydetector.checkers.ClassPathBasedCheckerRunnerFactory;
import org.mutabilitydetector.checkers.MutabilityCheckerFactory;
import org.mutabilitydetector.classloading.CachingAnalysisClassLoader;
import org.mutabilitydetector.classloading.ClassForNameWrapper;
import org.mutabilitydetector.cli.URLFallbackClassLoader;
import org.mutabilitydetector.repackaged.com.google.classpath.ClassPath;

import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.Detector;
import edu.umd.cs.findbugs.ba.ClassContext;
import edu.umd.cs.findbugs.classfile.Global;
import edu.umd.cs.findbugs.classfile.IClassPath;

public class ThisPluginDetector implements Detector {
    private static final String LOGGING_LABEL = MutabilityDetectorFindBugsPlugin.class.getSimpleName();
    
    static {
        System.out.format("Registered plugin detector [%s]%n", LOGGING_LABEL);
    }
    
    private final BugReporter bugReporter;
    
    public ThisPluginDetector(BugReporter bugReporter) {
        this.bugReporter = bugReporter;
    }

    public void report() { }
    
    public void visitClassContext(ClassContext classContext) {
        new MutabilityDetectorFindBugsPlugin(this, bugReporter, new AnalysisSessionHolder()).visitClassContext(classContext);
    }
    
    public static class AnalysisSessionHolder {
        private volatile AnalysisSession analysisSession = null;
        
        public AnalysisSession lazyGet() {
            if (analysisSession == null) {
                analysisSession = createNewAnalysisSession();
            }
            
            return analysisSession;
        }

        private AnalysisSession createNewAnalysisSession() {
            return makeFindBugsClasspathAvailable();
        }
        
        private AnalysisSession makeFindBugsClasspathAvailable() {
            IClassPath findBugsClassPath = Global.getAnalysisCache().getClassPath();
            
            try {
                List<String> codeBasePaths = new FBCodeBasePathExtractor().listOfCodeBasePaths(findBugsClassPath);
                
                ClassPath mutabilityDetectorClasspath = new FBClasspathConverter().createClassPathForCodeBases(codeBasePaths);
                AsmVerifierFactory verifierFactory = createClassLoadingVerifierFactory(codeBasePaths.toArray(new String[0]));

                return ThreadUnsafeAnalysisSession.createWithGivenClassPath(mutabilityDetectorClasspath, 
                        new ClassPathBasedCheckerRunnerFactory(mutabilityDetectorClasspath, ExceptionPolicy.FAIL_FAST), 
                        new MutabilityCheckerFactory(),
                        verifierFactory,
                        OUT_OF_THE_BOX_CONFIGURATION);
                
            } catch (InterruptedException e) {
                throw new ExceptionInInitializerError("Problem getting class path entries from FindBugs");
            }
        }
        
        private ClassLoadingVerifierFactory createClassLoadingVerifierFactory(String[] classPathFiles) {
            return new ClassLoadingVerifierFactory(
                    new CachingAnalysisClassLoader(
                            new URLFallbackClassLoader(getCustomClassLoader(classPathFiles), new ClassForNameWrapper())));
        }
        
        private URLClassLoader getCustomClassLoader(String[] classPathFiles) {
            List<URL> urlList = new ArrayList<URL>(classPathFiles.length);
            
            for (String classPathUrl : classPathFiles) {
                try {
                    URL toAdd = new File(classPathUrl).toURI().toURL();
                    urlList.add(toAdd);
                } catch (MalformedURLException e) {
                    System.err.format("Classpath option %s is invalid.", classPathUrl);
                }
            }
            return new URLClassLoader(urlList.toArray(new URL[urlList.size()]));
        }
    }

    
}
