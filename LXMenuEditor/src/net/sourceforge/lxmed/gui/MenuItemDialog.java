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
package net.sourceforge.lxmed.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import net.sourceforge.lxmed.LxmedException;
import net.sourceforge.lxmed.commands.CommandManager;
import net.sourceforge.lxmed.commands.EditItemCommand;
import net.sourceforge.lxmed.commands.NewItemCommand;
import net.sourceforge.lxmed.model.Category;
import net.sourceforge.lxmed.model.MenuItem;
import net.sourceforge.lxmed.model.Model;
import net.sourceforge.lxmed.persistence.FileUtil;
import net.sourceforge.lxmed.utils.Configuration;

/**
 * Dialog for editing specific menu item.
 *
 * @author <a href="mailto:cicakmarko@yahoo.com">Marko Čičak</a>
 */
public class MenuItemDialog extends javax.swing.JDialog {

    /**
     * Temporary menu item which is ignored if Cancel is clicked. If Ok was
     * clicked, then values from this menu item are written to actual menu item
     * named menuItem field.
     */
    protected MenuItem temporary;
    /**
     * Editing menu item from main form that will be changed after pressing OK.
     */
    protected MenuItem menuItem = null;
    /**
     * Menu item before pressing OK. It is a clone of received menu item.
     */
    protected MenuItem originalItem = null;
    /**
     * Category combo box model.
     */
    protected ComboBoxModel cbm;
    /**
     * Form regime - true if new item is being entered, false if existing item is
     * edited.
     */
    protected boolean newItem = false;
    /**
     * Default category.
     */
    protected Category defaultCategory;
    /**
     * {@link MainFrame}
     */
    protected MainFrame parent;
    /**
     * String for image-not-available status.
     */
    protected static String imageNotAvailable = "N/A";

    /**
     * Creates new form MenuItemDialog.
     *
     * @param parent parent frame
     * @param item menu item to edit
     */
    public MenuItemDialog(java.awt.Frame parent, MenuItem item) {
        super(parent, true);
        this.parent = (MainFrame) parent;
        this.menuItem = item;

        cbm = new DefaultComboBoxModel(Model.getModel().getCategories().toArray());
        initComponents();
        getRootPane().setDefaultButton(btnOk);
        setLocationRelativeTo(null);

        if (item == null) {
            newItem = true;
            cbVisible.setSelected(true);
            btnViewCode.setVisible(false);
            temporary = new MenuItem();
            menuItem = new MenuItem();
        } else {
            this.temporary = new MenuItem(item);
            try {
                this.originalItem = (MenuItem) item.clone();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // close on Esc
        this.getRootPane().getActionMap().put("close", new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                btnCancelActionPerformed(e);
            }
        });
        InputMap map = this.getRootPane().getInputMap(
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "close");
    }

    /**
     * Sets a font for file chooser so that it corresponds to the application's
     * font.
     *
     * @param comp list of components to whom a font is being set
     */
    private void setFileChooserFont(Component[] comp) {
        for (int x = 0; x < comp.length; x++) {
            if (comp[x] instanceof Container) {
                setFileChooserFont(((Container) comp[x]).getComponents());
            }

            try {
                comp[x].setFont(txtName.getFont());
            } catch (Exception e) {
            }//do nothing
        }
    }

    /**
     * Returns default category.
     */
    public Category getDefaultCategory() {
        return defaultCategory;
    }

    /**
     * Sets a default category.
     *
     * @param defaultCategory new default category
     */
    public void setDefaultCategory(Category defaultCategory) {
        this.defaultCategory = defaultCategory;
    }

    /**
     * Updates a form's GUI component to correspond to edited menu item.
     */
    void updateGui() {
        if (!Configuration.IS_ROOT && menuItem.isReadOnly()) {
            txtName.setEditable(false);
            txtCommand.setEditable(false);
            txtComment.setEditable(false);
            txtIcon.setEditable(false);
            cbCategories.setEnabled(false);
            cbVisible.setEnabled(false);
            btnOk.setEnabled(false);
            btnOk.setText("Close");
            btnOk.setMnemonic('c');
            btnCancel.setEnabled(false);
            btnBrowseIcon.setEnabled(false);
            btnBrowseCommand.setEnabled(false);
        }

        txtPath.setText(temporary.getPath().getAbsolutePath());
        txtName.setText(temporary.getName());
        txtCommand.setText(temporary.getExec());
        txtComment.setText(temporary.getComment());
        txtIcon.setText(temporary.getIconStr());
        cbCategories.setSelectedItem(temporary.getCategory());
        cbVisible.setSelected(!temporary.isNoDisplay());

        updateImage();
    }

    /**
     * Sets a form ready for entering new menu item.
     */
    private void readyForNew() {
        cbCategories.setSelectedItem(defaultCategory);
        txtPath.setText(Configuration.getAppsFolder());
        txtName.requestFocus();
    }

    /**
     * Checks and sets button Ok's enability status depending on whether
     * mandatory fields are filled.
     */
    private void checkBtnOk() {
        if (txtName.getText().trim().equals("")
                || txtCommand.getText().trim().equals("")) {
            btnOk.setEnabled(false);
        } else {
            btnOk.setEnabled(true);
        }
    }

    /**
     * Returns a menu item.
     */
    public MenuItem getMenuItem() {
        return temporary;
    }

    /**
     * Sets menu item.
     *
     * @param menuItem new menu item
     */
    public void setMenuItem(MenuItem menuItem) {
        this.temporary = menuItem;
    }

    /**
     * Creates new menu item. Sets properties for new menu item and then creates
     * {@link NewItemCommand} which creates new menu item.
     */
    private void processNewItem() {
        for (String key : temporary.getContent().keySet()) {
            menuItem.putToContentMap(key, temporary.getContent().get(key));
        }

        menuItem.setName(txtName.getText().trim());
        menuItem.setExec(txtCommand.getText().trim());
        menuItem.setComment(txtComment.getText().trim());
        menuItem.setIconStr(txtIcon.getText().trim());
        menuItem.setNoDisplay(!cbVisible.isSelected());
        menuItem.setCategory((Category) cbCategories.getSelectedItem());
        menuItem.setPath(new File(txtPath.getText().trim()));
        menuItem.setReadOnly(false);

        NewItemCommand newMenuItem = new NewItemCommand(menuItem);
        try {
            CommandManager.getInstance().addCommand(newMenuItem);
        } catch (LxmedException e) {
            Logger.getLogger(MenuItemDialog.class.getName()).log(Level.SEVERE, "Yeni menü öğesi hatası", e);
            JOptionPane.showMessageDialog(this, e.getMessage(), "Öğe kaydedilirken hata", JOptionPane.ERROR_MESSAGE);
        }
        parent = null;
        dispose();
    }

    /**
     * Saves edited menu item. Sets properties to edited menu item and then
     * creates {@link EditItemCommand} which edits given menu item.
     */
    private void processEditItem() {
        if (!menuItem.isReadOnly()) {
            for (String key : temporary.getContent().keySet()) {
                menuItem.putToContentMap(key, temporary.getContent().get(key));
            }

            menuItem.setName(txtName.getText().trim());
            menuItem.setExec(txtCommand.getText().trim());
            menuItem.setComment(txtComment.getText().trim());
            menuItem.setIconStr(txtIcon.getText().trim());
            menuItem.setNoDisplay(!cbVisible.isSelected());
            menuItem.setCategory((Category) cbCategories.getSelectedItem());
            menuItem.setPath(new File(txtPath.getText().trim()));
            menuItem.setReadOnly(false);

            EditItemCommand eic = new EditItemCommand(originalItem, menuItem);
            CommandManager.getInstance().addCommand(eic);
        }
        parent = null;
        dispose();
    }

    /**
     * Updates an image of menu item.
     */
    private void updateImage() {
        BufferedImage image;
        try {
            image = ImageIO.read(new File(txtIcon.getText().trim()));
        } catch (IOException ex) {
            lblImage.setText(imageNotAvailable);
            lblImage.setIcon(null);
            return;
        }

        Icon icon;
        try {
            icon = new ImageIcon(image.getScaledInstance(59, 59, Image.SCALE_AREA_AVERAGING));
        } catch (NullPointerException npe) {
            lblImage.setText(imageNotAvailable);
            lblImage.setIcon(null);
            return;
        }

        if (icon != null) {
            lblImage.setText("");
            lblImage.setIcon(icon);
        }
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

        pnlIcon = new JPanel();
        lblImage = new JLabel();
        pnlControls = new JPanel();
        btnCancel = new JButton();
        btnOk = new JButton();
        sepSouth = new JSeparator();
        btnViewCode = new JButton();
        pnlCenter = new JPanel();
        lblPath = new JLabel();
        txtPath = new JTextField();
        lblCategories = new JLabel();
        cbCategories = new JComboBox();
        lblName = new JLabel();
        txtName = new JTextField();
        lblCommand = new JLabel();
        txtCommand = new JTextField();
        btnBrowseCommand = new JButton();
        lblComment = new JLabel();
        txtComment = new JTextField();
        lblIcon = new JLabel();
        txtIcon = new JTextField();
        btnBrowseIcon = new JButton();
        cbVisible = new JCheckBox();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        pnlIcon.setFont(new Font("Dialog", 0, 11));
        pnlIcon.setLayout(new GridBagLayout());

        lblImage.setHorizontalAlignment(SwingConstants.CENTER);
        lblImage.setText("N/A");
        lblImage.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        lblImage.setMaximumSize(new Dimension(64, 64));
        lblImage.setMinimumSize(new Dimension(64, 64));
        lblImage.setPreferredSize(new Dimension(64, 64));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.insets = new Insets(0, 10, 0, 0);
        pnlIcon.add(lblImage, gridBagConstraints);

        getContentPane().add(pnlIcon, BorderLayout.WEST);

        pnlControls.setFont(new Font("Dialog", 0, 11));
        pnlControls.setLayout(new GridBagLayout());

        btnCancel.setFont(btnCancel.getFont().deriveFont(btnCancel.getFont().getStyle() & ~Font.BOLD, btnCancel.getFont().getSize()-1));
        btnCancel.setIcon(new ImageIcon(getClass().getResource("/net/sourceforge/lxmed/images/dialogs/cancel.png"))); // NOI18N
        btnCancel.setMnemonic('c');
        btnCancel.setText("İptal");
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new Insets(10, 0, 10, 0);
        pnlControls.add(btnCancel, gridBagConstraints);

        btnOk.setFont(btnOk.getFont().deriveFont(btnOk.getFont().getStyle() & ~Font.BOLD, btnOk.getFont().getSize()-1));
        btnOk.setIcon(new ImageIcon(getClass().getResource("/net/sourceforge/lxmed/images/dialogs/ok.png"))); // NOI18N
        btnOk.setMnemonic('o');
        btnOk.setText("Tamam");
        btnOk.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 17;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        gridBagConstraints.insets = new Insets(10, 10, 10, 10);
        pnlControls.add(btnOk, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(0, 10, 0, 10);
        pnlControls.add(sepSouth, gridBagConstraints);

        btnViewCode.setFont(btnViewCode.getFont().deriveFont(btnViewCode.getFont().getStyle() & ~Font.BOLD, btnViewCode.getFont().getSize()-1));
        btnViewCode.setIcon(new ImageIcon(getClass().getResource("/net/sourceforge/lxmed/images/dialogs/properties.png"))); // NOI18N
        btnViewCode.setMnemonic('e');
        btnViewCode.setText("Kodu manuel olarak düzenleyin");
        btnViewCode.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnViewCodeActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(0, 10, 0, 0);
        pnlControls.add(btnViewCode, gridBagConstraints);

        getContentPane().add(pnlControls, BorderLayout.SOUTH);

        pnlCenter.setFont(new Font("Dialog", 0, 11));

        lblPath.setFont(lblPath.getFont().deriveFont(lblPath.getFont().getStyle() & ~Font.BOLD, lblPath.getFont().getSize()-1));
        lblPath.setText("Yol:");

        txtPath.setEditable(false);
        txtPath.setFont(txtPath.getFont().deriveFont(txtPath.getFont().getStyle() & ~Font.BOLD, txtPath.getFont().getSize()-1));

        lblCategories.setDisplayedMnemonic('a');
        lblCategories.setFont(lblCategories.getFont().deriveFont(lblCategories.getFont().getStyle() & ~Font.BOLD, lblCategories.getFont().getSize()-1));
        lblCategories.setLabelFor(cbCategories);
        lblCategories.setText("Kategori:");

        cbCategories.setFont(cbCategories.getFont().deriveFont(cbCategories.getFont().getStyle() & ~Font.BOLD, cbCategories.getFont().getSize()-1));
        cbCategories.setMaximumRowCount(15);
        cbCategories.setModel(cbm);

        lblName.setDisplayedMnemonic('n');
        lblName.setFont(lblName.getFont().deriveFont(lblName.getFont().getStyle() & ~Font.BOLD, lblName.getFont().getSize()-1));
        lblName.setLabelFor(txtName);
        lblName.setText("İsim:");

        txtName.setFont(txtName.getFont().deriveFont(txtName.getFont().getStyle() & ~Font.BOLD, txtName.getFont().getSize()-1));
        txtName.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                txtNameKeyTyped(evt);
            }
        });

        lblCommand.setDisplayedMnemonic('d');
        lblCommand.setFont(lblCommand.getFont().deriveFont(lblCommand.getFont().getStyle() & ~Font.BOLD, lblCommand.getFont().getSize()-1));
        lblCommand.setLabelFor(txtCommand);
        lblCommand.setText("Komut:");

        txtCommand.setFont(txtCommand.getFont().deriveFont(txtCommand.getFont().getStyle() & ~Font.BOLD, txtCommand.getFont().getSize()-1));
        txtCommand.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                txtCommandKeyTyped(evt);
            }
        });

        btnBrowseCommand.setFont(btnBrowseCommand.getFont().deriveFont(btnBrowseCommand.getFont().getStyle() & ~Font.BOLD, btnBrowseCommand.getFont().getSize()-1));
        btnBrowseCommand.setIcon(new ImageIcon(getClass().getResource("/net/sourceforge/lxmed/images/dialogs/browse.png"))); // NOI18N
        btnBrowseCommand.setMnemonic('b');
        btnBrowseCommand.setText("Araştır...");
        btnBrowseCommand.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnBrowseCommandActionPerformed(evt);
            }
        });

        lblComment.setDisplayedMnemonic('m');
        lblComment.setFont(lblComment.getFont().deriveFont(lblComment.getFont().getStyle() & ~Font.BOLD, lblComment.getFont().getSize()-1));
        lblComment.setLabelFor(txtComment);
        lblComment.setText("Yorum Yap:");

        txtComment.setFont(txtComment.getFont().deriveFont(txtComment.getFont().getStyle() & ~Font.BOLD, txtComment.getFont().getSize()-1));

        lblIcon.setDisplayedMnemonic('i');
        lblIcon.setFont(lblIcon.getFont().deriveFont(lblIcon.getFont().getStyle() & ~Font.BOLD, lblIcon.getFont().getSize()-1));
        lblIcon.setLabelFor(txtIcon);
        lblIcon.setText("Simge:");

        txtIcon.setFont(txtIcon.getFont().deriveFont(txtIcon.getFont().getStyle() & ~Font.BOLD, txtIcon.getFont().getSize()-1));
        txtIcon.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                txtIconKeyTyped(evt);
            }
        });

        btnBrowseIcon.setFont(btnBrowseIcon.getFont().deriveFont(btnBrowseIcon.getFont().getStyle() & ~Font.BOLD, btnBrowseIcon.getFont().getSize()-1));
        btnBrowseIcon.setIcon(new ImageIcon(getClass().getResource("/net/sourceforge/lxmed/images/dialogs/browse.png"))); // NOI18N
        btnBrowseIcon.setMnemonic('r');
        btnBrowseIcon.setText("Araştır...");
        btnBrowseIcon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnBrowseIconActionPerformed(evt);
            }
        });

        cbVisible.setFont(cbVisible.getFont().deriveFont(cbVisible.getFont().getStyle() & ~Font.BOLD, cbVisible.getFont().getSize()-1));
        cbVisible.setMnemonic('v');
        cbVisible.setText("Görünür");

        GroupLayout pnlCenterLayout = new GroupLayout(pnlCenter);
        pnlCenter.setLayout(pnlCenterLayout);
        pnlCenterLayout.setHorizontalGroup(
            pnlCenterLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(pnlCenterLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCenterLayout.createParallelGroup(Alignment.TRAILING)
                    .addComponent(lblPath)
                    .addComponent(lblIcon)
                    .addComponent(lblComment)
                    .addComponent(lblCommand)
                    .addComponent(lblName)
                    .addComponent(lblCategories))
                .addPreferredGap(ComponentPlacement.UNRELATED)
                .addGroup(pnlCenterLayout.createParallelGroup(Alignment.LEADING)
                    .addComponent(cbVisible)
                    .addComponent(txtName, GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                    .addGroup(Alignment.TRAILING, pnlCenterLayout.createSequentialGroup()
                        .addComponent(txtCommand, GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(btnBrowseCommand))
                    .addComponent(txtComment, GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                    .addGroup(Alignment.TRAILING, pnlCenterLayout.createSequentialGroup()
                        .addComponent(txtIcon, GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(btnBrowseIcon))
                    .addComponent(cbCategories, 0, 388, Short.MAX_VALUE)
                    .addComponent(txtPath, GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlCenterLayout.setVerticalGroup(
            pnlCenterLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(pnlCenterLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCenterLayout.createParallelGroup(Alignment.BASELINE)
                    .addComponent(lblPath)
                    .addComponent(txtPath, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlCenterLayout.createParallelGroup(Alignment.BASELINE)
                    .addComponent(lblCategories)
                    .addComponent(cbCategories, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlCenterLayout.createParallelGroup(Alignment.TRAILING)
                    .addComponent(lblName)
                    .addComponent(txtName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlCenterLayout.createParallelGroup(Alignment.BASELINE)
                    .addComponent(lblCommand)
                    .addComponent(txtCommand, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBrowseCommand))
                .addGap(18, 18, 18)
                .addGroup(pnlCenterLayout.createParallelGroup(Alignment.BASELINE)
                    .addComponent(lblComment)
                    .addComponent(txtComment, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlCenterLayout.createParallelGroup(Alignment.BASELINE)
                    .addComponent(lblIcon)
                    .addComponent(txtIcon, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBrowseIcon))
                .addPreferredGap(ComponentPlacement.UNRELATED)
                .addComponent(cbVisible)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        getContentPane().add(pnlCenter, BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        parent = null;
        dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnOkActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        if (newItem) {
            processNewItem();
        } else {
            processEditItem();
        }
    }//GEN-LAST:event_btnOkActionPerformed

    private void txtNameKeyTyped(KeyEvent evt) {//GEN-FIRST:event_txtNameKeyTyped
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                if (newItem) {
                    txtPath.setText(Configuration.getAppsFolder() + "/" + FileUtil.getFileName(txtName.getText().trim()));
                }
                checkBtnOk();
            }
        });
    }//GEN-LAST:event_txtNameKeyTyped

    private void formComponentShown(ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        if (!newItem) {
            setTitle( temporary.getName() + "menüsü düzenleniyor " );
            if (temporary.isReadOnly()) {
                btnViewCode.setText("Orijinal kodu görüntüleyin");
            }
            updateGui();
        } else {
            setTitle("Yeni menü öğesi");
            readyForNew();
        }
        checkBtnOk();
    }//GEN-LAST:event_formComponentShown

    private void btnBrowseCommandActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnBrowseCommandActionPerformed
        JFileChooser fc = new JFileChooser("/usr/local/bin");

        setFileChooserFont(fc.getComponents());
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fc.setFileFilter(new FileFilter() {

            @Override
            public boolean accept(File f) {
                return true;
            }

            @Override
            public String getDescription() {
                return "Tüm Dosyalar [*.*]";
            }
        });

        fc.setAcceptAllFileFilterUsed(false);
        fc.setDialogTitle("Bu öğe için çalışma dosyası seçin");

        int returnVal = fc.showDialog(this, "Tamam");

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            txtCommand.setText(fc.getSelectedFile().getAbsolutePath());
            checkBtnOk();
        }

        fc.setSelectedFile(null);
        checkBtnOk();
    }//GEN-LAST:event_btnBrowseCommandActionPerformed

    private void btnBrowseIconActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnBrowseIconActionPerformed
        File f = new File(txtIcon.getText().trim()).getParentFile();

        if (f == null) {
            f = new File("/usr/local/share/icons");
            if (!f.isDirectory()) {
                f = new File("/usr/share/icons");
            }
        }
        JFileChooser fc = new JFileChooser(f);

        setFileChooserFont(fc.getComponents());
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fc.setFileFilter(new FileNameExtensionFilter("Resim Dosyası [*.png, *.jpg, *.gif, *.xpm, *.svg]", "png", "xpm", "svg", "jpg", "gif"));
        fc.setAccessory(new ImagePreview(fc));

        fc.setAcceptAllFileFilterUsed(false);
        fc.setDialogTitle("Bu öğe için simge dosyası seçin");

        int returnVal = fc.showDialog(this, "Tamam");

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            txtIcon.setText(fc.getSelectedFile().getAbsolutePath());
            checkBtnOk();
        }

        fc.setSelectedFile(null);
        updateImage();
    }//GEN-LAST:event_btnBrowseIconActionPerformed

    private void txtCommandKeyTyped(KeyEvent evt) {//GEN-FIRST:event_txtCommandKeyTyped
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                checkBtnOk();
            }
        });
    }//GEN-LAST:event_txtCommandKeyTyped

    private void btnViewCodeActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnViewCodeActionPerformed
        new CodeEditDialog((Frame) getParent(), temporary, !temporary.isReadOnly(), this).setVisible(true);
    }//GEN-LAST:event_btnViewCodeActionPerformed

    private void txtIconKeyTyped(KeyEvent evt) {//GEN-FIRST:event_txtIconKeyTyped
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                updateImage();
            }
        });
    }//GEN-LAST:event_txtIconKeyTyped
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton btnBrowseCommand;
    private JButton btnBrowseIcon;
    private JButton btnCancel;
    private JButton btnOk;
    private JButton btnViewCode;
    private JComboBox cbCategories;
    private JCheckBox cbVisible;
    private JLabel lblCategories;
    private JLabel lblCommand;
    private JLabel lblComment;
    private JLabel lblIcon;
    private JLabel lblImage;
    private JLabel lblName;
    private JLabel lblPath;
    private JPanel pnlCenter;
    private JPanel pnlControls;
    private JPanel pnlIcon;
    private JSeparator sepSouth;
    private JTextField txtCommand;
    private JTextField txtComment;
    private JTextField txtIcon;
    private JTextField txtName;
    private JTextField txtPath;
    // End of variables declaration//GEN-END:variables
}
