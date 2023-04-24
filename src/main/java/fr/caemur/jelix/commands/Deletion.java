package fr.caemur.jelix.commands;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.actions.EditorActionUtil;
import fr.caemur.jelix.JelixEditor;
import fr.caemur.jelix.util.Result;

public class Deletion {
    public static Result delete(JelixEditor jelixEditor) {
        var ed = jelixEditor.getEditor();
        WriteCommandAction.runWriteCommandAction(ed.getProject(), "", "",
            () -> ed.getCaretModel().runForEachCaret(caret -> {
                var doc = ed.getDocument();
                doc.deleteString(caret.getSelectionStart(), caret.getSelectionEnd());
            }));
        return Result.EXECUTE;
    }

    public static Result deleteWord(JelixEditor jelixEditor) {
        var ed = jelixEditor.getEditor();
        WriteCommandAction.runWriteCommandAction(ed.getProject(), "", "",
            () -> ed.getCaretModel().runForEachCaret(caret -> {
                if (caret.getOffset() == 0) {
                    return;
                }
                var offset = caret.getOffset() - 1;
                var doc = ed.getDocument();
                var text = doc.getCharsSequence();
                while (offset >= 0 && !Character.isWhitespace(text.charAt(offset))) {
                    offset--;
                }
                doc.deleteString(offset + 1, caret.getOffset());
            }));
        return Result.EXECUTE;
    }

    public static Result deleteToStart(JelixEditor jelixEditor) {
        var ed = jelixEditor.getEditor();
        WriteCommandAction.runWriteCommandAction(ed.getProject(), "", "",
            () -> ed.getCaretModel().runForEachCaret(caret -> {
                var doc = ed.getDocument();
                var offset = caret.getOffset();
                var line = caret.getLogicalPosition().line;
                var start = EditorActionUtil.findFirstNonSpaceOffsetOnTheLine(doc, line);
                if (start == offset) {
                    start = doc.getLineStartOffset(line);
                }
                doc.deleteString(start, offset);
            }));
        return Result.EXECUTE;
    }

    public static Result deleteToEnd(JelixEditor jelixEditor) {
        var ed = jelixEditor.getEditor();
        WriteCommandAction.runWriteCommandAction(ed.getProject(), "", "",
            () -> ed.getCaretModel().runForEachCaret(caret -> {
                var offset = caret.getOffset();
                var doc = ed.getDocument();
                var end = doc.getLineEndOffset(ed.getCaretModel().getLogicalPosition().line);
                doc.deleteString(offset, end);
            }));
        return Result.EXECUTE;
    }
}
