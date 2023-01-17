package fr.caemur.jelix;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.wm.WindowManager;
import fr.caemur.jelix.providers.CommandProvider;
import fr.caemur.jelix.providers.NormalProvider;
import fr.caemur.jelix.typing.JelixShortcutAction;
import fr.caemur.jelix.util.Mode;
import fr.caemur.jelix.util.Result;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JelixEditor {
    private final Editor editor;

    private @NotNull Mode mode = Mode.NORMAL;
    private @NotNull CommandProvider commandProvider = new NormalProvider();

    private int count = 0;
    private boolean isCounting = false;
    private final List<KeyStroke> keyStrokes = new ArrayList<>();

    public JelixEditor(@NotNull Editor editor) {
        this.editor = editor;
        this.setActionProvider(new NormalProvider());

        JelixShortcutAction.getInstance().registerCustomShortcutSet(Jelix.SHORTCUT_SET, editor.getComponent());
    }

    public void setActionProvider(@Nullable CommandProvider commandProvider) {
        this.commandProvider = Objects.requireNonNullElseGet(commandProvider, NormalProvider::new);
        this.isCounting = this.commandProvider.isCounting();
    }

    public void handleKey(@NotNull KeyStroke keyStroke) {
        this.keyStrokes.add(keyStroke);

        Result result;
        if (this.isCounting && Character.isDigit(keyStroke.getKeyChar())) {
            this.count = this.count * 10 + Character.getNumericValue(keyStroke.getKeyChar());
            result = Result.CONTINUE;
        } else {
            this.isCounting = false;
            result = this.commandProvider.handleKey(this, keyStroke);
        }

        // Update Widget
        final var proj = editor.getProject();
        if (proj != null) {
            WindowManager.getInstance().getStatusBar(proj).updateWidget("Jelix");
        }

        if (result != Result.CONTINUE) {
            this.count = 0;
            this.keyStrokes.clear();
            this.setActionProvider(this.mode.getDefaultActionProvider());
        }
    }

    public Editor getEditor() {
        return editor;
    }

    public @NotNull Mode getMode() {
        return mode;
    }

    public void setMode(@NotNull Mode mode) {
        this.mode = mode;
    }

    public List<KeyStroke> getKeyStrokes() {
        return keyStrokes;
    }

    public int getCount() {
        return count == 0 ? 1 : count;
    }
}
