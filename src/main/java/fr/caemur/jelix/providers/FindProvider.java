package fr.caemur.jelix.providers;

import fr.caemur.jelix.util.DocIter;
import fr.caemur.jelix.JelixEditor;
import fr.caemur.jelix.util.Result;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class FindProvider extends CommandProvider {
    public enum FindMode {
        NEXT,
        PREVIOUS,
        TILL_NEXT,
        TILL_PREVIOUS
    }

    private final FindMode mode;

    public FindProvider(FindMode mode) {
        this.mode = mode;
    }

    @Override
    public Result handleKey(JelixEditor editor, KeyStroke keyStroke) {
        final var keyChar = keyStroke.getKeyChar();
        if (keyChar == 0 || keyChar == KeyEvent.CHAR_UNDEFINED) {
            return Result.FAILED;
        }

        var ed = editor.getEditor();
        var doc = ed.getDocument();
        var text = doc.getImmutableCharSequence();
        ed.getCaretModel().runForEachCaret(c -> {
            var startOff = c.getOffset();
            var iter = new DocIter(text, startOff);
            if (this.mode == FindMode.PREVIOUS || this.mode == FindMode.TILL_PREVIOUS) {
                iter.reverse();
            }
            if (this.mode == FindMode.TILL_NEXT || this.mode == FindMode.TILL_PREVIOUS) {
                iter.next();
            }

            while (iter.hasNext()) {
                var ch = iter.next();
                assert ch.isPresent();
                if (ch.get() == keyChar) {
                    if (this.mode == FindMode.TILL_NEXT || this.mode == FindMode.TILL_PREVIOUS) {
                        iter.prev();
                    }
                    var endOff = iter.getOffset();
                    c.moveToOffset(endOff);
                    c.setSelection(startOff, endOff);
                    break;
                }
            }
        });

        editor.setLastMotion(editor_ -> this.handleKey(editor_, keyStroke));

        return Result.EXECUTE;
    }
}
