package com.lmmmowi.mowikit.log;

/**
 * @Author: mowi
 * @Date: 2019-05-23
 * @Description:
 */
public interface Logger {

    void trace(String msg);

    void trace(Throwable throwable);

    void trace(String msg, Throwable throwable);

    void debug(String msg);

    void debug(Throwable throwable);

    void debug(String msg, Throwable throwable);

    void info(String msg);

    void info(Throwable throwable);

    void info(String msg, Throwable throwable);

    void warn(String msg);

    void warn(Throwable throwable);

    void warn(String msg, Throwable throwable);

    void error(String msg);

    void error(Throwable throwable);

    void error(String msg, Throwable throwable);

    boolean isTraceEnabled();

    boolean isDebugEnabled();

    boolean isInfoEnabled();

    boolean isWarnEnabled();

    boolean isErrorEnabled();
}
