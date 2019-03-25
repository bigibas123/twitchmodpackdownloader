package com.github.bigibas123.twitchmodpackdownloader.util;

import com.github.bigibas123.twitchmodpackdownloader.Log;
import com.github.bigibas123.twitchmodpackdownloader.storage.ModPack;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class CreationUtils {

    public static void createInstanceDir(ModPack pack) {
        Path packfolder = Path.of(pack.getDownloadfolder());
        Path dest_dir = Paths.get(System.getProperty("user.dir"), packfolder.toFile().getName());
        try {
            FileUtils.copyFolder(packfolder.resolve("overrides"),dest_dir);
            ArrayList<Runnable> threads = new ArrayList<>();
            Path moddir = dest_dir.resolve("mods");
            moddir.toFile().mkdirs();

            for(ModPack.Mod mod:pack.getFiles()){
                threads.add(() -> {
                    Log.getLog().debug("Getting mod:" + mod.toString());
                    Path modpath = ModUtils.getMod(mod);
                    if (modpath == null) {
                        Log.getLog().debug("Modpath for mod:" + mod.toString() + " is null");
                        return;
                    }
                    String modfilename = modpath.getFileName().toString();
                    try {
                        Files.copy(modpath, moddir.resolve(modfilename));
                        Log.getLog().debug("Got mod:" + modfilename + " " + mod.toString());
                    } catch (IOException e) {
                        Log.printException(e);
                    }
                });
            }
            ThreadUtils.dosedRunAwait(threads,10);
            Log.getLog().info("Done creating instance");
        } catch (IOException e) {
            Log.printException(e);
        }
    }
}
