package com.github.bigibas123.twitchmodpackdownloader.util;

import com.github.bigibas123.twitchmodpackdownloader.Log;
import com.github.bigibas123.twitchmodpackdownloader.storage.Storage;
import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class StorageLoader {

    public final Gson gson = new Gson();

    public Storage loadStorage() {
        File file = new File(System.getProperty("user.dir"), "storage.json");
        Storage storage = new Storage();
        if (file.exists() && file.canRead()) {
            try (FileInputStream stream = new FileInputStream(file)) {
                storage = JsonUtils.readJsonStreamArray(stream, Storage.class).get(0);
            } catch (IOException e) {
                Log.getLog().error("Could not load file:");
                Log.printException(e);
            }
        }
        return storage;
    }

    public void saveStorage(Storage storage) {
        File file = new File(System.getProperty("user.dir"), "storage.json");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            JsonWriter writer = new JsonWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
            writer.setIndent("    ");
            writer.beginArray();
            gson.toJson(storage, Storage.class, writer);
            writer.endArray();
            writer.close();
        } catch (IOException e) {
            Log.getLog().error("Could not save file:");
            Log.printException(e);
        }
    }


}
