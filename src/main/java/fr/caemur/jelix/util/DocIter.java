package fr.caemur.jelix.util;

import java.util.Iterator;
import java.util.Optional;

public class DocIter implements Iterator<Optional<Character>> {
    private final CharSequence doc;
    private int pos;
    private boolean reversed = false;

    public DocIter(CharSequence doc, int pos) {
        this.doc = doc;
        this.pos = pos;
    }

    @Override
    public boolean hasNext() {
        return reversed ? pos > 0 : pos < doc.length();
    }

    public boolean hasPrev() {
        return reversed ? pos < doc.length() : pos > 0;
    }

    @Override
    public Optional<Character> next() {
        if (!hasNext()) {
            return Optional.empty();
        }
        return Optional.of(doc.charAt(reversed ? --pos : pos++));
    }

    public Optional<Character> prev() {
        if (!hasPrev()) {
            return Optional.empty();
        }
        return Optional.of(doc.charAt(reversed ? pos++ : --pos));
    }

    public void reverse() {
        reversed = !reversed;
    }

    public int getOffset() {
        return pos;
    }
}
