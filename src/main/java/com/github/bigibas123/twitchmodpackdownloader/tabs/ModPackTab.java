package com.github.bigibas123.twitchmodpackdownloader.tabs;

import com.github.bigibas123.twitchmodpackdownloader.TwitchModPackDownloader;
import com.github.bigibas123.twitchmodpackdownloader.TabClass;
import com.github.bigibas123.twitchmodpackdownloader.storage.ModPack;
import com.github.bigibas123.twitchmodpackdownloader.util.DataUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ModPackTab extends TabClass {

    public ModPackTab() {
        super("Modpacks");
    }

    @Override
    public void createComponent() {
        GridBagLayout layout = new GridBagLayout();
        pane.setLayout(layout);


        JTextField downloadField = new JTextField(500);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0.1;
        constraints.weighty = 0;
        constraints.gridheight = 1;
        constraints.gridwidth = 4;
        pane.add(downloadField, constraints);

        JButton addbutton = new JButton("Add");
        constraints.gridx = 4;
        constraints.gridwidth = 2;
        pane.add(addbutton, constraints);

        String[][] data = this.transformModpacks(TwitchModPackDownloader.storage.getAvailable());

        DefaultTableModel tableModel = new DefaultTableModel(data, new String[]{"Name", "Version", "Author", "Minecraft", "Mods"});
        addbutton.addActionListener(new AddButtonActionListener(tableModel, downloadField));
        JTable table = new JTable(tableModel);
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 6;
        constraints.weighty = 1;
        pane.add(new JScrollPane(table), constraints);
    }

    private String[][] transformModpacks(ArrayList<ModPack> packs) {
        ArrayList<String[]> out = new ArrayList<>();
        for (ModPack pack : packs) {
            out.add(pack.toRow());
        }

        return out.toArray(new String[0][0]);
    }

    private class AddButtonActionListener implements ActionListener {
        private final DefaultTableModel tableModel;
        private final JTextField downloadField;

        public AddButtonActionListener(DefaultTableModel tableModel, JTextField downloadField) {
            this.tableModel = tableModel;
            this.downloadField = downloadField;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            new Thread(() -> DataUtils.addPack(downloadField.getText(), tableModel)).start();
        }
    }
}
