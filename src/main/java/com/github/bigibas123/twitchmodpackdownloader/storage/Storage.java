package com.github.bigibas123.twitchmodpackdownloader.storage;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;

@AllArgsConstructor
@Getter
public class Storage {

    private final ArrayList<ModPack> available;

    public Storage() {
        available = new ArrayList<>();
    }

    public void addAvailableModpack(ModPack mp) {
        this.available.add(mp);
    }
}
