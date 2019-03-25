package com.github.bigibas123.twitchmodpackdownloader.util;

import com.github.bigibas123.twitchmodpackdownloader.Log;
import com.github.bigibas123.twitchmodpackdownloader.storage.ModPack;

import java.io.IOException;
import java.net.URL;
import java.nio.file.*;

public class ModUtils {

    public static Path getMod(ModPack.Mod mod){
        //https://minecraft.curseforge.com/projects/morpheus/files/2664449
        Path location = Paths.get(System.getProperty("user.dir"), "downloads", "files", Long.toString(mod.getProjectID()), Long.toString(mod.getFileID()));
        if(location.toFile().exists() && location.toFile().isDirectory()) {
            try {
                return Files.walk(location,1, FileVisitOption.FOLLOW_LINKS)
                        .filter(Files :: isRegularFile)
                        .findFirst().get();
            } catch (IOException e) {
                Log.printException(e);
            }
        }
        location.toFile().mkdirs();
        location.toFile().mkdir();
        try {
            String url = WebUtils.resolveURL("https://minecraft.curseforge.com/projects/"+mod.getProjectID()+"/files/"+mod.getFileID()+"/download");
            Log.getLog().debug("Downloading:"+url);
            URL url_obj = new URL(url);
            String fileName = url.substring(url.lastIndexOf('/') + 1);
            Path out = location.resolve(fileName);
            Files.copy(url_obj.openStream(),out, StandardCopyOption.REPLACE_EXISTING);
            return out;
        } catch (IOException e) {
            Log.printException(e);
        }
        return null;
    }
}
