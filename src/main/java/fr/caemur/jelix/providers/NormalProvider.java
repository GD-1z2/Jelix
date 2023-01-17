package fr.caemur.jelix.providers;

import fr.caemur.jelix.Command;
import fr.caemur.jelix.Key;

public class NormalProvider extends CommandProvider {
    public NormalProvider() {
        super.bind(Command.ESCAPE, Key.NOR_ESCAPE);

        super.bind(Command.MOVE_LEFT, Key.NOR_LEFT);
        super.bind(Command.MOVE_RIGHT, Key.NOR_RIGHT);
        super.bind(Command.MOVE_UP, Key.NOR_UP);
        super.bind(Command.MOVE_DOWN, Key.NOR_DOWN);
        super.bind(Command.MOVE_NEXT_WORD_START, Key.NOR_NEXT_WORD_START);
        super.bind(Command.MOVE_NEXT_LONG_WORD_START, Key.NOR_NEXT_LONG_WORD_START);
        super.bind(Command.MOVE_NEXT_WORD_END, Key.NOR_NEXT_WORD_END);
        super.bind(Command.MOVE_NEXT_LONG_WORD_END, Key.NOR_NEXT_LONG_WORD_END);
        super.bind(Command.MOVE_PREV_WORD_START, Key.NOR_PREV_WORD_START);
        super.bind(Command.MOVE_PREV_LONG_WORD_START, Key.NOR_PREV_LONG_WORD_START);
        super.bind(Command.MOVE_PREV_WORD_END, Key.NOR_PREV_WORD_END);
        super.bind(Command.FIND_NEXT_CHAR, Key.NOR_NEXT_CHAR);
        super.bind(Command.FIND_PREV_CHAR, Key.NOR_PREV_CHAR);
        super.bind(Command.FIND_TILL_NEXT_CHAR, Key.NOR_TILL_NEXT_CHAR);
        super.bind(Command.FIND_TILL_PREV_CHAR, Key.NOR_TILL_PREV_CHAR);

        super.bind(Command.INSERT, Key.NOR_INSERT);
        super.bind(Command.INSERT_START, Key.NOR_INSERT_START);

        super.bind(Command.CLONE_CURSOR_ABOVE, Key.NOR_CLONE_CURSOR_ABOVE);
        super.bind(Command.CLONE_CURSOR_BELOW, Key.NOR_CLONE_CURSOR_BELOW);
        super.bind(Command.PREV_CURSOR, Key.NOR_PREV_CURSOR);
        super.bind(Command.NEXT_CURSOR, Key.NOR_NEXT_CURSOR);
        super.bind(Command.REM_CURSOR, Key.NOR_REM_CURSOR);
        super.bind(Command.REM_OTHER_CURSORS, Key.NOR_REM_OTHER_CURSORS);
    }

    @Override
    public boolean isCounting() {
        return true;
    }
}
