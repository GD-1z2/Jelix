package fr.caemur.jelix.commands;

import fr.caemur.jelix.JelixEditor;
import fr.caemur.jelix.util.CharCategory;
import fr.caemur.jelix.util.DocIter;
import fr.caemur.jelix.util.Range;
import fr.caemur.jelix.util.Result;

import java.util.function.Consumer;

import static fr.caemur.jelix.util.CharCategory.isLineEnding;
import static fr.caemur.jelix.util.CharCategory.isWordBoundary;
import static java.lang.Character.isWhitespace;

public class WordMotions {
    private enum Target {
        NEXT_WORD_START, NEXT_LONG_WORD_START,
        NEXT_WORD_END, NEXT_LONG_WORD_END,
        PREV_WORD_START, PREV_LONG_WORD_START,
        PREV_WORD_END;

        public boolean isPrev() {
            return this.name().startsWith("PREV");
        }
    }

    public static Result moveNextWordStart(JelixEditor jelixEditor) {
        return wordMove(jelixEditor, Target.NEXT_WORD_START);
    }

    public static Result moveNextLongWordStart(JelixEditor jelixEditor) {
        return wordMove(jelixEditor, Target.NEXT_LONG_WORD_START);
    }

    public static Result moveNextWordEnd(JelixEditor jelixEditor) {
        return wordMove(jelixEditor, Target.NEXT_WORD_END);
    }

    public static Result moveNextLongWordEnd(JelixEditor jelixEditor) {
        return wordMove(jelixEditor, Target.NEXT_LONG_WORD_END);
    }

    public static Result movePrevWordStart(JelixEditor jelixEditor) {
        return wordMove(jelixEditor, Target.PREV_WORD_START);
    }

    public static Result movePrevLongWordStart(JelixEditor jelixEditor) {
        return wordMove(jelixEditor, Target.PREV_LONG_WORD_START);
    }

    public static Result movePrevWordEnd(JelixEditor jelixEditor) {
        return wordMove(jelixEditor, Target.PREV_WORD_END);
    }

    private static Result wordMove(JelixEditor jelixEditor, Target target) {
        var ed = jelixEditor.getEditor();
        var isPrev = target.isPrev();
        ed.getCaretModel().runForEachCaret(c -> {
            if (isPrev && c.getOffset() == 0 ||
                !isPrev && c.getOffset() == ed.getDocument().getTextLength()) {
                return;
            }

            var startRange = new Range(c.getOffset());

            for (int i = 0; i < jelixEditor.getCount(); i++) {
                startRange = rangeToTarget(target, startRange, ed.getDocument().getImmutableCharSequence());
            }

            c.moveToOffset(startRange.head);
            c.setSelection(startRange.anchor, startRange.head);
        });
        return Result.EXECUTE;
    }

    private static Range rangeToTarget(Target target, Range origin, CharSequence txt) {
        var isPrev = target.isPrev();
        var iter = new DocIter(txt, origin.head);
        if (isPrev) {
            iter.reverse();
        }

        // Function to advance index in the appropriate motion direction.
        Consumer<Range> advance = r -> r.head += isPrev ? -1 : 1;

        // Initialize state variables.
        var range = new Range(origin);
        var prevCh = iter.prev();
        if (prevCh.isPresent()) {
            iter.next();
        }

        // Skip any initial newline characters.
        while (true) {
            var ch = iter.next();
            if (ch.isEmpty()) {
                break;
            }
            if (isLineEnding(ch.get())) {
                prevCh = ch;
                advance.accept(range);
            } else {
                iter.prev();
                break;
            }
        }
        if (prevCh.map(CharCategory::isLineEnding).orElse(false)) {
            range.anchor = range.head;
        }

        // Find our target position(s).
        var headStart = range.head;
        while (true) {
            var nextCh = iter.next();
            if (nextCh.isEmpty()) {
                break;
            }
            if (prevCh.isEmpty() || reachedTarget(target, prevCh.get(), nextCh.get())) {
                if (range.head == headStart) {
                    range.anchor = range.head;
                } else {
                    break;
                }
            }
            prevCh = nextCh;
            advance.accept(range);
        }

        // Un-reverse the iterator if needed.
        if (isPrev) {
            iter.reverse();
        }

        return range;
    }

    private static boolean reachedTarget(Target target, char prevCh, char nextCh) {
        if (target == Target.NEXT_WORD_START ||
            target == Target.PREV_WORD_END) {
            return isWordBoundary(prevCh, nextCh) && (isLineEnding(nextCh) || !isWhitespace(nextCh));
        }
        if (target == Target.NEXT_WORD_END ||
            target == Target.PREV_WORD_START) {
            return isWordBoundary(prevCh, nextCh) && (!isWhitespace(prevCh) || isLineEnding(nextCh));
        }
        if (target == Target.NEXT_LONG_WORD_START) {
            return CharCategory.isLongWordBoundary(prevCh, nextCh) &&
                (isLineEnding(nextCh) || !isWhitespace(nextCh));
        }
        if (target == Target.NEXT_LONG_WORD_END ||
            target == Target.PREV_LONG_WORD_START) {
            return CharCategory.isLongWordBoundary(prevCh, nextCh) &&
                (!isWhitespace(prevCh) || isLineEnding(nextCh));
        }
        return false;
    }
}
