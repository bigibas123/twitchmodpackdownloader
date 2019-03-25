package com.github.bigibas123.twitchmodpackdownloader.util;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class JsonUtils {

    public static Gson gson = new Gson();

    public static <T> ArrayList<T> readJsonStreamArray(InputStream in, Class<T> type) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        ArrayList<T> storage = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            T message = gson.fromJson(reader, type);
            storage.add(message);
        }
        reader.endArray();
        reader.close();
        return storage;
    }

    public static <T> T readJsonStreamObject(InputStream in, Class<T> type) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        T storage;
        storage = gson.fromJson(reader, type);
        reader.close();
        return storage;
    }
}
