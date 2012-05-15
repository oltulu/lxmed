package net.sourceforge.lxmed.actions;

/**
 *
 * @author <a href="mailto:cicakmarko@yahoo.com">Marko Čičak</a>
 */
public class ActionManager {

    private static ActionManager instance;
    private CutAction cutAction = new CutAction();
    private CopyAction copyAction = new CopyAction();
    private PasteAction pasteAction = new PasteAction();

    public static ActionManager getInstance() {
        if (instance == null) {
            instance = new ActionManager();
        }

        return instance;
    }

    private ActionManager() {
    }

    public CopyAction getCopyAction() {
        return copyAction;
    }

    public void setCopyAction(CopyAction copyAction) {
        this.copyAction = copyAction;
    }

    public CutAction getCutAction() {
        return cutAction;
    }

    public void setCutAction(CutAction cutAction) {
        this.cutAction = cutAction;
    }

    public PasteAction getPasteAction() {
        return pasteAction;
    }

    public void setPasteAction(PasteAction pasteAction) {
        this.pasteAction = pasteAction;
    }
}
