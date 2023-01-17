package fr.caemur.jelix.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.openapi.wm.StatusBarWidget;
import com.intellij.openapi.wm.impl.status.EditorBasedWidget;
import com.intellij.openapi.wm.impl.status.widget.StatusBarEditorBasedWidgetFactory;
import com.intellij.util.Consumer;
import fr.caemur.jelix.Jelix;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class JxWidgetFactory extends StatusBarEditorBasedWidgetFactory {
    @Override
    public @NonNls @NotNull String getId() {
        return "Jelix";
    }

    @Override
    public @Nls @NotNull String getDisplayName() {
        return "Jelix Status";
    }

    @Override
    public @NotNull StatusBarWidget createWidget(@NotNull Project project) {
        return new JxWidget(project);
    }

    @Override
    public void disposeWidget(@NotNull StatusBarWidget widget) {
    }

    private static class JxWidget extends EditorBasedWidget implements StatusBarWidget.TextPresentation {
        private String text = "Jelix";

        protected JxWidget(@NotNull Project project) {
            super(project);
        }

        @Override
        public @NotNull @NlsContexts.Label String getText() {
            this.update();
            return this.text;
        }

        private void update() {
            final var ed = Jelix.getInstance().getEditor(this.getEditor());
            if (ed == null) {
                this.text = "Jelix";
                return;
            }

            final var builder = new StringBuilder();
            builder.append(ed.getMode());
            builder.append(" ");
            for (KeyStroke key : ed.getKeyStrokes()) {
                builder.append(key.getKeyChar());
            }
            this.text = builder.toString();
        }

        @Override
        public @NonNls @NotNull String ID() {
            return "Jelix";
        }

        @Override
        public @Nullable WidgetPresentation getPresentation() {
            return this;
        }

        @Override
        public float getAlignment() {
            return Component.CENTER_ALIGNMENT;
        }

        @Override
        public @Nullable @NlsContexts.Tooltip String getTooltipText() {
            return null;
        }

        @Override
        public @Nullable Consumer<MouseEvent> getClickConsumer() {
            return null;
        }
    }
}
