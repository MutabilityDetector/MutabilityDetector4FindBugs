# MutabilityDetector4FindBugs


Run [Mutability Detector](https://github.com/MutabilityDetector/MutabilityDetector) during a [FindBugs](http://findbugs.sourceforge.net/) analysis to check all your classes annotated with `@Immutable`.

As of version 0.9.1, support for FindBugs 1.3.9 has been relegated to a branch, with a different artifact. FindBugs 2.0.0 was released in December 2011, I recommend upgrading.

## How to get MutabilityDetector4FindBugs



<table>
    <tr>
        <td colspan=1>FindBugs version
        <td colspan=1>Link</td>
        <td colspan=3>Maven Coordinates</td>
    <tr>
        <td>
        <td>
        <td>groupId</td>
        <td>artifactId</td>
        <td>version</td>
    </tr>
    <tr>
        <td>2.0.0+</td>
        <td><a href="http://search.maven.org/remotecontent?filepath=org/mutabilitydetector/MutabilityDetector4FindBugs/0.9.1/MutabilityDetector4FindBugs-0.9.1.jar">Download</a></td>
        <td>org.mutabilitydetector</td>
        <td>MutabilityDetector4FindBugs</td>
        <td>0.9.1</td>
    </tr>
    <tr>
        <td>1.3.9</td>
        <td><a href="http://search.maven.org/remotecontent?filepath=org/mutabilitydetector/MutabilityDetector4FindBugs-v139/0.9.1/MutabilityDetector4FindBugs-v139-0.9.1.jar">Download</a></td>
        <td>org.mutabilitydetector</td>
        <td>MutabilityDetector4FindBugs-v139</td>
        <td>0.9.1</td>
    </tr>
</table>

#### Currently waiting on Maven Central sync
The above may not yet be available from Maven Central. The previous version, 0.9, which only works for FindBugs v1.3.9, is available [here](http://search.maven.org/remotecontent?filepath=org/mutabilitydetector/MutabilityDetector4FindBugs/0.9/MutabilityDetector4FindBugs-0.9.jar). A temporary release, which works on FindBugs 2.0.0+, is available [here](https://github.com/MutabilityDetector/MutabilityDetector4FindBugs/releases/download/temporary-findbugs2-support-0.1/MutabilityDetector4FindBugs-0.10-SNAPSHOT.jar).


### See Also:

[How to configure a custom detector to run during FindBugs analysis](https://code.google.com/p/findbugs/wiki/DetectorPluginTutorial#Loading_Our_Plugin)

### Java 8 Compatibility
![Compatibility Badge](https://java.net/downloads/adoptopenjdk/compat.svg)

