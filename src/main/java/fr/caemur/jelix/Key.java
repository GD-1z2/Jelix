package fr.caemur.jelix;

import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public enum Key {
    NOR_ESCAPE("ESCAPE"),
    NOR_UP('k'),
    NOR_DOWN('j'),
    NOR_LEFT('h'),
    NOR_RIGHT('l'),
    NOR_NEXT_WORD_START('w'),
    NOR_NEXT_LONG_WORD_START('W'),
    NOR_NEXT_WORD_END('e'),
    NOR_NEXT_LONG_WORD_END('E'),
    NOR_PREV_WORD_START('b'),
    NOR_PREV_LONG_WORD_START('B'),
    NOR_PREV_WORD_END(null),
    NOR_NEXT_CHAR('f'),
    NOR_PREV_CHAR('F'),
    NOR_TILL_NEXT_CHAR('t'),
    NOR_TILL_PREV_CHAR('T'),

    NOR_REPEAT_LAST_CHANGE('.'),
    NOR_REPEAT_LAST_SEARCH("shift alt pressed SEMICOLON"),

    NOR_INSERT('i'),
    NOR_INSERT_START('I'),
    NOR_APPEND('a'),
    NOR_APPEND_END('A'),
    NOR_INSERT_LINE('o'),
    NOR_INSERT_LINE_ABOVE('O'),
    NOR_CHANGE('c'),
    NOR_DELETE('d'),

    NOR_UNDO('u'),
    NOR_REDO('U'),

    NOR_CLONE_CURSOR_BELOW('C'),
    NOR_CLONE_CURSOR_ABOVE("shift alt pressed C"),
    NOR_NEXT_CURSOR(')'),
    NOR_PREV_CURSOR('('),
    NOR_REM_CURSOR("alt pressed COMMA"),
    NOR_REM_OTHER_CURSORS(','),
    NOR_SELECT_LINE('x'),
    NOR_SELECT_FILE('%'),
    NOR_FLIP_SELECTION("alt pressed SEMICOLON"),
    NOR_COLLAPSE_SELECTION(';'),

    NOR_GLOBAL_MENU(' '),
    NOR_GOTO_MENU('g'),
    NOR_GOTO_LINE('G'),

    INS_ESCAPE("ESCAPE"),
    INS_DEL_WORD("control pressed W"),
    INS_DEL_TO_START("control pressed U"),
    INS_DEL_TO_END("control pressed K"),

    GLOBAL_ESCAPE("ESCAPE"),
    ;

    private final KeyStroke keyStroke;

    Key(@Nullable String key) {
        this.keyStroke = KeyStroke.getKeyStroke(key);
    }

    Key(char key) {
        this.keyStroke = KeyStroke.getKeyStroke(key);
    }

    public @Nullable KeyStroke getKey() {
        return this.keyStroke;
    }
}
