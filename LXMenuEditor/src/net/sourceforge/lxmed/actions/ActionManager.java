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
    private UndoAction undoAction = new UndoAction();
    private RedoAction redoAction = new RedoAction();

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

    public RedoAction getRedoAction() {
        return redoAction;
    }

    public void setRedoAction(RedoAction redoAction) {
        this.redoAction = redoAction;
    }

    public UndoAction getUndoAction() {
        return undoAction;
    }

    public void setUndoAction(UndoAction undoAction) {
        this.undoAction = undoAction;
    }
}
