package fr.caemur.jelix.commands;

import com.intellij.codeInsight.documentation.actions.ShowQuickDocInfoAction;
import com.intellij.ide.actions.GotoActionAction;
import com.intellij.ide.actions.GotoFileAction;
import com.intellij.ide.actions.GotoSymbolAction;
import com.intellij.openapi.actionSystem.ex.ActionUtil;
import com.intellij.refactoring.actions.RenameElementAction;
import fr.caemur.jelix.JelixEditor;
import fr.caemur.jelix.providers.GlobalProvider;
import fr.caemur.jelix.util.Result;

public class GlobalMenu {
    public static Result globalMenu(JelixEditor jelixEditor) {
        jelixEditor.setActionProvider(new GlobalProvider(jelixEditor));
        return Result.CONTINUE;
    }

    public static Result showFilePicker(JelixEditor jelixEditor) {
        ActionUtil.invokeAction(
            new GotoFileAction(),
            jelixEditor.getEditor().getContentComponent(),
            "", null, null);
        return Result.EXECUTE;
    }

    public static Result showSymbolPicker(JelixEditor jelixEditor) {
        ActionUtil.invokeAction(
            new GotoSymbolAction(),
            jelixEditor.getEditor().getContentComponent(),
            "", null, null);
        return Result.EXECUTE;
    }

    public static Result showDocs(JelixEditor jelixEditor) {
        ActionUtil.invokeAction(
            new ShowQuickDocInfoAction(),
            jelixEditor.getEditor().getContentComponent(),
            "", null, null);
        return Result.EXECUTE;
    }

    public static Result renameSymbol(JelixEditor jelixEditor) {
        ActionUtil.invokeAction(
            new RenameElementAction(),
            jelixEditor.getEditor().getContentComponent(),
            "", null, null);
        return Result.EXECUTE;
    }

    public static Result showCommandPicker(JelixEditor jelixEditor) {
        ActionUtil.invokeAction(
            new GotoActionAction(),
            jelixEditor.getEditor().getContentComponent(),
            "", null, null);
        return Result.EXECUTE;
    }
}
