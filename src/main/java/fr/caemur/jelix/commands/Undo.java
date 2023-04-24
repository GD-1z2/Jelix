package fr.caemur.jelix.commands;

import com.intellij.ide.actions.RedoAction;
import com.intellij.ide.actions.UndoAction;
import com.intellij.openapi.actionSystem.ex.ActionUtil;
import fr.caemur.jelix.JelixEditor;
import fr.caemur.jelix.util.Result;

public class Undo {
    public static Result undo(JelixEditor jelixEditor) {
        ActionUtil.invokeAction(
            new UndoAction(),
            jelixEditor.getEditor().getComponent(),
            "", null, null);
        return Result.EXECUTE;
    }

    public static Result redo(JelixEditor jelixEditor) {
        ActionUtil.invokeAction(
            new RedoAction(),
            jelixEditor.getEditor().getComponent(),
            "", null, null);
        return Result.EXECUTE;
    }
}
