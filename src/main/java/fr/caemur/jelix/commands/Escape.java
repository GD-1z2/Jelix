package fr.caemur.jelix.commands;

import fr.caemur.jelix.JelixEditor;
import fr.caemur.jelix.util.Mode;
import fr.caemur.jelix.util.Result;

public final class Escape {
    public static Result escape(JelixEditor editor) {
        editor.setMode(Mode.NORMAL);
        return Result.EXECUTE;
    }
}
