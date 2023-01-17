package fr.caemur.jelix.commands;

import com.intellij.openapi.editor.LogicalPosition;
import fr.caemur.jelix.JelixEditor;
import fr.caemur.jelix.util.Mode;
import fr.caemur.jelix.util.Result;
import fr.caemur.jelix.providers.InsertProvider;

public class Insert {
    public static Result insert(JelixEditor editor) {
        editor.setMode(Mode.INSERT);
        editor.setActionProvider(new InsertProvider());
        return Result.EXECUTE;
    }

    public static Result insertStart(JelixEditor editor) {
        editor.getEditor().getCaretModel().runForEachCaret(c -> {
            var doc = editor.getEditor().getDocument();
            var line = c.getLogicalPosition().line;
            var text = doc.getText().substring(
                doc.getLineStartOffset(line),
                doc.getLineEndOffset(line)
            );
            var firstNonBlank = text.indexOf(text.trim());
            c.moveToLogicalPosition(new LogicalPosition(line, firstNonBlank));
        });

        return Insert.insert(editor);
    }
}
