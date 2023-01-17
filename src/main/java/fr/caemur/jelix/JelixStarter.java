package fr.caemur.jelix;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import org.jetbrains.annotations.NotNull;

public class JelixStarter implements StartupActivity.DumbAware {
    @Override
    public void runActivity(@NotNull Project project) {
        Jelix.getInstance().init();
    }
}
