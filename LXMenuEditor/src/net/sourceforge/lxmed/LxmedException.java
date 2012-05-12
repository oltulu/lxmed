package net.sourceforge.lxmed;

/**
 * Lxmed Exception.
 *
 * @author <a href="mailto:cicakmarko@yahoo.com">Marko Čičak</a>
 */
public class LxmedException extends RuntimeException {

    /**
     * Title for message box.
     */
    private String title;
    /**
     * Text for message box.
     */
    private String message;

    public LxmedException(String message) {
        super(message);
    }

    public LxmedException(String title, String message) {
        super(message);
        this.title = title;
        this.message = message;
    }
}
