package fr.caemur.jelix.typing;

import com.intellij.openapi.actionSystem.ActionPromoter;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.EmptyAction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

public class JxActionPromoter implements ActionPromoter {
    @Override
    public @Nullable List<AnAction> promote(@NotNull List<? extends AnAction> actions, @NotNull DataContext context) {
        return actions.stream()
            .filter(action -> action instanceof EmptyAction.MyDelegatingAction
                && ((EmptyAction.MyDelegatingAction) action).getDelegate() instanceof JelixShortcutAction)
            .collect(Collectors.toList());
    }
}
