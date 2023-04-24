package fr.caemur.jelix;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.actionSystem.KeyboardShortcut;
import com.intellij.openapi.actionSystem.Shortcut;
import com.intellij.openapi.actionSystem.ShortcutSet;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.ClientEditorManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.actionSystem.TypedAction;
import com.intellij.openapi.editor.actionSystem.TypedActionHandler;
import fr.caemur.jelix.typing.JxTypedHandler;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Jelix implements Disposable {
    private final List<JelixEditor> editors = new ArrayList<>();
    private TypedActionHandler originalHandler;

    // TODO REMOVE
    public static final ShortcutSet SHORTCUT_SET = () -> new Shortcut[]{
        new KeyboardShortcut(KeyStroke.getKeyStroke("ESCAPE"), null),
        new KeyboardShortcut(KeyStroke.getKeyStroke("shift alt pressed C"), null),
        new KeyboardShortcut(KeyStroke.getKeyStroke("alt pressed COMMA"), null),
        new KeyboardShortcut(KeyStroke.getKeyStroke("control pressed W"), null),
        new KeyboardShortcut(KeyStroke.getKeyStroke("control pressed U"), null),
        new KeyboardShortcut(KeyStroke.getKeyStroke("control pressed K"), null),
        new KeyboardShortcut(KeyStroke.getKeyStroke("alt pressed SEMICOLON"), null),
        new KeyboardShortcut(KeyStroke.getKeyStroke("shift alt pressed SEMICOLON"), null),
    };

    public void init() {
        final var typedAction = TypedAction.getInstance();
        this.originalHandler = typedAction.getRawHandler();
        typedAction.setupRawHandler(new JxTypedHandler());

        ClientEditorManager.getCurrentInstance().editors().map(JelixEditor::new).forEach(editors::add);
        EditorFactory.getInstance().addEditorFactoryListener(
            new JxEditorListener(), this);
    }

    public void handleKey(KeyStroke keyStroke, Editor editor) {
        final var ed = this.getEditor(editor);
        if (ed != null) {
            ed.handleKey(keyStroke);
        }
    }

    public @Nullable JelixEditor getEditor(Editor editor) {
        return this.editors.stream()
            .filter(jxEditor -> jxEditor.getEditor() == editor)
            .findFirst()
            .orElse(null);
    }

    public void registerEditor(JelixEditor editor) {
        this.editors.add(editor);
    }

    public void unregisterEditor(JelixEditor editor) {
        this.editors.remove(editor);
    }

    public TypedActionHandler getOriginalHandler() {
        return originalHandler;
    }

    public static Jelix getInstance() {
        return ApplicationManager.getApplication().getService(Jelix.class);
    }

    @Override
    public void dispose() {
    }
}
