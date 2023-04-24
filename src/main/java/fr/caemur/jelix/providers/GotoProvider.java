package fr.caemur.jelix.providers;

import fr.caemur.jelix.Command;
import fr.caemur.jelix.JelixEditor;

import java.util.List;

public class GotoProvider extends PopupProvider {
    public GotoProvider(JelixEditor jelixEditor) {
        super(jelixEditor, "Goto",
            List.of(
                new ListCmd('g', Command.GOTO_LINE),
                new ListCmd('e', Command.GOTO_END),
                new ListCmd('h', Command.GOTO_LINE_START),
                new ListCmd('l', Command.GOTO_LINE_END),
                new ListCmd('s', Command.GOTO_FIRST_CHAR),
                new ListCmd('d', Command.GOTO_DEFINITION),
                new ListCmd('y', Command.GOTO_TYPE_DEFINITION),
                new ListCmd('r', Command.GOTO_REFERENCES),
                new ListCmd('i', Command.GOTO_IMPLEMENTATION),
                new ListCmd('t', Command.GOTO_WINDOW_TOP),
                new ListCmd('c', Command.GOTO_WINDOW_MIDDLE),
                new ListCmd('b', Command.GOTO_WINDOW_BOTTOM),
                new ListCmd('n', Command.GOTO_NEXT_TAB),
                new ListCmd('p', Command.GOTO_PREV_TAB)
            ));
    }
}
