/*
 * Copyright (c) 2016 Fyzxs
 *
 * Licensed under The MIT License (MIT)
 */

package com.quantityandconversion.log;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.util.Log;


import java.util.Locale;

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
    /**
     * This is the tag prefix that will be displayed in the tag component of the {@link Log#v(String, String) Android Log} methods
     */
    private static final String TAG_PREFIX = "FYZ:";

    /**
     * Write Log Message to {@link System#out#println}
     */
    @VisibleForTesting
    public static boolean doPrint = false;

    /**
     * Controls the level of logging.
     * This can be configured during build to limit logging messages to {@link Log#ERROR} or other levels.
     * Default value is {@link Log#VERBOSE the lowest setting}
     */
    @VisibleForTesting
    public static int logLevel = Log.VERBOSE;


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
        if (doPrint) {
            Logger.SystemOut.println(Log.VERBOSE, logLevel, msgFormat, args);
        } else {
            log(Log.VERBOSE, msgFormat, args);
        }
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
        if (doPrint) {
            Logger.SystemOut.println(Log.DEBUG, logLevel, msgFormat, args);
        } else {
            log(Log.DEBUG, msgFormat, args);
        }
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
        if (doPrint) {
            Logger.SystemOut.println(Log.INFO, logLevel, msgFormat, args);
        } else {
            log(Log.INFO, msgFormat, args);
        }
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
        if (doPrint) {
            Logger.SystemOut.println(Log.WARN, logLevel, msgFormat, args);
        } else {
            log(Log.WARN, msgFormat, args);
        }
    }
    //endregion

    //region error

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
        if (doPrint) {
            Logger.SystemOut.println(Log.ERROR, logLevel, msgFormat, args);
        } else {
            log(Log.ERROR, msgFormat, args);
        }
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
        if (doPrint) {
            Logger.SystemOut.println(Log.ASSERT, logLevel, msgFormat, args);
        } else {
            log(Log.ASSERT, msgFormat, args);
        }
    }

    /**
     * IFF the logLevel is high enough, logs to Android.
     * <p>
     * This will drop a null msg.
     *
     * @param level     The invoking level of logging
     * @param msgFormat the message format string
     * @param args      the args to format in
     */
    private static void log(final int level, final String msgFormat, final Object... args) {
        if (level >= logLevel && msgFormat != null) {
            final StackTraceElement frame = getCallingStackTraceElement();
            final String tag = createTag(frame);
            final String msg = String.format(Locale.US, msgFormat, args);
            final String message = createMessage(frame, msg);

            switch (level) {
                case Log.VERBOSE:
                    Log.v(tag, message);
                    break;
                case Log.DEBUG:
                    Log.d(tag, message);
                    break;
                case Log.INFO:
                    Log.i(tag, message);
                    break;
                case Log.WARN:
                    Log.w(tag, message);
                    break;
                case Log.ERROR:
                    Log.e(tag, message);
                    break;
                case Log.ASSERT:
                    Log.wtf(tag, message);
                    break;
            }
        }
    }

    /**
     * Creates the tag to be used
     *
     * @param frame {@link StackTraceElement} to build the tag from
     * @return Tag to use for the logged message
     */
    private static String createTag(final StackTraceElement frame) {
        final String fullClassName = frame.getClassName();
        final String className = fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
        return TAG_PREFIX + className;
    }

    /**
     * Builds the final message to be logged.
     * The format will be
     * InvokingMethodName [currentThreadName] providedMessage
     *
     * @param frame The {@link StackTraceElement} to pull the method name from
     * @param msg   the message to prefix the method name to
     * @return The final message string
     */
    private static String createMessage(final StackTraceElement frame, final String msg) {
        return String.format(Locale.US,
                "[%s] %s : %s",
                Thread.currentThread().getName(),
                frame.getMethodName(),
                msg);
    }

    /**
     * Gets the {@link StackTraceElement} for the method that invoked logging
     *
     * @return {@link StackTraceElement} for the caller into {@link FyzLog}
     */
    private static StackTraceElement getCallingStackTraceElement() {
        boolean hitLogger = false;
        for (final StackTraceElement ste : Thread.currentThread().getStackTrace()) {
            final boolean isLogger = ste.getClassName().startsWith(FyzLog.class.getName());
            hitLogger = hitLogger || isLogger;
            if (hitLogger && !isLogger) {
                return ste;
            }
        }
        return new StackTraceElement(FyzLog.class.getName(),
                "getCallingStackTraceElement",
                null,
                -1);
    }
}