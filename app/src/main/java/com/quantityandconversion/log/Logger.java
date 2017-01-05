package com.quantityandconversion.log;

import android.util.Log;

import java.util.Locale;

/* package */ class Logger {
    private static final String TAG_PREFIX = "FYZ:";
    /* package */ static final Logger SystemOut = new Logger();

    private Logger(){}

    /* package */ void println(final int level, final int logLevel, final String msgFormat, final Object... args) {
        if(msgFormat == null) throw new IllegalArgumentException("FyzLog message can not be null");

        final StackTraceElement frame = getCallingStackTraceElement();
        final String output =
                getLevelTag(level) + "@" + getLevelTag(logLevel) + "/ " +
                        createTag(frame) + " " +
                        createMessage(frame, String.format(Locale.US, msgFormat, args));
        System.out.println(output);
    }
    private static String getLevelTag(final int level) {
        switch (level) {
            case Log.VERBOSE:
                return "V";
            case Log.DEBUG:
                return "D";
            case Log.INFO:
                return "I";
            case Log.WARN:
                return "W";
            case Log.ERROR:
                return "E";
            case Log.ASSERT:
                return "WTF";
            default:
                return "?";
        }
    }

    private static String createTag(final StackTraceElement frame) {
        final String fullClassName = frame.getClassName();
        final String className = fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
        return TAG_PREFIX + className;
    }

    private static String createMessage(final StackTraceElement frame, final String msg) {
        return String.format(Locale.US,
                "[%s] %s : %s",
                Thread.currentThread().getName(),
                frame.getMethodName(),
                msg);
    }

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
