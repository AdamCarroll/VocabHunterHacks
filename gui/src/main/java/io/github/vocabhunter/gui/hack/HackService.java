/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.vocabhunter.gui.hack;

import io.github.vocabhunter.analysis.core.CoreConstants;
import io.github.vocabhunter.analysis.core.DelayedExecutor;
import io.github.vocabhunter.analysis.core.ThreadPoolTool;
import io.github.vocabhunter.analysis.core.VocabHunterException;
import io.github.vocabhunter.gui.controller.MainController;
import javafx.application.Platform;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.NumberFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class HackService {
    private static final int OPEN_FILE_COUNT = 10;

    private static final Logger LOG = LoggerFactory.getLogger(HackService.class);

    private final DelayedExecutor executor;

    private final MainController mainController;

    private final AtomicReference<CountDownLatch> latch = new AtomicReference<>();

    private final AtomicReference<Instant> start = new AtomicReference<>();

    @Inject
    public HackService(final ThreadPoolTool threadPoolTool, final MainController mainController) {
        executor = threadPoolTool.delayedExecutor("hacker", 1);
        this.mainController = mainController;

        Runtime.getRuntime().addShutdownHook(new Thread(this::logShutdown));
        executor.execute(this::runHack);
   }

    public void beginHack(final Instant start) {
        this.start.set(start);
        executor.beginExecution();
    }

    private void runHack() {
        try {
            LOG.info("Begin hack");

            for (int i = 0; i < OPEN_FILE_COUNT; i++) {
                LOG.info("File open {}", i + 1);
                latch.set(new CountDownLatch(1));
                Platform.runLater(() -> mainController.hackNew());
                latch.get().await();
            }
            LOG.info("Closing");
            Platform.runLater(() -> mainController.hackClose());
        } catch (InterruptedException e) {
            throw new VocabHunterException("Bang", e);
        }
    }

    private void logShutdown() {
        long duration = Duration.between(start.get(), Instant.now()).toMillis();
        String longForm = DurationFormatUtils.formatDurationWords(duration, true, false);
        String shortForm = NumberFormat.getIntegerInstance(CoreConstants.LOCALE).format(duration);

        LOG.info("Total duration {} ({} ms)", longForm, shortForm);
    }

    public void finishOpen() {
        latch.get().countDown();
    }
}
