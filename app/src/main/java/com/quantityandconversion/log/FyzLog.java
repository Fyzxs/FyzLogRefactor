/*
 * Copyright (c) 2016 Fyzxs
 *
 * Licensed under The MIT License (MIT)
 */

package com.quantityandconversion.log;

import android.support.annotation.NonNull;
import android.util.Log;

/**
 * Fyzxs Log(ger)
 * <p>
 * This lightweight static class wraps the Android logger.
 * It allows customization of the logging process.
 * <p>
 * Most importantly, it allows control of the logging for
 * unit testing purposes.
 */
public final class FyzLog {
//Understands handling a user request to log

    private static Logger ActiveLogger = Logger.AndroidLog;
    private static LogLevel logLevel = LogLevel.VERBOSE;

    /* package */
    static void writeToSystem() {
        ActiveLogger = Logger.SystemOut;
    }

    /* package */
    static void writeToLog() {
        ActiveLogger = Logger.AndroidLog;
    }

    /* package */
    static void updateCurrentLogLevel(final LogLevel logLevel) {
        FyzLog.logLevel = logLevel;
    }


    /**
     * The {@link Log#VERBOSE} level logging
     *
     * @param msg The message to log
     */
    public static void v(@NonNull final String msg) {
        v(msg, (Object[]) null);
    }

    /**
     * The {@link Log#VERBOSE} level logging with string formatting
     *
     * @param msgFormat the format string
     * @param args      the args to format in
     */
    public static void v(@NonNull final String msgFormat, final Object... args) {
        ActiveLogger.log(LogLevel.VERBOSE, logLevel, msgFormat, args);
    }

    /**
     * The {@link Log#DEBUG} level logging
     *
     * @param msg The message to log
     */
    public static void d(@NonNull final String msg) {
        d(msg, (Object[]) null);
    }

    /**
     * The {@link Log#DEBUG} level logging with string formatting
     *
     * @param msgFormat the format string
     * @param args      the args to format in
     */
    public static void d(@NonNull final String msgFormat, final Object... args) {
        ActiveLogger.log(LogLevel.DEBUG, logLevel, msgFormat, args);
    }

    /**
     * The {@link Log#INFO} level logging
     *
     * @param msg The message to log
     */
    public static void i(@NonNull final String msg) {
        i(msg, (Object[]) null);
    }

    /**
     * The {@link Log#INFO} level logging with string formatting
     *
     * @param msgFormat the format string
     * @param args      the args to format in
     */
    public static void i(@NonNull final String msgFormat, final Object... args) {
        ActiveLogger.log(LogLevel.INFO, logLevel, msgFormat, args);
    }

    /**
     * The {@link Log#WARN} level logging
     *
     * @param msg The message to log
     */
    public static void w(@NonNull final String msg) {
        w(msg, (Object[]) null);
    }

    /**
     * The {@link Log#WARN} level logging with string formatting
     *
     * @param msgFormat the format string
     * @param args      the args to format in
     */
    public static void w(@NonNull final String msgFormat, final Object... args) {
        ActiveLogger.log(LogLevel.WARN, logLevel, msgFormat, args);
    }

    /**
     * The {@link Log#ERROR} level logging
     *
     * @param msg The message to log
     */
    public static void e(@NonNull final String msg) {
        e(msg, (Object[]) null);
    }

    /**
     * The {@link Log#ERROR} level logging with string formatting
     *
     * @param msgFormat the format string
     * @param args      the args to format in
     */
    public static void e(@NonNull final String msgFormat, final Object... args) {
        ActiveLogger.log(LogLevel.ERROR, logLevel, msgFormat, args);
    }

    /**
     * The {@link Log#ASSERT} level logging
     *
     * @param msg The message to log
     */
    public static void wtf(@NonNull final String msg) {
        wtf(msg, (Object[]) null);
    }

    /**
     * The {@link Log#ASSERT} level logging with string formatting
     *
     * @param msgFormat the format string
     * @param args      the args to format in
     */
    public static void wtf(@NonNull final String msgFormat, final Object... args) {
        ActiveLogger.log(LogLevel.ASSERT, logLevel, msgFormat, args);
    }
}