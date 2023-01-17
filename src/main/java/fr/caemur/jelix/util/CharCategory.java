package fr.caemur.jelix.util;

public enum CharCategory {
    WHITESPACE,
    EOL,
    WORD,
    PUNCTUATION,
    UNKNOWN;

    public static CharCategory of(char c) {
        if (Character.isWhitespace(c)) {
            return WHITESPACE;
        } else if (isLineEnding(c)) {
            return EOL;
        } else if (Character.isLetterOrDigit(c)) {
            return WORD;
        } else if (isPunctuation(c)) {
            return PUNCTUATION;
        } else {
            return UNKNOWN;
        }
    }

    public static boolean isLineEnding(char ch) {
        return ch == '\n' || ch == '\u000B' || ch == '\u000C' || ch == '\r' || ch == '\u0085' || ch == '\u2028'
            || ch == '\u2029';
    }

    public static boolean isPunctuation(char ch) {
        var type = Character.getType(ch);
        return type == Character.OTHER_PUNCTUATION
            || type == Character.START_PUNCTUATION
            || type == Character.END_PUNCTUATION
            || type == Character.INITIAL_QUOTE_PUNCTUATION
            || type == Character.FINAL_QUOTE_PUNCTUATION
            || type == Character.CONNECTOR_PUNCTUATION
            || type == Character.DASH_PUNCTUATION
            || type == Character.MATH_SYMBOL
            || type == Character.CURRENCY_SYMBOL
            || type == Character.MODIFIER_SYMBOL;
    }

    public static boolean isWordBoundary(char a, char b) {
        return of(a) != of(b);
    }

    public static boolean isLongWordBoundary(char a, char b) {
        var aCat = of(a);
        var bCat = of(b);
        if (((aCat == WORD) && (bCat == PUNCTUATION)) || ((aCat == PUNCTUATION) && (bCat == WORD))) {
            return false;
        }
        return aCat != bCat;
    }
}
