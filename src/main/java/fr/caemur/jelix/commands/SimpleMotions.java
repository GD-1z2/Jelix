package fr.caemur.jelix.commands;

import com.intellij.openapi.editor.ScrollType;
import fr.caemur.jelix.JelixEditor;
import fr.caemur.jelix.util.Result;

public class SimpleMotions {
    public static Result moveLeft(JelixEditor editor) {
        return moveCarets(editor, -1, 0);
    }

    public static Result moveRight(JelixEditor editor) {
        return moveCarets(editor, 1, 0);
    }

    public static Result moveUp(JelixEditor editor) {
        return moveCarets(editor, 0, -1);
    }

    public static Result moveDown(JelixEditor editor) {
        return moveCarets(editor, 0, 1);
    }

    private static Result moveCarets(JelixEditor editor, int x, int y) {
        editor.getEditor().getCaretModel().runForEachCaret(
            c -> c.moveCaretRelatively(x * editor.getCount(), y * editor.getCount(),
                false, false)
        );
        editor.getEditor().getScrollingModel().scrollToCaret(ScrollType.RELATIVE);
        return Result.EXECUTE;
    }
}
