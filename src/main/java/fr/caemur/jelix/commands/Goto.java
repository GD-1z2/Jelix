package fr.caemur.jelix.commands;

import com.intellij.codeInsight.navigation.actions.GotoDeclarationAction;
import com.intellij.codeInsight.navigation.actions.GotoImplementationAction;
import com.intellij.codeInsight.navigation.actions.GotoTypeDeclarationAction;
import com.intellij.find.actions.ShowUsagesAction;
import com.intellij.ide.actions.NextTabAction;
import com.intellij.ide.actions.PreviousTabAction;
import com.intellij.openapi.actionSystem.ex.ActionUtil;
import com.intellij.openapi.editor.*;
import fr.caemur.jelix.JelixEditor;
import fr.caemur.jelix.providers.GotoProvider;
import fr.caemur.jelix.util.Result;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

import static com.intellij.openapi.editor.actions.EditorActionUtil.selectNonexpandableFold;

public class Goto {
    public static Result gotoMenu(JelixEditor jelixEditor) {
        jelixEditor.setActionProvider(new GotoProvider(jelixEditor));
        return Result.CONTINUE;
    }

    public static Result gotoLine(JelixEditor jelixEditor) {
        var line = jelixEditor.getCount();
        line = Math.max(line, 1);
        line = Math.min(line, jelixEditor.getEditor().getDocument().getLineCount());

        var offset = jelixEditor.getEditor().getDocument().getLineStartOffset(line - 1);
        jelixEditor.getEditor().getCaretModel().moveToOffset(offset);
        jelixEditor.getEditor().getScrollingModel().scrollToCaret(ScrollType.MAKE_VISIBLE);
        return Result.EXECUTE;
    }

    public static Result gotoEnd(JelixEditor jelixEditor) {
        var line = jelixEditor.getEditor().getDocument().getLineCount();
        var offset = jelixEditor.getEditor().getDocument().getLineEndOffset(line - 1);
        jelixEditor.getEditor().getCaretModel().moveToOffset(offset);
        jelixEditor.getEditor().getScrollingModel().scrollToCaret(ScrollType.MAKE_VISIBLE);
        return Result.EXECUTE;
    }

    public static Result gotoLineStart(JelixEditor jelixEditor) {
        var line = jelixEditor.getEditor().getCaretModel().getLogicalPosition().line;
        var offset = jelixEditor.getEditor().getDocument().getLineStartOffset(line);
        jelixEditor.getEditor().getCaretModel().moveToOffset(offset);
        jelixEditor.getEditor().getScrollingModel().scrollToCaret(ScrollType.MAKE_VISIBLE);
        return Result.EXECUTE;
    }

    public static Result gotoLineEnd(JelixEditor jelixEditor) {
        var line = jelixEditor.getEditor().getCaretModel().getLogicalPosition().line;
        var offset = jelixEditor.getEditor().getDocument().getLineEndOffset(line);
        jelixEditor.getEditor().getCaretModel().moveToOffset(offset);
        jelixEditor.getEditor().getScrollingModel().scrollToCaret(ScrollType.MAKE_VISIBLE);
        return Result.EXECUTE;
    }

    public static Result gotoFirstChar(JelixEditor jelixEditor) {
        var line = jelixEditor.getEditor().getCaretModel().getLogicalPosition().line;
        var offset = jelixEditor.getEditor().getDocument().getLineStartOffset(line);
        var text = jelixEditor.getEditor().getDocument().getText().substring(offset);
        var firstChar = text.indexOf(text.trim());
        jelixEditor.getEditor().getCaretModel().moveToOffset(offset + firstChar);
        jelixEditor.getEditor().getScrollingModel().scrollToCaret(ScrollType.MAKE_VISIBLE);
        return Result.EXECUTE;
    }

    public static Result gotoDefinition(JelixEditor jelixEditor) {
        ActionUtil.invokeAction(
            new GotoDeclarationAction(),
            jelixEditor.getEditor().getContentComponent(),
            "", null, null
        );
        return Result.EXECUTE;
    }

    public static Result gotoTypeDefinition(JelixEditor jelixEditor) {
        ActionUtil.invokeAction(
            new GotoTypeDeclarationAction(),
            jelixEditor.getEditor().getContentComponent(),
            "", null, null
        );
        return Result.EXECUTE;
    }

    public static Result gotoReferences(JelixEditor jelixEditor) {
        ActionUtil.invokeAction(
            new ShowUsagesAction(),
            jelixEditor.getEditor().getContentComponent(),
            "", null, null
        );
        return Result.EXECUTE;
    }

    public static Result gotoImplementation(JelixEditor jelixEditor) {
        ActionUtil.invokeAction(
            new GotoImplementationAction(),
            jelixEditor.getEditor().getContentComponent(),
            "", null, null
        );
        return Result.EXECUTE;
    }

    public static Result gotoWindowTop(JelixEditor jelixEditor) {
        var editor = jelixEditor.getEditor();
        SelectionModel selectionModel = editor.getSelectionModel();
        CaretModel caretModel = editor.getCaretModel();

        int selectionStart = selectionModel.getLeadSelectionOffset();
        LogicalPosition blockSelectionStart = caretModel.getLogicalPosition();
        Rectangle visibleArea = getVisibleArea(editor);
        int lineNumber = editor.yToVisualLine(visibleArea.y);
        if (visibleArea.y > editor.visualLineToY(lineNumber) && visibleArea.y + visibleArea.height > editor.visualLineToY(lineNumber + 1)) {
            lineNumber++;
        }
        VisualPosition pos = new VisualPosition(lineNumber, editor.getCaretModel().getVisualPosition().column);
        editor.getCaretModel().moveToVisualPosition(pos);
        setupSelection(editor, false, selectionStart, blockSelectionStart);

        return Result.EXECUTE;
    }

    public static Result gotoWindowMiddle(JelixEditor jelixEditor) {
        var editor = jelixEditor.getEditor();
        SelectionModel selectionModel = editor.getSelectionModel();
        CaretModel caretModel = editor.getCaretModel();

        int selectionStart = selectionModel.getLeadSelectionOffset();
        LogicalPosition blockSelectionStart = caretModel.getLogicalPosition();
        Rectangle visibleArea = getVisibleArea(editor);
        int lineNumber = editor.yToVisualLine(visibleArea.y);
        if (visibleArea.y > editor.visualLineToY(lineNumber) && visibleArea.y + visibleArea.height > editor.visualLineToY(lineNumber + 1)) {
            lineNumber++;
        }
        VisualPosition pos = new VisualPosition(lineNumber + visibleArea.height / editor.getLineHeight() / 2, editor.getCaretModel().getVisualPosition().column);
        editor.getCaretModel().moveToVisualPosition(pos);
        setupSelection(editor, false, selectionStart, blockSelectionStart);

        return Result.EXECUTE;
    }

    public static Result gotoWindowBottom(JelixEditor jelixEditor) {
        var editor = jelixEditor.getEditor();
        SelectionModel selectionModel = editor.getSelectionModel();
        CaretModel caretModel = editor.getCaretModel();

        int selectionStart = selectionModel.getLeadSelectionOffset();
        LogicalPosition blockSelectionStart = caretModel.getLogicalPosition();
        Rectangle visibleArea = getVisibleArea(editor);
        int maxY = visibleArea.y + visibleArea.height - editor.getLineHeight();
        int lineNumber = editor.yToVisualLine(maxY);
        if (lineNumber > 0 && maxY < editor.visualLineToY(lineNumber) && visibleArea.y <= editor.visualLineToY(lineNumber - 1)) {
            lineNumber--;
        }
        VisualPosition pos = new VisualPosition(lineNumber, editor.getCaretModel().getVisualPosition().column);
        editor.getCaretModel().moveToVisualPosition(pos);
        setupSelection(editor, false, selectionStart, blockSelectionStart);

        return Result.EXECUTE;
    }

    public static Result gotoNextTab(JelixEditor jelixEditor) {
        ActionUtil.invokeAction(
            new NextTabAction(),
            jelixEditor.getEditor().getContentComponent(),
            "", null, null);
        return Result.EXECUTE;
    }

    public static Result gotoPrevTab(JelixEditor jelixEditor) {
        ActionUtil.invokeAction(
            new PreviousTabAction(),
            jelixEditor.getEditor().getContentComponent(),
            "", null, null);
        return Result.EXECUTE;
    }

    private static void setupSelection(@NotNull Editor editor,
                                       boolean isWithSelection,
                                       int selectionStart,
                                       @NotNull LogicalPosition blockSelectionStart) {
        SelectionModel selectionModel = editor.getSelectionModel();
        CaretModel caretModel = editor.getCaretModel();
        if (isWithSelection) {
            if (editor.isColumnMode() && !caretModel.supportsMultipleCarets()) {
                selectionModel.setBlockSelection(blockSelectionStart, caretModel.getLogicalPosition());
            } else {
                selectionModel.setSelection(selectionStart, caretModel.getVisualPosition(), caretModel.getOffset());
            }
        } else {
            selectionModel.removeSelection();
        }

        selectNonexpandableFold(editor);
    }

    private static Rectangle getVisibleArea(@NotNull Editor editor) {
        ScrollingModel model = editor.getScrollingModel();
        return EditorCoreUtil.isTrueSmoothScrollingEnabled() ? model.getVisibleAreaOnScrollingFinished() : model.getVisibleArea();
    }
}
