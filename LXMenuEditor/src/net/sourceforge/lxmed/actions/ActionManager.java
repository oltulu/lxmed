// lxmed - LXDE Main Menu Editor
// Copyright (C) 2011  Marko Čičak
//
// This file is part of lxmed.
//
// lxmed is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// lxmed is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with lxmed.  If not, see <http://www.gnu.org/licenses/>.
package net.sourceforge.lxmed.actions;

/**
 * ActionManager is a singleton containing all actions used in app.
 *
 * @author <a href="mailto:cicakmarko@yahoo.com">Marko Čičak</a>
 */
public class ActionManager {

    /**
     * Singleton instance.
     */
    private static ActionManager instance;
    /**
     * Cut action.
     */
    private CutAction cutAction = new CutAction();
    /**
     * Copy action.
     */
    private CopyAction copyAction = new CopyAction();
    /**
     * Paste action.
     */
    private PasteAction pasteAction = new PasteAction();

    /**
     * Action manager instance.
     */
    public static ActionManager getInstance() {
        if (instance == null) {
            instance = new ActionManager();
        }

        return instance;
    }

    /**
     * Private constructor.
     */
    private ActionManager() {
    }

    /**
     * {@link CopyAction}
     */
    public CopyAction getCopyAction() {
        return copyAction;
    }

    /**
     * {@link CutAction}
     */
    public CutAction getCutAction() {
        return cutAction;
    }

    /**
     * {@link PasteAction}
     */
    public PasteAction getPasteAction() {
        return pasteAction;
    }
}
