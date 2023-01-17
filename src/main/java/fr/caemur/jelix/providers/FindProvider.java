package fr.caemur.jelix.providers;

import com.intellij.openapi.editor.Document;
import fr.caemur.jelix.util.DocIter;
import fr.caemur.jelix.JelixEditor;
import fr.caemur.jelix.util.Range;
import fr.caemur.jelix.util.Result;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.OptionalInt;

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

        if (this.mode == FindMode.NEXT) {
            findCharImpl(editor, FindProvider::findNextCharImpl, true, false, keyChar,
                editor.getCount());
        } else if (this.mode == FindMode.PREVIOUS) {
            findCharImpl(editor, FindProvider::findPreviousCharImpl, true, false, keyChar,
                editor.getCount());
        } else if (this.mode == FindMode.TILL_NEXT) {
            findCharImpl(editor, FindProvider::findNextCharImpl, false, true, keyChar,
                editor.getCount());
        } else if (this.mode == FindMode.TILL_PREVIOUS) {
            findCharImpl(editor, FindProvider::findPreviousCharImpl, false, true, keyChar,
                editor.getCount());
        }

        // TODO: set last motion for . command

        return Result.EXECUTE;
    }

    private interface FindImpl {
        OptionalInt find(Document doc, char ch, int pos, int n, boolean inclusive);
    }

    private static void findCharImpl(JelixEditor editor, FindImpl searchFn, boolean inclusive, boolean extend,
                                     char ch, int count) {
        Range.transform(editor.getEditor().getCaretModel(), range -> {
            int searchStartPos = range.head;
            if (range.anchor < range.head) {
                searchStartPos--;
            }

            var res = searchFn.find(editor.getEditor().getDocument(), ch, searchStartPos, count, inclusive);
            if (res.isEmpty()) {
                return range;
            }
            if (extend) {
                return range.putCursor(res.getAsInt(), true);
            }
            return new Range(range.head).putCursor(res.getAsInt(), true);
        });
    }

    private static OptionalInt findNextCharImpl(Document doc, char ch, int pos, int n, boolean inclusive) {
        pos = Math.min(pos + 1, doc.getTextLength());
        if (inclusive) {
            return findNthNext(doc.getCharsSequence(), ch, pos, n);
        } else {
            if (pos >= 0 && pos < doc.getTextLength() && doc.getCharsSequence().charAt(pos) == ch) {
                n = n + 1;
            }
            var res = findNthNext(doc.getCharsSequence(), ch, pos, n);
            if (res.isEmpty()) {
                return res;
            }
            return OptionalInt.of(Math.max(0, res.getAsInt() - 1));
        }
    }

    private static OptionalInt findNthNext(CharSequence text, char ch, int pos, int n) {
        if (pos >= text.length() || n == 0) {
            return OptionalInt.empty();
        }

        var iter = new DocIter(text, pos);
        for (int i = 0; i < n; i++) {
            while (true) {
                var c = iter.next();
                if (c.isEmpty()) {
                    return OptionalInt.empty();
                }

                pos++;

                if (ch == c.get()) {
                    break;
                }
            }
        }

        return OptionalInt.of(pos);
    }

    private static OptionalInt findPreviousCharImpl(Document doc, char ch, int pos, int n, boolean inclusive) {
        pos = Math.max(pos - 1, 0);
        if (inclusive) {
            return findNthPrevious(doc.getCharsSequence(), ch, pos, n);
        } else {
            if (pos >= 1 && pos < doc.getTextLength() + 1 && doc.getCharsSequence().charAt(pos - 1) == ch) {
                n = n + 1;
            }
            var res = findNthPrevious(doc.getCharsSequence(), ch, pos, n);
            if (res.isEmpty()) {
                return res;
            }
            return OptionalInt.of(Math.min(doc.getTextLength(), res.getAsInt() + 1));
        }
    }

    private static OptionalInt findNthPrevious(CharSequence text, char ch, int pos, int n) {
        if (pos == 0 || n == 0) {
            return OptionalInt.empty();
        }

        var iter = new DocIter(text, pos);
        for (int i = 0; i < n; i++) {
            while (true) {
                var c = iter.prev();
                if (c.isEmpty()) {
                    return OptionalInt.empty();
                }

                pos--;

                if (ch == c.get()) {
                    break;
                }
            }
        }

        return OptionalInt.of(pos);
    }
}
