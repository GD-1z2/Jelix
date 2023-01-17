package fr.caemur.jelix.commands;

import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.LogicalPosition;
import fr.caemur.jelix.JelixEditor;
import fr.caemur.jelix.util.Result;

public class MultiCursor {
    public static Result cloneAbove(JelixEditor jelixEditor) {
        // find next line(s), where the selection's start and end are valid positions
        //      if found, add new primary caret at this position with same selection
        // note : we can't use intellij's Caret#clone because it can place caret at wrong columns

        var editor = jelixEditor.getEditor();
        jelixEditor.getEditor().getCaretModel().runForEachCaret(caret -> {
            var selStart = editor.offsetToLogicalPosition(caret.getSelectionStart());
            var selEnd = editor.offsetToLogicalPosition(caret.getSelectionEnd());
            var selLines = selEnd.line - selStart.line + 1;
            var found = false;
            var line = selStart.line - selLines + 1;
            while (!found && line > 0) {
                line--;
                found = isLineValidForSelection(editor, line, selLines, selStart, selEnd);
            }
            if (found) copyCaret(editor, caret, line, selLines, selStart, selEnd);
        });
        return Result.EXECUTE;
    }

    public static Result cloneBelow(JelixEditor jelixEditor) {
        var editor = jelixEditor.getEditor();
        jelixEditor.getEditor().getCaretModel().runForEachCaret(caret -> {
            var selStart = editor.offsetToLogicalPosition(caret.getSelectionStart());
            var selEnd = editor.offsetToLogicalPosition(caret.getSelectionEnd());
            var selLines = selEnd.line - selStart.line + 1;
            var lineCount = editor.getDocument().getLineCount();
            var found = false;
            var line = selStart.line + selLines - 1;
            while (!found && line + selLines < lineCount) {
                line++;
                found = isLineValidForSelection(editor, line, selLines, selStart, selEnd);
            }
            if (found) copyCaret(editor, caret, line, selLines, selStart, selEnd);
        });
        return Result.EXECUTE;
    }

    public static Result prevCursor(JelixEditor jelixEditor) {
        return changePrimaryCaret(jelixEditor.getEditor(), false);
    }

    public static Result nextCursor(JelixEditor jelixEditor) {
        return changePrimaryCaret(jelixEditor.getEditor(), true);
    }

    public static Result removeCursor(JelixEditor jelixEditor) {
        var caret = jelixEditor.getEditor().getCaretModel();
        return caret.removeCaret(caret.getPrimaryCaret())
            ? Result.EXECUTE : Result.FAILED;
    }

    public static Result removeOtherCursors(JelixEditor jelixEditor) {
        var caretModel = jelixEditor.getEditor().getCaretModel();
        for (var caret : caretModel.getAllCarets()) {
            if (caret != caretModel.getPrimaryCaret()) {
                caretModel.removeCaret(caret);
            }
        }
        return Result.EXECUTE;
    }

    private static boolean isLineValidForSelection(Editor editor, int line, int selLines,
                                                   LogicalPosition selStart, LogicalPosition selEnd) {
        var lineStart = editor.getDocument().getLineStartOffset(line);
        var lineEnd = editor.getDocument().getLineEndOffset(line);

        if (selStart.column <= lineEnd - lineStart) {
            // Selection start is within line ; checking if selection end is within line
            lineStart = editor.getDocument().getLineStartOffset(line + selLines - 1);
            lineEnd = editor.getDocument().getLineEndOffset(line + selLines - 1);
            return selEnd.column <= lineEnd - lineStart;
        }
        return false;
    }

    private static void copyCaret(Editor editor, Caret caret, int line, int selLines,
                                  LogicalPosition selStart, LogicalPosition selEnd) {
        var newCaret = editor.getCaretModel().addCaret(new LogicalPosition(
            line - selStart.line + caret.getLogicalPosition().line,
            caret.getLogicalPosition().column
        ), true);
        if (newCaret != null) {
            newCaret.setSelection(
                editor.getDocument().getLineStartOffset(line) + selStart.column,
                editor.getDocument().getLineStartOffset(line + selLines - 1) + selEnd.column
            );
        }
    }

    private static Result changePrimaryCaret(Editor editor, boolean next) {
        var model = editor.getCaretModel();
        var carets = model.getAllCarets();
        if (carets.size() == 0) {
            return Result.FAILED;
        }
        Caret oldPrimary;
        if (next) {
            oldPrimary = carets.get((carets.indexOf(model.getPrimaryCaret()) + 1) % carets.size());
        } else {
            oldPrimary = carets.get((carets.indexOf(model.getPrimaryCaret()) - 1 + carets.size()) % carets.size());
        }
        // remove caret, then add it back with the same position and selection
        model.removeCaret(oldPrimary);
        var newPrimary = model.addCaret(oldPrimary.getLogicalPosition(), true);
        if (newPrimary == null) {
            return Result.FAILED;
        }
        newPrimary.setSelection(oldPrimary.getSelectionStart(), oldPrimary.getSelectionEnd());
        return Result.EXECUTE;
    }
}
