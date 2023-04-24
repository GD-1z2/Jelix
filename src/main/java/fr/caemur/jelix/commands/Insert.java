package fr.caemur.jelix.commands;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.LogicalPosition;
import com.intellij.openapi.editor.actions.EditorActionUtil;
import com.intellij.openapi.editor.actions.EnterAction;
import fr.caemur.jelix.JelixEditor;
import fr.caemur.jelix.providers.InsertProvider;
import fr.caemur.jelix.util.Mode;
import fr.caemur.jelix.util.Result;

public class Insert {
    public static Result insert(JelixEditor editor) {
        editor.getEditor().getCaretModel().runForEachCaret(c -> {
            c.moveToOffset(c.getSelectionStart());
            c.removeSelection();
        });

        editor.setMode(Mode.INSERT);
        editor.setActionProvider(new InsertProvider());

        return Result.EXECUTE;
    }

    public static Result insertStart(JelixEditor editor) {
        editor.getEditor().getCaretModel().runForEachCaret(c -> {
            c.removeSelection();
            c.moveToOffset(EditorActionUtil.findFirstNonSpaceOffsetOnTheLine(
                editor.getEditor().getDocument(), c.getLogicalPosition().line));
        });

        editor.setMode(Mode.INSERT);
        editor.setActionProvider(new InsertProvider());

        return Result.EXECUTE;
    }

    public static Result append(JelixEditor editor) {
        editor.getEditor().getCaretModel().runForEachCaret(c -> {
            c.moveToOffset(c.getSelectionEnd());
            c.removeSelection();
        });

        editor.setMode(Mode.INSERT);
        editor.setActionProvider(new InsertProvider());

        return Result.EXECUTE;
    }

    public static Result appendEnd(JelixEditor editor) {
        editor.getEditor().getCaretModel().runForEachCaret(c -> {
            c.removeSelection();
            c.moveToOffset(editor.getEditor().getDocument().getLineEndOffset(c.getLogicalPosition().line));
        });

        editor.setMode(Mode.INSERT);
        editor.setActionProvider(new InsertProvider());

        return Result.EXECUTE;
    }

    public static Result insertNewLine(JelixEditor editor) {
        var ed = editor.getEditor();
        WriteCommandAction.runWriteCommandAction(editor.getEditor().getProject(), () -> {
            ed.getSelectionModel().removeSelection();
            ed.getCaretModel().runForEachCaret(c -> {
                c.moveToOffset(ed.getDocument().getLineEndOffset(c.getLogicalPosition().line));
                EnterAction.insertNewLineAtCaret(ed);
            });
        });

        editor.setMode(Mode.INSERT);
        editor.setActionProvider(new InsertProvider());

        return Result.EXECUTE;
    }

    public static Result insertNewLineAbove(JelixEditor editor) {
        var ed = editor.getEditor();
        ed.getSelectionModel().removeSelection();

        WriteCommandAction.runWriteCommandAction(editor.getEditor().getProject(),
            () -> ed.getCaretModel().runForEachCaret(c -> {
                c.moveToOffset(EditorActionUtil.findFirstNonSpaceOffsetOnTheLine(
                    ed.getDocument(), c.getLogicalPosition().line));
                EnterAction.insertNewLineAtCaret(ed);
                c.moveToLogicalPosition(new LogicalPosition(c.getLogicalPosition().line - 1, 0));
                c.moveToOffset(EditorActionUtil.findFirstNonSpaceOffsetOnTheLine(
                    ed.getDocument(), c.getLogicalPosition().line));
            }));

        editor.setMode(Mode.INSERT);
        editor.setActionProvider(new InsertProvider());

        return Result.EXECUTE;
    }

    public static Result change(JelixEditor editor) {
        Deletion.delete(editor);

        editor.setMode(Mode.INSERT);
        editor.setActionProvider(new InsertProvider());

        return Result.EXECUTE;
    }
}
