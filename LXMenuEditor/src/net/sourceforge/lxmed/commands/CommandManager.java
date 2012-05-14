package net.sourceforge.lxmed.commands;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import net.sourceforge.lxmed.LxmedException;
import net.sourceforge.lxmed.gui.MainFrame;

/**
 *
 * @author <a href="mailto:cicakmarko@yahoo.com">Marko Čičak</a>
 */
public class CommandManager {

    /**
     * Commands list.
     */
    private List<LxmedCommand> commands = new ArrayList<LxmedCommand>();
    /**
     * Cursor which indicates current command.
     */
    private int currentCommand = 0;
    /**
     * Singleton.
     */
    private static CommandManager instance;

    /**
     * Private constructor.
     */
    private CommandManager() {
    }

    /**
     * Command Manager instance.
     */
    public static CommandManager getInstance() {
        if (instance == null) {
            instance = new CommandManager();
        }

        return instance;
    }

    /**
     * Adds a new command to command list.
     *
     * @param command new command
     */
    public void addCommand(LxmedCommand command) throws LxmedException {
        while (currentCommand < commands.size()) {
            commands.remove(currentCommand);
        }
        try {
            commands.add(command);
            doCommand();
        } catch (LxmedException e) {
            throw e;
        }
    }

    /**
     * Executes a command.
     */
    public void doCommand() {
        if (currentCommand < commands.size()) {
            commands.get(currentCommand++).doCommand();
            refreshButtons();
        }
    }

    /**
     * Calls undo method on current command.
     */
    public void undoCommand() {
        if (currentCommand > 0) {
            commands.get(--currentCommand).undoCommand();
            refreshButtons();
        }
    }

    /**
     * Updates enability of Undo/Redo buttons.c
     */
    private void refreshButtons() {
        JButton undo = MainFrame.getInstance().getBtnUndo();
        JButton redo = MainFrame.getInstance().getBtnRedo();

        if (commands.isEmpty()) {
            undo.setEnabled(false);
            redo.setEnabled(false);
        } else if (currentCommand == commands.size()) {
            undo.setEnabled(true);
            redo.setEnabled(false);
        } else if (currentCommand == 0) {
            undo.setEnabled(false);
            redo.setEnabled(true);
        } else {
            undo.setEnabled(true);
            redo.setEnabled(true);
        }
    }
}
