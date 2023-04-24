package fr.caemur.jelix.providers;

import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import fr.caemur.jelix.Command;
import fr.caemur.jelix.JelixEditor;
import fr.caemur.jelix.Key;
import fr.caemur.jelix.util.Result;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.List;

public abstract class PopupProvider extends CommandProvider {
    protected static class ListCmd {
        private final char key;
        private final Command cmd;

        public ListCmd(char key, Command cmd) {
            this.key = key;
            this.cmd = cmd;
        }

        @Override
        public String toString() {
            return String.format("%c : %s", this.key, this.cmd);
        }
    }

    protected final JBPopup popup;

    public PopupProvider(JelixEditor jelixEditor, @NotNull String title, @NotNull List<ListCmd> commands) {
        super.bind(Command.ESCAPE, Key.INS_ESCAPE);
        commands.forEach(cmd -> super.bind(cmd.cmd, KeyStroke.getKeyStroke(cmd.key)));

        this.popup = JBPopupFactory.getInstance().createPopupChooserBuilder(commands)
            .setTitle(title)
            .setItemChosenCallback(cmd -> jelixEditor.handleResult(cmd.cmd.execute(jelixEditor)))
            .setRequestFocus(false)
            .createPopup();

        this.popup.showInBestPositionFor(jelixEditor.getEditor());
    }

    @Override
    public Result handleKey(JelixEditor editor, KeyStroke keyStroke) {
        this.popup.cancel();
        return super.handleKey(editor, keyStroke);
    }
}
