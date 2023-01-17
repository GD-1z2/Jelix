package fr.caemur.jelix.util;

public enum Result {
    /**
     * An action has not been executed yet ; keep editor state.
     */
    CONTINUE,

    /**
     * An action was successfully executed ; return to default state.
     */
    EXECUTE,

    /**
     * An action was found, but failed to execute ; return to default state.
     */
    FAILED,
}
