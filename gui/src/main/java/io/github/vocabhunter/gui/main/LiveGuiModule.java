/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.vocabhunter.gui.main;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.github.vocabhunter.analysis.core.GuiTaskHandler;
import io.github.vocabhunter.analysis.core.GuiTaskHandlerImpl;
import io.github.vocabhunter.analysis.core.ThreadPoolTool;
import io.github.vocabhunter.analysis.core.VocabHunterException;
import io.github.vocabhunter.analysis.settings.FileListManager;
import io.github.vocabhunter.analysis.settings.FileListManagerImpl;
import io.github.vocabhunter.gui.controller.MainController;
import io.github.vocabhunter.gui.dialogues.FileDialogueFactory;
import io.github.vocabhunter.gui.hack.HackFileDialogueFactory;
import io.github.vocabhunter.gui.hack.HackService;
import io.github.vocabhunter.gui.services.*;
import io.github.vocabhunter.gui.settings.SettingsManager;
import io.github.vocabhunter.gui.settings.SettingsManagerImpl;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javax.inject.Singleton;

public class LiveGuiModule extends AbstractModule {
    private static final String ERROR_MESSAGE = "Usage: <test-file> <open-count>";

    private final Path testFile;

    private final int openFileCount;

    public LiveGuiModule(final List<String> commandLineArgs) {
        try {
            if (commandLineArgs.size() == 2) {
                testFile = Paths.get(commandLineArgs.get(0));
                openFileCount = Integer.parseInt(commandLineArgs.get(1));
            } else {
                throw new VocabHunterException("Usage: <test-file> <open-count>");
            }
        } catch (NumberFormatException e) {
            throw new VocabHunterException(ERROR_MESSAGE, e);
        }
    }

    @Override
    protected void configure() {
        bind(SettingsManager.class).to(SettingsManagerImpl.class);
        bind(FileListManager.class).to(FileListManagerImpl.class);
        bind(FileDialogueFactory.class).toInstance(new HackFileDialogueFactory(testFile));
        bind(PlacementManager.class).to(PlacementManagerImpl.class);
        bind(EnvironmentManager.class).to(EnvironmentManagerImpl.class);
        bind(WebPageTool.class).to(WebPageToolImpl.class);
        bind(GuiTaskHandler.class).to(GuiTaskHandlerImpl.class);
    }

    @Provides
    @Singleton
    public HackService hackService(final ThreadPoolTool threadPoolTool, final MainController mainController) {
        return new HackService(threadPoolTool, mainController, openFileCount);
    }
}
