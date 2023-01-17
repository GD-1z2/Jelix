package fr.caemur.jelix.providers;

import fr.caemur.jelix.Command;
import fr.caemur.jelix.JelixEditor;
import fr.caemur.jelix.Key;
import fr.caemur.jelix.util.Result;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public abstract class CommandProvider {
    private final Map<KeyStroke, Command> actions = new HashMap<>();

    protected void bind(Command cmd, Key key) {
        if (key.getKey() != null) {
            actions.put(key.getKey(), cmd);
        }
    }

    public Result handleKey(JelixEditor editor, KeyStroke keyStroke) {
        var action = this.actions.get(keyStroke);
        if (action == null) {
            return Result.FAILED;
        }

        return action.execute(editor);
    }

    public boolean isCounting() {
        return false;
    }
}
