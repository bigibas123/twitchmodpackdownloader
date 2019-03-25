package com.github.bigibas123.twitchmodpackdownloader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log {

    private static final Logger logger = LogManager.getLogger(TwitchModPackDownloader.class.getSimpleName());

    public static Logger getLog(){
        return logger;
    }

    public static void printException(Throwable t){
        logger.error(t.getLocalizedMessage());
        logger.error(t.getMessage());
        t.printStackTrace();
    }

}
