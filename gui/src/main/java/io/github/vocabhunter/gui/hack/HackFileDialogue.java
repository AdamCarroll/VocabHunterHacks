/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.vocabhunter.gui.hack;

import io.github.vocabhunter.gui.dialogues.FileDialogue;
import io.github.vocabhunter.gui.dialogues.FileFormatType;

import java.nio.file.Path;
import java.nio.file.Paths;

public class HackFileDialogue implements FileDialogue {
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
        return Paths.get("big.txt");
    }

    @Override
    public FileFormatType getFileFormatType() {
        return FileFormatType.TEXT;
    }
}
