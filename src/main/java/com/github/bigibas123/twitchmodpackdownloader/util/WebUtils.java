package com.github.bigibas123.twitchmodpackdownloader.util;

import com.github.bigibas123.twitchmodpackdownloader.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebUtils {

    public static String resolveURL(String source_url) throws IOException {
        String location = source_url;
        HttpURLConnection connection;
        try {
            for (; ; ) {
                URL url = new URL(location);
                connection = (HttpURLConnection) url.openConnection();
                connection.setInstanceFollowRedirects(false);
                Log.getLog().trace("Requesting:" + location);
                String redirectLocation = connection.getHeaderField("Location");
                if (redirectLocation == null) {
                    break;
                } else {
                    Log.getLog().trace("Redirect:" + redirectLocation);
                    if(redirectLocation.startsWith("http")) {
                        location = redirectLocation;
                    }else if(redirectLocation.startsWith("/")){
                        location = url.getProtocol()+"://"+url.getHost()+redirectLocation;
                    }else {
                        location = redirectLocation;
                    }
                }

            }
            return location;
        } catch (IOException e) {
            Log.printException(e);
            throw e;
        }
    }
}
