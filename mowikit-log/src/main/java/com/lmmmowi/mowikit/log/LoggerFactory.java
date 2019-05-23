package com.lmmmowi.mowikit.log;

import com.lmmmowi.mowikit.log.adapter.Slf4jLoggerAdapter;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: mowi
 * @Date: 2019-05-23
 * @Description:
 */
public class LoggerFactory {

    private static final Map<String, Logger> LOGGER_MAP = new HashMap<String, Logger>();

    public static Logger getLogger(String key) {
        if (LOGGER_MAP.get(key) == null) {
            synchronized (LOGGER_MAP) {
                if (LOGGER_MAP.get(key) == null) {
                    Logger logger = createBasicLogger(key);
                    LOGGER_MAP.put(key, logger);
                }
            }
        }
        return LOGGER_MAP.get(key);
    }

    public static Logger getLogger(Class key) {
        return getLogger(key.getName());
    }

    private static Logger createBasicLogger(String key) {
        LoggerAdapter loggerAdapter = new Slf4jLoggerAdapter(key);
        return new BasicLogger(loggerAdapter);
    }
}
