package com.quantityandconversion.log;

import android.util.Log;

import java.util.Locale;

/* package */ class Logger {
    private static final String TAG_PREFIX = "FYZ:";
    /* package */ static final Logger SystemOut = new Logger();

    private Logger(){}

    /* package */ void println(final LogLevel level, final LogLevel logLevel, final String msgFormat, final Object... args) {
        if(msgFormat == null) throw new IllegalArgumentException("FyzLog message can not be null");

        final StackTraceElement frame = getCallingStackTraceElement();
        final String output =
                level.tag() + "@" + logLevel.tag() + "/ " +
                        createTag(frame) + " " +
                        createMessage(frame, String.format(Locale.US, msgFormat, args));
        System.out.println(output);
    }

    /* package */ static void log(final LogLevel level, final LogLevel logLevel, final String msgFormat, final Object... args) {
        if (level.logAt(logLevel) && msgFormat != null) {
            final StackTraceElement frame = getCallingStackTraceElement();
            final String tag = createTag(frame);
            final String msg = String.format(Locale.US, msgFormat, args);
            final String message = createMessage(frame, msg);

            if(level == LogLevel.VERBOSE)
                Log.v(tag, message);
            else if(level == LogLevel.DEBUG)
                Log.d(tag, message);
            else if(level == LogLevel.INFO)
                Log.i(tag, message);
            else if(level == LogLevel.WARN)
                Log.w(tag, message);
            else if(level == LogLevel.ERROR)
                Log.e(tag, message);
            else if(level == LogLevel.ASSERT)
                Log.wtf(tag, message);
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
