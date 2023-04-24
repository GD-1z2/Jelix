package fr.caemur.jelix.util;

import com.intellij.openapi.editor.CaretVisualAttributes;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.ex.EditorEx;

public enum CaretShape {
    BLOCK(CaretVisualAttributes.Shape.BLOCK, 1f),
    BAR(CaretVisualAttributes.Shape.BAR, .25f);

    private final CaretVisualAttributes attributes;

    CaretShape(CaretVisualAttributes.Shape shape, float thickness) {
        this.attributes = new CaretVisualAttributes(null, CaretVisualAttributes.Weight.NORMAL, shape, thickness);
    }

    public void apply(Editor editor) {
        editor.getCaretModel().runForEachCaret(caret -> caret.setVisualAttributes(this.attributes));
        if (editor instanceof EditorEx) {
            EditorEx editorEx = (EditorEx) editor;
            editorEx.setCaretVisible(true);
        }
    }
}
