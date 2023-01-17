package fr.caemur.jelix;

import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public enum Key {
    NOR_ESCAPE("ESCAPE"),
    NOR_UP("typed k"),
    NOR_DOWN("typed j"),
    NOR_LEFT("typed h"),
    NOR_RIGHT("typed l"),
    NOR_NEXT_WORD_START("typed w"),
    NOR_NEXT_LONG_WORD_START("typed W"),
    NOR_NEXT_WORD_END("typed e"),
    NOR_NEXT_LONG_WORD_END("typed E"),
    NOR_PREV_WORD_START("typed b"),
    NOR_PREV_LONG_WORD_START("typed B"),
    NOR_PREV_WORD_END(null),
    NOR_NEXT_CHAR("typed f"),
    NOR_PREV_CHAR("typed F"),
    NOR_TILL_NEXT_CHAR("typed t"),
    NOR_TILL_PREV_CHAR("typed T"),

    NOR_INSERT("typed i"),
    NOR_INSERT_START("typed I"),

    NOR_CLONE_CURSOR_BELOW("typed C"),
    NOR_CLONE_CURSOR_ABOVE("shift alt pressed C"),
    NOR_NEXT_CURSOR("typed )"),
    NOR_PREV_CURSOR("typed ("),
    NOR_REM_CURSOR("alt pressed COMMA"),
    NOR_REM_OTHER_CURSORS("typed ,"),

    INS_ESCAPE("ESCAPE"),
    INS_DEL_WORD("control pressed W"),
    INS_DEL_TO_START("control pressed U"),
    INS_DEL_TO_END("control pressed K");

    private final KeyStroke keyStroke;

    Key(@Nullable String defaultKeyStroke) {
        this.keyStroke = KeyStroke.getKeyStroke(defaultKeyStroke);
    }

    public @Nullable KeyStroke getKey() {
        return this.keyStroke;
    }
}
