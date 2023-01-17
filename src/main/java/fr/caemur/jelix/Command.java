package fr.caemur.jelix;

import fr.caemur.jelix.commands.*;
import fr.caemur.jelix.util.Result;

import java.util.function.Function;

public enum Command {
    ESCAPE("Escape", Escape::escape),
    MOVE_LEFT("Move left", SimpleMotions::moveLeft),
    MOVE_RIGHT("Move right", SimpleMotions::moveRight),
    MOVE_UP("Move up", SimpleMotions::moveUp),
    MOVE_DOWN("Move down", SimpleMotions::moveDown),
    MOVE_NEXT_WORD_START("Move next word start", WordMotions::moveNextWordStart),
    MOVE_NEXT_LONG_WORD_START("Move next long word start", WordMotions::moveNextLongWordStart),
    MOVE_NEXT_WORD_END("Move next word end", WordMotions::moveNextWordEnd),
    MOVE_NEXT_LONG_WORD_END("Move next long word end", WordMotions::moveNextLongWordEnd),
    MOVE_PREV_WORD_START("Move prev word start", WordMotions::movePrevWordStart),
    MOVE_PREV_LONG_WORD_START("Move prev long word start", WordMotions::movePrevLongWordStart),
    MOVE_PREV_WORD_END("Move prev word end", WordMotions::movePrevWordEnd),
    FIND_NEXT_CHAR("Move to next char", FindChar::findNextChar),
    FIND_PREV_CHAR("Move to previous char", FindChar::findPreviousChar),
    FIND_TILL_NEXT_CHAR("Move till next char", FindChar::tillNextChar),
    FIND_TILL_PREV_CHAR("Move till previous char", FindChar::tillPreviousChar),
    INSERT("Insert", Insert::insert),
    INSERT_START("Insert at start", Insert::insertStart),
    DELETE_WORD("Delete word", Deletion::deleteWord),
    DELETE_TO_START("Delete to start", Deletion::deleteToStart),
    DELETE_TO_END("Delete to end", Deletion::deleteToEnd),
    CLONE_CURSOR_ABOVE("Clone cursor above", MultiCursor::cloneAbove),
    CLONE_CURSOR_BELOW("Clone cursor below", MultiCursor::cloneBelow),
    PREV_CURSOR("Previous cursor", MultiCursor::prevCursor),
    NEXT_CURSOR("Next cursor", MultiCursor::nextCursor),
    REM_CURSOR("Remove cursor", MultiCursor::removeCursor),
    REM_OTHER_CURSORS("Remove other cursors", MultiCursor::removeOtherCursors);

    public interface Impl extends Function<JelixEditor, Result> {
    }

    private final String name;
    private final Impl impl;

    Command(String name, Impl impl) {
        this.name = name;
        this.impl = impl;
    }

    public Result execute(JelixEditor editor) {
        return impl.apply(editor);
    }
}
