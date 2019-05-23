package com.lmmmowi.mowikit.log.adapter;

import com.lmmmowi.mowikit.log.LoggerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: mowi
 * @Date: 2019-05-23
 * @Description:
 */
public class Slf4jLoggerAdapter implements LoggerAdapter {

    private Logger logger;

    public Slf4jLoggerAdapter(String key) {
        logger = LoggerFactory.getLogger(key);
    }

    public void trace(String msg) {
        logger.trace(msg);
    }

    public void trace(Throwable throwable) {
        logger.trace(null, throwable);
    }

    public void trace(String msg, Throwable throwable) {
        logger.trace(msg, throwable);
    }

    public void debug(String msg) {
        logger.debug(msg);
    }

    public void debug(Throwable throwable) {
        logger.debug(null, throwable);
    }

    public void debug(String msg, Throwable throwable) {
        logger.debug(msg, throwable);
    }

    public void info(String msg) {
        logger.info(msg);
    }

    public void info(Throwable throwable) {
        logger.info(null, throwable);
    }

    public void info(String msg, Throwable throwable) {
        logger.info(msg, throwable);
    }

    public void warn(String msg) {
        logger.warn(msg);
    }

    public void warn(Throwable throwable) {
        logger.warn(null, throwable);
    }

    public void warn(String msg, Throwable throwable) {
        logger.warn(msg, throwable);
    }

    public void error(String msg) {
        logger.error(msg);
    }

    public void error(Throwable throwable) {
        logger.error(null, throwable);
    }

    public void error(String msg, Throwable throwable) {
        logger.error(msg, throwable);
    }

    public boolean isTraceEnabled() {
        return logger.isTraceEnabled();
    }

    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    public boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }

    public boolean isWarnEnabled() {
        return logger.isWarnEnabled();
    }

    public boolean isErrorEnabled() {
        return logger.isErrorEnabled();
    }
}
