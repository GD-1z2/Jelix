package fr.caemur.jelix;

import com.intellij.openapi.editor.event.EditorFactoryEvent;
import com.intellij.openapi.editor.event.EditorFactoryListener;
import org.jetbrains.annotations.NotNull;

public class JxEditorListener implements EditorFactoryListener {
    private final Jelix jelix;

    public JxEditorListener() {
        this.jelix = Jelix.getInstance();
    }

    @Override
    public void editorCreated(@NotNull EditorFactoryEvent event) {
        jelix.registerEditor(new JelixEditor(event.getEditor()));
    }

    @Override
    public void editorReleased(@NotNull EditorFactoryEvent event) {
        jelix.unregisterEditor(jelix.getEditor(event.getEditor()));
    }
}
