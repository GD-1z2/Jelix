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
    REPEAT_LAST_CHANGE("Repeat last change", Repeat::repeatLastChange),
    REPEAT_LAST_MOTION("Repeat last search", Repeat::repeatLastMotion),
    INSERT("Insert", Insert::insert),
    INSERT_START("Insert at start", Insert::insertStart),
    APPEND("Append", Insert::append),
    APPEND_END("Append at end", Insert::appendEnd),
    INSERT_NEWLINE("Insert newline", Insert::insertNewLine),
    INSERT_NEWLINE_ABOVE("Insert newline above", Insert::insertNewLineAbove),
    CHANGE("Change", Insert::change),
    DELETE("Delete", Deletion::delete),
    DELETE_WORD("Delete word", Deletion::deleteWord),
    DELETE_TO_START("Delete to start", Deletion::deleteToStart),
    DELETE_TO_END("Delete to end", Deletion::deleteToEnd),
    UNDO("Undo", Undo::undo),
    REDO("Redo", Undo::redo),
    CLONE_CURSOR_ABOVE("Clone cursor above", MultiCursor::cloneAbove),
    CLONE_CURSOR_BELOW("Clone cursor below", MultiCursor::cloneBelow),
    PREV_CURSOR("Previous cursor", MultiCursor::prevCursor),
    NEXT_CURSOR("Next cursor", MultiCursor::nextCursor),
    REM_CURSOR("Remove cursor", MultiCursor::removeCursor),
    REM_OTHER_CURSORS("Remove other cursors", MultiCursor::removeOtherCursors),
    SELECT_LINE("Select line", Selection::selectLine),
    SELECT_FILE("Select file", Selection::selectFile),
    FLIP_SELECTION("Flip selection", Selection::flipSelection),
    COLLAPSE_SELECTION("Collapse selection", Selection::collapseSelection),
    GLOBAL_MENU("Global menu", GlobalMenu::globalMenu),
    SHOW_FILE_PICKER("Show file picker", GlobalMenu::showFilePicker),
    SHOW_SYMBOL_PICKER("Show symbol picker", GlobalMenu::showSymbolPicker),
    SHOW_DOCS("Show docs", GlobalMenu::showDocs),
    RENAME_SYMBOL("Rename symbol", GlobalMenu::renameSymbol),
    SHOW_COMMAND_PICKER("Show action picker", GlobalMenu::showCommandPicker),
    GOTO_MENU("Goto menu", Goto::gotoMenu),
    GOTO_LINE("Line N or first", Goto::gotoLine),
    GOTO_END("End of file", Goto::gotoEnd),
    GOTO_LINE_START("Line start", Goto::gotoLineStart),
    GOTO_LINE_END("Line end", Goto::gotoLineEnd),
    GOTO_FIRST_CHAR("First char in line", Goto::gotoFirstChar),
    GOTO_DEFINITION("Definition", Goto::gotoDefinition),
    GOTO_TYPE_DEFINITION("Type definition", Goto::gotoTypeDefinition),
    GOTO_REFERENCES("References", Goto::gotoReferences),
    GOTO_IMPLEMENTATION("Implementation", Goto::gotoImplementation),
    GOTO_WINDOW_TOP("Window top", Goto::gotoWindowTop),
    GOTO_WINDOW_MIDDLE("Window middle", Goto::gotoWindowMiddle),
    GOTO_WINDOW_BOTTOM("Window bottom", Goto::gotoWindowBottom),
    GOTO_NEXT_TAB("Next tab", Goto::gotoNextTab),
    GOTO_PREV_TAB("Previous tab", Goto::gotoPrevTab),
    ;

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

    @Override
    public String toString() {
        return name;
    }
}
