package net.sourceforge.lxmed.persistence;

/**
 * Indetifies wheather a user launching an application is root or not.
 * @author <a href="mailto:cicakmarko@yahoo.com">Marko Čičak</a>
 */
public class UserDeterminator {

    /**
     * Determines a user and sets IS_ROOT attribute in Configuration class.
     * @see Configuration#IS_ROOT
     */
    public static void determineUser() {
        String userId = ProcessExecutor.execute("id -u");

        Integer id = Integer.parseInt(userId);

        if (id == 0) {
            Configuration.IS_ROOT = true;
        } else {
            Configuration.IS_ROOT = false;
        }
    }
}
