package fr.caemur.jelix.providers;

import fr.caemur.jelix.Command;
import fr.caemur.jelix.Key;

public class InsertProvider extends CommandProvider {
    public InsertProvider() {
        super.bind(Command.ESCAPE, Key.INS_ESCAPE);
        super.bind(Command.DELETE_WORD, Key.INS_DEL_WORD);
        super.bind(Command.DELETE_TO_START, Key.INS_DEL_TO_START);
        super.bind(Command.DELETE_TO_END, Key.INS_DEL_TO_END);
    }
}
