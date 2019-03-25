package com.github.bigibas123.twitchmodpackdownloader.util;

import com.github.bigibas123.twitchmodpackdownloader.TwitchModPackDownloader;
import com.github.bigibas123.twitchmodpackdownloader.Log;
import com.github.bigibas123.twitchmodpackdownloader.storage.ModPack;
import com.github.bigibas123.twitchmodpackdownloader.tabs.InstallTab;

import javax.swing.table.DefaultTableModel;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class DataUtils {


    private static Path downloadModpackInfo(String url_in) {

        Path pack = null;
        try {

            String location = WebUtils.resolveURL(url_in);
            String fileName = location.substring(location.lastIndexOf('/') + 1);
            URL url_obj = new URL(location);
            Log.getLog().debug("Downloading:" + location);
            BufferedInputStream in = new BufferedInputStream(url_obj.openStream());
            Path out = Paths.get(System.getProperty("user.dir"), "downloads", fileName);
            out.toFile().getParentFile().mkdirs();
            out.toFile().createNewFile();
            Files.copy(in, out, StandardCopyOption.REPLACE_EXISTING);
            pack = out;

        } catch (MalformedURLException e) {
            Log.getLog().error("The url:\"" + url_in + "\" is in the wrong format");
            Log.printException(e);

        } catch (IOException e) {
            Log.printException(e);
        }
        return pack;
    }

    private static Path unzip(Path zipFile) {
        Path dest = null;
        try {
            String name = zipFile.getFileName().toString();
            name = name.substring(0, name.length() - 4);
            dest = zipFile.getParent().resolve(name);
            Log.getLog().debug("Unzipping");
            ZipFile zip = new ZipFile(zipFile.toFile());
            Enumeration<? extends ZipEntry> entries = zip.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                if (entry.isDirectory()) {
                    Log.getLog().trace("dir:" + entry.getName());
                    dest.resolve(entry.getName()).toFile().mkdirs();
                } else {
                    Log.getLog().trace("file:" + entry.getName());
                    Path out = dest.resolve(entry.getName());
                    out.getParent().toFile().mkdirs();
                    out.toFile().createNewFile();
                    Files.copy(zip.getInputStream(entry), out, StandardCopyOption.REPLACE_EXISTING);
                }
            }


        } catch (IOException e) {
            Log.printException(e);
        }
        return dest;
    }

    private static ModPack parseModPack(Path dir) {
        ModPack pack = null;
        Log.getLog().debug("Reading JSON");
        File file = dir.resolve("manifest.json").toFile();
        if (file.exists() && file.canRead()) {
            try (FileInputStream stream = new FileInputStream(file)) {
                pack = JsonUtils.readJsonStreamObject(stream, ModPack.class);
                Log.getLog().debug("Set extraction dir to:" + dir.toString());
                pack.setDownloadfolder(dir.toString());

            } catch (IOException e) {
                Log.printException(e);
            }
        }

        return pack;
    }

    public static void addPack(String url, DefaultTableModel tableModel) {
        Path zip = downloadModpackInfo(url);
        if (zip == null) return;
        Path dir = unzip(zip);
        if (dir == null) return;
        ModPack pack = parseModPack(dir);
        TwitchModPackDownloader.storage.addAvailableModpack(pack);
        Log.getLog().info("Added pack:" + pack.getName());
        tableModel.addRow(pack.toRow());
        ((InstallTab) TwitchModPackDownloader.tabs[1]).addModpack(pack);
    }
}
