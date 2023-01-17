package fr.caemur.jelix.commands;

import fr.caemur.jelix.JelixEditor;
import fr.caemur.jelix.util.Result;
import fr.caemur.jelix.providers.FindProvider;

import static fr.caemur.jelix.providers.FindProvider.FindMode.*;

public class FindChar {
    public static Result findNextChar(JelixEditor editor) {
        editor.setActionProvider(new FindProvider(NEXT));
        return Result.CONTINUE;
    }

    public static Result findPreviousChar(JelixEditor editor) {
        editor.setActionProvider(new FindProvider(PREVIOUS));
        return Result.CONTINUE;
    }

    public static Result tillNextChar(JelixEditor editor) {
        editor.setActionProvider(new FindProvider(TILL_NEXT));
        return Result.CONTINUE;
    }

    public static Result tillPreviousChar(JelixEditor editor) {
        editor.setActionProvider(new FindProvider(TILL_PREVIOUS));
        return Result.CONTINUE;
    }
}
