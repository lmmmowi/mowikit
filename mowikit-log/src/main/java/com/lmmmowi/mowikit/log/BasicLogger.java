package com.lmmmowi.mowikit.log;

/**
 * @Author: mowi
 * @Date: 2019-05-23
 * @Description:
 */
public class BasicLogger implements Logger {

    private LoggerAdapter loggerAdapter;

    public BasicLogger(LoggerAdapter loggerAdapter) {
        this.loggerAdapter = loggerAdapter;
    }

    public void trace(String msg) {
        loggerAdapter.trace(msg);
    }

    public void trace(Throwable throwable) {
        loggerAdapter.trace(throwable);
    }

    public void trace(String msg, Throwable throwable) {
        loggerAdapter.trace(msg, throwable);
    }

    public void debug(String msg) {
        loggerAdapter.debug(msg);
    }

    public void debug(Throwable throwable) {
        loggerAdapter.debug(throwable);
    }

    public void debug(String msg, Throwable throwable) {
        loggerAdapter.debug(msg, throwable);
    }

    public void info(String msg) {
        loggerAdapter.info(msg);
    }

    public void info(Throwable throwable) {
        loggerAdapter.info(null, throwable);
    }

    public void info(String msg, Throwable throwable) {
        loggerAdapter.info(msg, throwable);
    }

    public void warn(String msg) {
        loggerAdapter.warn(msg);
    }

    public void warn(Throwable throwable) {
        loggerAdapter.warn(null, throwable);
    }

    public void warn(String msg, Throwable throwable) {
        loggerAdapter.warn(msg, throwable);
    }

    public void error(String msg) {
        loggerAdapter.error(msg);
    }

    public void error(Throwable throwable) {
        loggerAdapter.error(null, throwable);
    }

    public void error(String msg, Throwable throwable) {
        loggerAdapter.error(msg, throwable);
    }

    public boolean isTraceEnabled() {
        return loggerAdapter.isTraceEnabled();
    }

    public boolean isDebugEnabled() {
        return loggerAdapter.isDebugEnabled();
    }

    public boolean isInfoEnabled() {
        return loggerAdapter.isInfoEnabled();
    }

    public boolean isWarnEnabled() {
        return loggerAdapter.isWarnEnabled();
    }

    public boolean isErrorEnabled() {
        return loggerAdapter.isErrorEnabled();
    }
}
