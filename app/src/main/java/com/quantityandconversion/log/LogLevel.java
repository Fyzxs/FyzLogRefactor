package com.quantityandconversion.log;

import android.util.Log;

/* package */ abstract class LogLevel {

    /* package */ final static LogLevel VERBOSE = new LogLevel(Log.VERBOSE, "V") {
        @Override /* package */ void log(final String logTag, final String msg) {
            Log.v(logTag, msg);
        }
    };
    /* package */ final static LogLevel DEBUG = new LogLevel(Log.DEBUG, "D") {
        @Override /* package */ void log(final String logTag, final String msg) {
            Log.d(logTag, msg);
        }
    };
    /* package */ final static LogLevel INFO = new LogLevel(Log.INFO, "I") {
        @Override /* package */ void log(final String logTag, final String msg) {
            Log.i(logTag, msg);
        }
    };
    /* package */ final static LogLevel WARN = new LogLevel(Log.WARN, "W") {
        @Override /* package */ void log(final String logTag, final String msg) {
            Log.w(logTag, msg);
        }
    };
    /* package */ final static LogLevel ERROR = new LogLevel(Log.ERROR, "E") {
        @Override /* package */ void log(final String logTag, final String msg) {
            Log.e(logTag, msg);
        }
    };
    /* package */ final static LogLevel ASSERT = new LogLevel(Log.ASSERT, "WTF") {
        @Override /* package */ void log(final String logTag, final String msg) {
            Log.wtf(logTag, msg);
        }
    };

    private final int level;
    private final String levelTag;

    private LogLevel(final int level, final String levelTag) {
        this.level = level;
        this.levelTag = levelTag;
    }

    /* package */
    abstract void log(final String logTag, final String msg);

    /* package */ String tag() {
        return levelTag;
    }

    /* package */ boolean logAt(final LogLevel other) {
        return this.level >= other.level;
    }
}