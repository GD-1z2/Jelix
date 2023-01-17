package fr.caemur.jelix.util;

import fr.caemur.jelix.providers.CommandProvider;
import fr.caemur.jelix.providers.InsertProvider;
import fr.caemur.jelix.providers.NormalProvider;

import java.util.function.Supplier;

public enum Mode {
    NORMAL(NormalProvider::new),
    INSERT(InsertProvider::new),
    SELECT(() -> null);

    private final Supplier<CommandProvider> actionProviderSupplier;

    Mode(Supplier<CommandProvider> actionProviderSupplier) {
        this.actionProviderSupplier = actionProviderSupplier;
    }

    @Override
    public String toString() {
        return this.name().substring(0, 3);
    }

    public CommandProvider getDefaultActionProvider() {
        return this.actionProviderSupplier.get();
    }
}
