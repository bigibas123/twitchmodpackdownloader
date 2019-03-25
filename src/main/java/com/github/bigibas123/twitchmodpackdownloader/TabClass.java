package com.github.bigibas123.twitchmodpackdownloader;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;

public abstract class TabClass {

    protected JPanel pane;
    @Getter
    private final String tabName;

    protected TabClass(String name) {
        this.tabName = name;
    }

    public abstract void createComponent();

    public Component getComponent() {
        if(pane==null){
            this.pane= new JPanel();
            createComponent();
        }
        return pane;
    }
}
