package fr.caemur.jelix.commands;

import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.ScrollType;
import fr.caemur.jelix.JelixEditor;
import fr.caemur.jelix.util.Result;

public class Selection {
    public static Result selectLine(JelixEditor jelixEditor) {
        // first try to grab all lines covered by selection
        // if already selected, add next line to selection

        var ed = jelixEditor.getEditor();
        var doc = ed.getDocument();
        ed.getCaretModel().runForEachCaret(c -> {
            for (int i = 0; i < jelixEditor.getCount(); i++) {
                var start = c.getSelectionStart();
                var end = c.getSelectionEnd();
                var lineStart = doc.getLineStartOffset(doc.getLineNumber(start));
                var lineEnd = doc.getLineEndOffset(doc.getLineNumber(end));
                c.moveToOffset(lineEnd);
                c.setSelection(lineStart, Math.min(doc.getTextLength(), lineEnd + 1));
            }
        });

        return Result.EXECUTE;
    }

    public static Result selectFile(JelixEditor jelixEditor) {
        var length = jelixEditor.getEditor().getDocument().getTextLength();
        jelixEditor.getEditor().getSelectionModel().setSelection(0, length);
        jelixEditor.getEditor().getCaretModel().moveToOffset(length);
        jelixEditor.getEditor().getScrollingModel().scrollToCaret(ScrollType.MAKE_VISIBLE);
        return Result.EXECUTE;
    }

    public static Result flipSelection(JelixEditor jelixEditor) {
        var ed = jelixEditor.getEditor();

        ed.getCaretModel().runForEachCaret(caret -> {
            var offset = caret.getOffset();
            var startOffset = caret.getLeadSelectionOffset();
            caret.moveToOffset(startOffset);
            caret.setSelection(startOffset, offset);
        });
        ed.getScrollingModel().scrollToCaret(ScrollType.MAKE_VISIBLE);

        return Result.EXECUTE;
    }

    public static Result collapseSelection(JelixEditor jelixEditor) {
        jelixEditor.getEditor().getCaretModel().runForEachCaret(Caret::removeSelection);
        return Result.EXECUTE;
    }
}
