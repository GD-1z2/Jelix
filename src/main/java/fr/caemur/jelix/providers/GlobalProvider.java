package fr.caemur.jelix.providers;

import fr.caemur.jelix.Command;
import fr.caemur.jelix.JelixEditor;
import fr.caemur.jelix.Key;

import java.util.List;

public class GlobalProvider extends PopupProvider {
    public GlobalProvider(JelixEditor jelixEditor) {
        super(jelixEditor, "Global",
            List.of(
                new ListCmd('f', Command.SHOW_FILE_PICKER),
                new ListCmd('s', Command.SHOW_SYMBOL_PICKER),
                new ListCmd('k', Command.SHOW_DOCS),
                new ListCmd('r', Command.RENAME_SYMBOL),
                new ListCmd('?', Command.SHOW_COMMAND_PICKER)
            ));
        super.bind(Command.ESCAPE, Key.INS_ESCAPE);
    }
}
