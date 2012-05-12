package net.sourceforge.lxmed.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.sourceforge.lxmed.actions.ActionManager;
import net.sourceforge.lxmed.commands.CommandManager;
import net.sourceforge.lxmed.commands.DeleteItemCommand;
import net.sourceforge.lxmed.model.Category;
import net.sourceforge.lxmed.model.MenuItem;
import net.sourceforge.lxmed.model.Model;
import net.sourceforge.lxmed.persistence.ModelLoader;

/**
 * Application's main window. It allows basic manipulating of main menu
 * structure.
 *
 * @author <a href="mailto:cicakmarko@yahoo.com">Marko Čičak</a>
 */
public class MainFrame extends javax.swing.JFrame implements Observer {

    /**
     * List model for menu items.
     */
    protected DefaultListModel dlmItems = new DefaultListModel();
    /**
     * List model for categories.
     */
    protected DefaultListModel dlmCategories = new DefaultListModel();
    /**
     * Cell renderer for menu items list.
     */
    protected MenuItemsListCellRenderer menuItemsListCellRenderer = new MenuItemsListCellRenderer();
    /**
     * Cell renderer for categories.
     */
    protected CategoriesListCellRenderer categoriesListCellRenderer = new CategoriesListCellRenderer(Model.getModel().getCategories());
    /**
     * Action manager.
     */
    protected ActionManager actionManager = ActionManager.getInstance();
    /**
     * Singleton instance.
     */
    private static MainFrame instance;

    /**
     * Returns MainFrame singleton instance.
     */
    public static MainFrame getInstance() {
        if (instance == null) {
            instance = new MainFrame();
        }

        return instance;
    }

    /**
     * Creates new form MainFrame.
     */
    private MainFrame() {
        setPreferredSize(new Dimension(640, 450));
        for (Category categorie : Model.getModel().getCategories()) {
            dlmCategories.addElement(categorie);
        }
        initComponents();
        lstCategories.setSelectedIndex(0);
        setLocationRelativeTo(null);
        //btnHelp.setEnabled(false);
        Image iconimage = new ImageIcon(getClass().getResource("/net/sourceforge/lxmed/images/dialogs/lxmed_small.png")).getImage();
        setIconImage(iconimage);
    }

    private void enableControls() {
        btnDelete.setEnabled(true);
        btnProperties.setEnabled(true);
    }

    private void disableControls() {
        btnDelete.setEnabled(false);
        btnProperties.setEnabled(false);
    }

    private void editItem() {
        MenuItem mi = (MenuItem) lstItems.getSelectedValue();
        if (mi == null) {
            return;
        }

        new MenuItemDialog(this, mi).setVisible(true);
        lstItems.requestFocus();
    }

    private void deleteItem() {
        if (lstItems.getSelectedValue() == null) {
            return;
        }

        if (!((MenuItem) lstItems.getSelectedValue()).isReadOnly()) {
            int confirm = JOptionPane.showConfirmDialog(this, "Really delete file? You can just make it invisible in properties dialog.", "Delete file?", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                MenuItem toDelete = (MenuItem) lstItems.getSelectedValue();
                DeleteItemCommand dic = new DeleteItemCommand(toDelete);
                CommandManager.getInstance().addCommand(dic);
            }
        }
    }

    public void updateCategory() {
        dlmItems.clear();
        Category c = (Category) lstCategories.getSelectedValue();
        ModelLoader.sortItemsByName(c);
        for (MenuItem menuItem : c) {
            dlmItems.addElement(menuItem);
        }
        disableControls();
    }

    public JButton getBtnRedo() {
        return btnRedo;
    }

    public JButton getBtnUndo() {
        return btnUndo;
    }

    public void update(Observable o, Object arg) {
        updateCategory();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        GridBagConstraints gridBagConstraints;

        pnlSouth = new JPanel();
        btnHelp = new JButton();
        btnAbout = new JButton();
        btnClose = new JButton();
        sprSouth = new JSeparator();
        pnlNorth = new JPanel();
        lblTitle = new JLabel();
        sprTitle = new JSeparator();
        pnlCenter = new JPanel();
        lblCategories = new JLabel();
        spCategories = new JScrollPane();
        lstCategories = new JList();
        spItems = new JScrollPane();
        lstItems = new JList();
        lblItems = new JLabel();
        pnlControls = new JPanel();
        btnNewItem = new JButton();
        btnProperties = new JButton();
        btnDelete = new JButton();
        jSeparator1 = new JSeparator();
        btnUndo = new JButton();
        btnRedo = new JButton();
        jSeparator2 = new JSeparator();
        btnCut = new JButton();
        btnCopy = new JButton();
        btnPaste = new JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("LXDE Main Menu Editor");

        pnlSouth.setLayout(new GridBagLayout());

        btnHelp.setFont(btnHelp.getFont().deriveFont(btnHelp.getFont().getStyle() & ~Font.BOLD, btnHelp.getFont().getSize()-1));
        btnHelp.setIcon(new ImageIcon(getClass().getResource("/net/sourceforge/lxmed/images/dialogs/help.png")));         btnHelp.setMnemonic('h');
        btnHelp.setText("Help");
        btnHelp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnHelpActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(0, 10, 0, 10);
        pnlSouth.add(btnHelp, gridBagConstraints);

        btnAbout.setFont(btnAbout.getFont().deriveFont(btnAbout.getFont().getStyle() & ~Font.BOLD, btnAbout.getFont().getSize()-1));
        btnAbout.setIcon(new ImageIcon(getClass().getResource("/net/sourceforge/lxmed/images/dialogs/about.png")));         btnAbout.setMnemonic('b');
        btnAbout.setText("About");
        btnAbout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnAboutActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new Insets(10, 10, 10, 10);
        pnlSouth.add(btnAbout, gridBagConstraints);

        btnClose.setFont(btnClose.getFont().deriveFont(btnClose.getFont().getStyle() & ~Font.BOLD, btnClose.getFont().getSize()-1));
        btnClose.setIcon(new ImageIcon(getClass().getResource("/net/sourceforge/lxmed/images/dialogs/exit.png")));         btnClose.setMnemonic('s');
        btnClose.setText("Close");
        btnClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        gridBagConstraints.insets = new Insets(0, 10, 0, 10);
        pnlSouth.add(btnClose, gridBagConstraints);

        sprSouth.setForeground(new Color(0, 0, 0));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        pnlSouth.add(sprSouth, gridBagConstraints);

        getContentPane().add(pnlSouth, BorderLayout.SOUTH);

        pnlNorth.setLayout(new GridBagLayout());

        lblTitle.setFont(lblTitle.getFont().deriveFont(lblTitle.getFont().getStyle() | Font.BOLD, lblTitle.getFont().getSize()-1));
        lblTitle.setText("Configure main menu:");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new Insets(7, 10, 7, 10);
        pnlNorth.add(lblTitle, gridBagConstraints);

        sprTitle.setForeground(new Color(0, 0, 0));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(0, 10, 0, 10);
        pnlNorth.add(sprTitle, gridBagConstraints);

        getContentPane().add(pnlNorth, BorderLayout.NORTH);

        pnlCenter.setLayout(new GridBagLayout());

        lblCategories.setDisplayedMnemonic('a');
        lblCategories.setFont(lblCategories.getFont().deriveFont(lblCategories.getFont().getStyle() & ~Font.BOLD, lblCategories.getFont().getSize()-1));
        lblCategories.setLabelFor(lstCategories);
        lblCategories.setText("Categories:");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(12, 12, 0, 0);
        pnlCenter.add(lblCategories, gridBagConstraints);

        lstCategories.setFont(lstCategories.getFont().deriveFont(lstCategories.getFont().getStyle() & ~Font.BOLD, lstCategories.getFont().getSize()-1));
        lstCategories.setModel(dlmCategories);
        lstCategories.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lstCategories.setCellRenderer(categoriesListCellRenderer);
        lstCategories.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                lstCategoriesMouseClicked(evt);
            }
        });
        lstCategories.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {
                lstCategoriesValueChanged(evt);
            }
        });
        lstCategories.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                lstCategoriesKeyReleased(evt);
            }
        });
        spCategories.setViewportView(lstCategories);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 165;
        gridBagConstraints.ipady = 275;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(6, 12, 12, 0);
        pnlCenter.add(spCategories, gridBagConstraints);

        lstItems.setFont(lstItems.getFont().deriveFont(lstItems.getFont().getStyle() & ~Font.BOLD, lstItems.getFont().getSize()-1));
        lstItems.setModel(dlmItems);
        lstItems.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lstItems.setCellRenderer(menuItemsListCellRenderer);
        lstItems.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                lstItemsMouseClicked(evt);
            }
        });
        lstItems.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {
                lstItemsValueChanged(evt);
            }
        });
        lstItems.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                lstItemsKeyPressed(evt);
            }
            public void keyReleased(KeyEvent evt) {
                lstItemsKeyReleased(evt);
            }
        });
        spItems.setViewportView(lstItems);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 264;
        gridBagConstraints.ipady = 275;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(6, 10, 12, 1);
        pnlCenter.add(spItems, gridBagConstraints);

        lblItems.setDisplayedMnemonic('t');
        lblItems.setFont(lblItems.getFont().deriveFont(lblItems.getFont().getStyle() & ~Font.BOLD, lblItems.getFont().getSize()-1));
        lblItems.setLabelFor(lstItems);
        lblItems.setText("Menu Items:");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(12, 10, 0, 0);
        pnlCenter.add(lblItems, gridBagConstraints);

        getContentPane().add(pnlCenter, BorderLayout.CENTER);

        pnlControls.setLayout(new GridBagLayout());

        btnNewItem.setFont(btnNewItem.getFont().deriveFont(btnNewItem.getFont().getStyle() & ~Font.BOLD, btnNewItem.getFont().getSize()-1));
        btnNewItem.setIcon(new ImageIcon(getClass().getResource("/net/sourceforge/lxmed/images/dialogs/new.png")));         btnNewItem.setMnemonic('n');
        btnNewItem.setText("New Item");
        btnNewItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnNewItemActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        gridBagConstraints.insets = new Insets(31, 10, 5, 10);
        pnlControls.add(btnNewItem, gridBagConstraints);

        btnProperties.setFont(btnProperties.getFont().deriveFont(btnProperties.getFont().getStyle() & ~Font.BOLD, btnProperties.getFont().getSize()-1));
        btnProperties.setIcon(new ImageIcon(getClass().getResource("/net/sourceforge/lxmed/images/dialogs/properties.png")));         btnProperties.setMnemonic('r');
        btnProperties.setText("Properties");
        btnProperties.setEnabled(false);
        btnProperties.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnPropertiesActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        gridBagConstraints.insets = new Insets(5, 10, 5, 10);
        pnlControls.add(btnProperties, gridBagConstraints);

        btnDelete.setFont(btnDelete.getFont().deriveFont(btnDelete.getFont().getStyle() & ~Font.BOLD, btnDelete.getFont().getSize()-1));
        btnDelete.setIcon(new ImageIcon(getClass().getResource("/net/sourceforge/lxmed/images/dialogs/delete.png")));         btnDelete.setMnemonic('d');
        btnDelete.setText("Delete");
        btnDelete.setEnabled(false);
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        gridBagConstraints.insets = new Insets(5, 10, 5, 10);
        pnlControls.add(btnDelete, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(2, 10, 2, 10);
        pnlControls.add(jSeparator1, gridBagConstraints);

        btnUndo.setFont(new Font("Dialog", 0, 11));         btnUndo.setIcon(new ImageIcon(getClass().getResource("/net/sourceforge/lxmed/images/dialogs/edit-undo.png")));         btnUndo.setMnemonic('u');
        btnUndo.setText("Undo");
        btnUndo.setEnabled(false);
        btnUndo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnUndoActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        gridBagConstraints.insets = new Insets(5, 10, 5, 10);
        pnlControls.add(btnUndo, gridBagConstraints);

        btnRedo.setFont(new Font("Dialog", 0, 11));         btnRedo.setIcon(new ImageIcon(getClass().getResource("/net/sourceforge/lxmed/images/dialogs/edit-redo.png")));         btnRedo.setMnemonic('e');
        btnRedo.setText("Redo");
        btnRedo.setEnabled(false);
        btnRedo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnRedoActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        gridBagConstraints.insets = new Insets(5, 10, 5, 10);
        pnlControls.add(btnRedo, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(2, 10, 2, 10);
        pnlControls.add(jSeparator2, gridBagConstraints);

        btnCut.setFont(new Font("Dialog", 0, 11));         btnCut.setIcon(new ImageIcon(getClass().getResource("/net/sourceforge/lxmed/images/dialogs/edit-cut.png")));         btnCut.setMnemonic('c');
        btnCut.setText("Cut");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        gridBagConstraints.insets = new Insets(5, 10, 5, 10);
        pnlControls.add(btnCut, gridBagConstraints);

        btnCopy.setFont(new Font("Dialog", 0, 11));         btnCopy.setIcon(new ImageIcon(getClass().getResource("/net/sourceforge/lxmed/images/dialogs/edit-copy.png")));         btnCopy.setMnemonic('y');
        btnCopy.setText("Copy");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        gridBagConstraints.insets = new Insets(5, 10, 5, 10);
        pnlControls.add(btnCopy, gridBagConstraints);

        btnPaste.setFont(new Font("Dialog", 0, 11));         btnPaste.setIcon(new ImageIcon(getClass().getResource("/net/sourceforge/lxmed/images/dialogs/edit-paste.png")));         btnPaste.setMnemonic('p');
        btnPaste.setText("Paste");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new Insets(5, 10, 5, 10);
        pnlControls.add(btnPaste, gridBagConstraints);

        getContentPane().add(pnlControls, BorderLayout.EAST);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnHelpActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnHelpActionPerformed
        // TODO: some notification that the browser is opening
        try {
            Runtime.getRuntime().exec("firefox http://lxmed.sourceforge.net/help.html");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error while launching firefox browser. Visit http://lxmed.sourceforge.net/help.html for help", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnHelpActionPerformed

    private void btnAboutActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnAboutActionPerformed
        String msg = "LXDE Main Menu Editor 20120515-beta\n\n";
        msg += "Copyleft 2011. HEEM-BA-SHOU\n";
        JOptionPane.showMessageDialog(this, msg, "About", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnAboutActionPerformed

    private void btnNewItemActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnNewItemActionPerformed
        MenuItemDialog mid = new MenuItemDialog(this, null);
        mid.setDefaultCategory((Category) lstCategories.getSelectedValue());
        mid.setVisible(true);
    }//GEN-LAST:event_btnNewItemActionPerformed

    private void btnPropertiesActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnPropertiesActionPerformed
        editItem();
    }//GEN-LAST:event_btnPropertiesActionPerformed

    private void btnDeleteActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        deleteItem();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void lstCategoriesValueChanged(ListSelectionEvent evt) {//GEN-FIRST:event_lstCategoriesValueChanged
        if (evt.getValueIsAdjusting()) {
            return;
        }

        updateCategory();
    }//GEN-LAST:event_lstCategoriesValueChanged

    private void lstItemsValueChanged(ListSelectionEvent evt) {//GEN-FIRST:event_lstItemsValueChanged
        if (evt.getValueIsAdjusting()) {
            return;
        }

        MenuItem mi = (MenuItem) lstItems.getSelectedValue();
        if (mi == null) {
            disableControls();
            return;
        }

        if (mi.isReadOnly()) {
            btnProperties.setEnabled(true);
            btnDelete.setEnabled(false);
            return;
        }

        enableControls();
    }//GEN-LAST:event_lstItemsValueChanged

    private void lstItemsMouseClicked(MouseEvent evt) {//GEN-FIRST:event_lstItemsMouseClicked
        if (evt.getButton() == MouseEvent.BUTTON3) {

            lstItems.setSelectedIndex(lstItems.locationToIndex(evt.getPoint()));
            MenuItem mi = (MenuItem) lstItems.getSelectedValue();

            JPopupMenu popupMenu = new JPopupMenu();
            if (mi.isReadOnly()) {
                actionManager.getCutAction().setEnabled(false);
            } else {
                actionManager.getCutAction().setEnabled(true);
            }
            popupMenu.add(actionManager.getCutAction());
            popupMenu.add(actionManager.getCopyAction());
            popupMenu.show(lstItems, evt.getX(), evt.getY());
        } else if (evt.getClickCount() == 2) {
            editItem();
        }
    }//GEN-LAST:event_lstItemsMouseClicked

    private void lstItemsKeyPressed(KeyEvent evt) {//GEN-FIRST:event_lstItemsKeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                editItem();
                break;
            case KeyEvent.VK_DELETE:
                deleteItem();
                break;
        }
    }//GEN-LAST:event_lstItemsKeyPressed

    private void lstCategoriesMouseClicked(MouseEvent evt) {//GEN-FIRST:event_lstCategoriesMouseClicked
        if (evt.getButton() == MouseEvent.BUTTON3) {

            lstCategories.setSelectedIndex(lstCategories.locationToIndex(evt.getPoint()));

            JPopupMenu popupMenu = new JPopupMenu();
            popupMenu.add(actionManager.getPasteAction());
            popupMenu.show(lstCategories, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_lstCategoriesMouseClicked

    private void lstCategoriesKeyReleased(KeyEvent evt) {//GEN-FIRST:event_lstCategoriesKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_CONTEXT_MENU) {
            JPopupMenu popupMenu = new JPopupMenu();
            popupMenu.add(actionManager.getPasteAction());
            JList source = (JList) evt.getSource();

            popupMenu.show(source, 5, 5);
        }
    }//GEN-LAST:event_lstCategoriesKeyReleased

    private void lstItemsKeyReleased(KeyEvent evt) {//GEN-FIRST:event_lstItemsKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_CONTEXT_MENU) {
            JPopupMenu popupMenu = new JPopupMenu();
            popupMenu.add(actionManager.getCutAction());
            popupMenu.add(actionManager.getCopyAction());
            JList source = (JList) evt.getSource();

            popupMenu.show(source, 5, 5);
        }
    }//GEN-LAST:event_lstItemsKeyReleased

    private void btnUndoActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnUndoActionPerformed
        CommandManager.getInstance().undoCommand();
    }//GEN-LAST:event_btnUndoActionPerformed

    private void btnRedoActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnRedoActionPerformed
        CommandManager.getInstance().doCommand();
    }//GEN-LAST:event_btnRedoActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton btnAbout;
    private JButton btnClose;
    private JButton btnCopy;
    private JButton btnCut;
    private JButton btnDelete;
    private JButton btnHelp;
    private JButton btnNewItem;
    private JButton btnPaste;
    private JButton btnProperties;
    private JButton btnRedo;
    private JButton btnUndo;
    private JSeparator jSeparator1;
    private JSeparator jSeparator2;
    private JLabel lblCategories;
    private JLabel lblItems;
    private JLabel lblTitle;
    private JList lstCategories;
    private JList lstItems;
    private JPanel pnlCenter;
    private JPanel pnlControls;
    private JPanel pnlNorth;
    private JPanel pnlSouth;
    private JScrollPane spCategories;
    private JScrollPane spItems;
    private JSeparator sprSouth;
    private JSeparator sprTitle;
    // End of variables declaration//GEN-END:variables
}
