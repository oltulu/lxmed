package net.sourceforge.lxmed.commands;

/**
 *
 * @author <a href="mailto:cicakmarko@yahoo.com">Marko Čičak</a>
 */
public interface LxmedCommand {

    void doCommand();

    void undoCommand();
}
