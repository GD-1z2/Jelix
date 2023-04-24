package fr.caemur.jelix.commands;

import fr.caemur.jelix.JelixEditor;
import fr.caemur.jelix.util.Result;

public class Repeat {
    public static Result repeatLastChange(JelixEditor jelixEditor) {
        var lastChange = jelixEditor.getLastChange();
        if (lastChange != null) {
            lastChange.accept(jelixEditor);
            return Result.EXECUTE;
        }
        return Result.FAILED;
    }

    public static Result repeatLastMotion(JelixEditor jelixEditor) {
        var lastMotion = jelixEditor.getLastMotion();
        if (lastMotion != null) {
            lastMotion.accept(jelixEditor);
            return Result.EXECUTE;
        }
        return Result.FAILED;
    }
}
