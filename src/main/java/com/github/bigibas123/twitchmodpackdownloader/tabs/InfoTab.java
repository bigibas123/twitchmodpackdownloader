package com.github.bigibas123.twitchmodpackdownloader.tabs;

import com.github.bigibas123.twitchmodpackdownloader.TwitchModPackDownloader;
import com.github.bigibas123.twitchmodpackdownloader.TabClass;
import com.github.bigibas123.twitchmodpackdownloader.storage.ModPack;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import java.awt.*;

public class InfoTab extends TabClass {

    private TreeModel treeModel;
    private DefaultMutableTreeNode top;
    private JTree tree;


    public InfoTab() {
        super("Info");
    }

    private void updateTreeModel() {
        if(this.treeModel == null) {
            this.top = new DefaultMutableTreeNode();
            for (ModPack mp : TwitchModPackDownloader.storage.getAvailable()) {
                this.top.add(mp.getTree());
            }
            this.treeModel = new DefaultTreeModel(this.top);
        }else {
            this.top.removeAllChildren();
            for (ModPack mp : TwitchModPackDownloader.storage.getAvailable()) {
                this.top.add(mp.getTree());
            }
        }
    }

    public TreeModel getTreeModel() {
        if (this.treeModel == null) {
            updateTreeModel();
        }
        return this.treeModel;
    }

    @Override
    public void createComponent() {
        GridBagLayout layout = new GridBagLayout();
        this.pane.setLayout(layout);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;

        JButton addbutton = new JButton("Refresh");
        constraints.gridx = 0;
        constraints.gridwidth = 2;
        constraints.gridheight = 1;
        constraints.weighty=0.1;
        constraints.weightx=0.1;
        addbutton.addActionListener(actionEvent -> {
            updateTreeModel();
            this.tree.updateUI();
        });
        pane.add(addbutton, constraints);

        this.tree = new JTree(this.getTreeModel());

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 5;
        constraints.gridheight = 4;
        constraints.weightx = 0.9;
        constraints.weighty = 0.9;
        this.pane.add(new JScrollPane(this.tree), constraints);



    }
}
