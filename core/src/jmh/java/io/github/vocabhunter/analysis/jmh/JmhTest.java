/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.vocabhunter.analysis.jmh;

import io.github.vocabhunter.analysis.session.SessionWordsTool;
import io.github.vocabhunter.analysis.session.SessionWordsToolImpl;
import org.openjdk.jmh.annotations.Benchmark;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class JmhTest {
    @Benchmark
    public List<String> runTest() {
        Path file = Paths.get("/full/path/sample.wordy");
        SessionWordsTool target = new SessionWordsToolImpl();

        return target.knownWords(file);
    }
}
