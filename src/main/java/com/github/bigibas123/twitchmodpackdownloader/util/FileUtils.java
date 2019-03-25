package com.github.bigibas123.twitchmodpackdownloader.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {

    public static void copyFolder(Path src, Path dest) throws IOException {
        Files.walk(src).forEach(path -> {
            try {
                Path fd = dest.resolve(src.relativize(path));
                fd.getParent().toFile().mkdirs();
                Files.copy(path,fd);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
