package com.github.bigibas123.twitchmodpackdownloader;

import com.github.bigibas123.twitchmodpackdownloader.storage.Storage;
import com.github.bigibas123.twitchmodpackdownloader.tabs.InstallTab;
import com.github.bigibas123.twitchmodpackdownloader.tabs.ModPackTab;
import com.github.bigibas123.twitchmodpackdownloader.tabs.InfoTab;
import com.github.bigibas123.twitchmodpackdownloader.util.StorageLoader;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


public class TwitchModPackDownloader {



    public static final TabClass[] tabs = {new ModPackTab(), new InstallTab(), new InfoTab()};

    public static Storage storage;

    private static StorageLoader sl;

    public static void main(String[] args) {

        sl = new StorageLoader();
        storage = sl.loadStorage();

        JFrame mainwindow = new JFrame("Launcher");
        mainwindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainwindow.addWindowListener(new WL());
        JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.TOP);
        for (TabClass tab : tabs) {
            tabbedPane.addTab(tab.getTabName(), tab.getComponent());
        }
        mainwindow.add(tabbedPane);

        mainwindow.pack();
        mainwindow.setVisible(true);

    }

    private static class WL implements WindowListener {
        @Override
        public void windowOpened(WindowEvent windowEvent) {

        }

        @Override
        public void windowClosing(WindowEvent windowEvent) {
            sl.saveStorage(storage);
        }

        @Override
        public void windowClosed(WindowEvent windowEvent) {

        }

        @Override
        public void windowIconified(WindowEvent windowEvent) {

        }

        @Override
        public void windowDeiconified(WindowEvent windowEvent) {

        }

        @Override
        public void windowActivated(WindowEvent windowEvent) {

        }

        @Override
        public void windowDeactivated(WindowEvent windowEvent) {

        }
    }
}
