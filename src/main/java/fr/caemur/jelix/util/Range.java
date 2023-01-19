package fr.caemur.jelix.util;

import com.intellij.openapi.editor.CaretModel;

import java.util.function.Function;

public class Range {
    public int anchor, head;

    public Range(int anchor, int head) {
        this.anchor = anchor;
        this.head = head;
    }

    public Range(int head) {
        this.head = head;
        this.anchor = head;
    }

    public Range(Range range) {
        this.anchor = range.anchor;
        this.head = range.head;
    }

    public static void transform(CaretModel caret, Function<Range, Range> f) {
        caret.runForEachCaret(c -> {
            final var range = new Range(c.getOffset(), c.getLeadSelectionOffset());
            final var newRange = f.apply(range);
            c.moveToOffset(newRange.head);
            c.setSelection(newRange.anchor, newRange.head);
        });
    }

    public Range putCursor(int charIndex, boolean extend) {
        if (extend) {
            return new Range(this.anchor, charIndex);
        } else {
            return new Range(charIndex);
        }
    }
}
