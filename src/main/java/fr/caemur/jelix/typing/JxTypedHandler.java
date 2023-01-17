package fr.caemur.jelix.typing;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.TypedActionHandler;
import fr.caemur.jelix.Jelix;
import fr.caemur.jelix.util.Mode;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class JxTypedHandler implements TypedActionHandler {
    private final Jelix jelix;

    public JxTypedHandler() {
        this.jelix = Jelix.getInstance();
    }

    @Override
    public void execute(@NotNull Editor editor, char c, @NotNull DataContext context) {
        final var ed = this.jelix.getEditor(editor);
        if (ed == null) {
            return;
        }

        if (ed.getMode() == Mode.INSERT) {
            this.jelix.getOriginalHandler().execute(editor, c, context);
            return;
        }

        ed.handleKey(KeyStroke.getKeyStroke(c));
    }
}
