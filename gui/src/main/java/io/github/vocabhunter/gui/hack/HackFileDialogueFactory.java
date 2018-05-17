/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.vocabhunter.gui.hack;

import io.github.vocabhunter.gui.dialogues.FileDialogue;
import io.github.vocabhunter.gui.dialogues.FileDialogueFactory;
import io.github.vocabhunter.gui.dialogues.FileDialogueType;
import javafx.stage.Stage;

import java.nio.file.Path;

public class HackFileDialogueFactory implements FileDialogueFactory {
    private final Path testFile;

    public HackFileDialogueFactory(final Path testFile) {
        this.testFile = testFile;
    }

    @Override
    public FileDialogue create(final FileDialogueType type, final Stage stage) {
        return new HackFileDialogue(testFile);
    }
}
