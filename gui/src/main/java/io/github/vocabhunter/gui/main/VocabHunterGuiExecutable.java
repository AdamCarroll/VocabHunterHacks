/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.vocabhunter.gui.main;

import com.gluonhq.ignite.guice.GuiceContext;
import com.google.inject.Module;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.time.Instant;
import java.util.Collection;
import java.util.function.Consumer;
import javax.inject.Inject;

import static io.github.vocabhunter.gui.main.ExecutableLogTool.*;
import static java.util.Arrays.asList;

public class VocabHunterGuiExecutable extends Application {
    private static final Instant START = Instant.now();

    private static Collection<Module> modules;

    private final GuiceContext context = new GuiceContext(this, () -> modules);

    @Inject
    private VocabHunterGui vocabHunterGui;

    public static void setModules(final Module... m) {
        modules = asList(m);
    }

    @Override
    public void start(final Stage stage) {
        Thread.currentThread().setUncaughtExceptionHandler((t, e) -> logError(e));
        try {
            context.init();
            vocabHunterGui.start(stage, START);
        } catch (final RuntimeException e) {
            logError(e);
            Platform.exit();
        }
    }

    public static void main(final String... args) {
        runApp(args, Application::launch, new CoreGuiModule(), new LiveGuiModule(asList(args)), new StandardEventSourceModule());
    }

    protected static void runApp(final String[] args, final Consumer<String[]> launcher, final Module... modules) {
        logStartup();
        try {
            logSystemDetails();
            setModules(modules);
            launcher.accept(args);
        } finally {
            logShutdown();
        }
    }
}
