/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.vocabhunter.gui.hack;

import io.github.vocabhunter.gui.dialogues.FileDialogue;
import io.github.vocabhunter.gui.dialogues.FileDialogueFactory;
import io.github.vocabhunter.gui.dialogues.FileDialogueType;
import javafx.stage.Stage;

public class HackFileDialogueFactory implements FileDialogueFactory {
    @Override
    public FileDialogue create(final FileDialogueType type, final Stage stage) {
        return new HackFileDialogue();
    }
}
