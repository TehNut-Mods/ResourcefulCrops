package tehnut.resourceful.crops.util.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tehnut.resourceful.crops.ConfigHandler;
import tehnut.resourceful.crops.ModInformation;

public class LogHelper {

    private static Logger logger = LogManager.getLogger(ModInformation.NAME);

    /**
     * @param info - String to log to the info level
     */

    public static void info(Object info) {
        if (ConfigHandler.enableConsoleLogging)
            logger.info(info);
    }

    /**
     * @param error - String to log to the error level
     */

    public static void error(Object error) {
        if (ConfigHandler.enableConsoleLogging)
            logger.error(error);
    }

    /**
     * @param debug - String to log to the debug level
     */

    public static void debug(Object debug) {
        if (ConfigHandler.enableConsoleLogging)
            logger.debug(debug);
    }
}
