package com.github.bigibas123.twitchmodpackdownloader.tabs;

import com.github.bigibas123.twitchmodpackdownloader.TwitchModPackDownloader;
import com.github.bigibas123.twitchmodpackdownloader.Log;
import com.github.bigibas123.twitchmodpackdownloader.TabClass;
import com.github.bigibas123.twitchmodpackdownloader.storage.ModPack;
import com.github.bigibas123.twitchmodpackdownloader.util.CreationUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InstallTab extends TabClass {

    private int y;

    public InstallTab() {
        super("Install");
    }

    public void addModpack(ModPack pack){
        this.createMPR(pack);
        this.pane.updateUI();
    }

    private void createMPR(ModPack mp){
        JPanel modPackRow = new JPanel();
        modPackRow.setLayout(new FlowLayout());

        modPackRow.add(new JLabel(mp.getName()));
        modPackRow.add(new JLabel(mp.getVersion()));
        Button button = new Button("Create");
        button.addActionListener(new CreateAL(mp));
        modPackRow.add(button);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = y;
        constraints.weightx = 0.1;
        constraints.weighty = 0.1;
        constraints.gridheight = 1;
        constraints.gridwidth = 6;
        this.pane.add(modPackRow,constraints);
        this.y++;
    }

    @Override
    public void createComponent() {
        this.y=0;

        for(ModPack mp: TwitchModPackDownloader.storage.getAvailable()){
            this.createMPR(mp);
        }
    }

    private class CreateAL implements ActionListener {
        private final ModPack mp;

        public CreateAL(ModPack mp) {
            this.mp = mp;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Log.getLog().debug("Installing:"+mp.getName()+" "+mp.getVersion());

            new Thread(() -> CreationUtils.createInstanceDir(this.mp)).start();
        }
    }
}
