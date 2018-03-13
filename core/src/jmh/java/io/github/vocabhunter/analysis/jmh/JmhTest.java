/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.vocabhunter.analysis.jmh;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.github.vocabhunter.analysis.file.FileStreamer;
import io.github.vocabhunter.analysis.session.EnrichedSessionState;
import io.github.vocabhunter.analysis.simple.SimpleAnalyser;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import java.nio.file.Path;
import java.nio.file.Paths;

@State(Scope.Benchmark)
@SuppressFBWarnings("DMI_HARDCODED_ABSOLUTE_FILENAME")
public class JmhTest {
    private final FileStreamer streamer = new FileStreamer(new SimpleAnalyser());

    @Benchmark
    public EnrichedSessionState runTest() {
        Path file = Paths.get("big.txt");

        return streamer.createNewSession(file);
    }
}
