/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.vocabhunter.gui.hack;

import io.github.vocabhunter.gui.dialogues.FileDialogue;
import io.github.vocabhunter.gui.dialogues.FileFormatType;

import java.nio.file.Path;

public class HackFileDialogue implements FileDialogue {
    private final Path testFile;

    public HackFileDialogue(final Path testFile) {
        this.testFile = testFile;
    }

    @Override
    public void showChooser() {
        // Do nothing
    }

    @Override
    public boolean isFileSelected() {
        return true;
    }

    @Override
    public Path getSelectedFile() {
        return testFile;
    }

    @Override
    public FileFormatType getFileFormatType() {
        return FileFormatType.TEXT;
    }
}
