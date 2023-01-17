package fr.caemur.jelix.typing;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.ui.KeyStrokeAdapter;
import fr.caemur.jelix.Jelix;
import kotlin.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class JelixShortcutAction extends DumbAwareAction {
    private static AnAction instance;

    private @NotNull Pair<
        @Nullable KeyEvent,
        @Nullable KeyStroke> cache = new Pair<>(null, null);

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        if (!(e.getInputEvent() instanceof KeyEvent)) {
            return;
        }

        final var keyEvent = (KeyEvent) e.getInputEvent();
        final var keyStroke = this.getKeyStroke(keyEvent);
        final var editor = e.getData(PlatformDataKeys.EDITOR);

        if (keyStroke == null || editor == null) {
            return;
        }

        Jelix.getInstance().handleKey(keyStroke, editor);
    }

    private @Nullable KeyStroke getKeyStroke(KeyEvent event) {
        var defaultKeyStroke = KeyStrokeAdapter.getDefaultKeyStroke(event);
        var strokeCache = this.cache;
        if (defaultKeyStroke != null) {
            this.cache = new Pair<>(event, defaultKeyStroke);
            return defaultKeyStroke;
        } else if (strokeCache.getFirst() == event) {
            this.cache = new Pair<>(null, null);
            return strokeCache.getSecond();
        }
        return KeyStroke.getKeyStrokeForEvent(event);
    }

    public static AnAction getInstance() {
        if (instance == null) {
            instance = EmptyAction.wrap(ActionManager.getInstance().getAction("JelixShortcutAction"));
        }
        return instance;
    }
}
