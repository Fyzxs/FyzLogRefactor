package com.quantityandconversion.log;

import android.util.Log;

public class LogLevel {

    public final static LogLevel VERBOSE = new LogLevel(Log.VERBOSE, "V");
    public final static LogLevel DEBUG = new LogLevel(Log.DEBUG, "D");
    public final static LogLevel INFO = new LogLevel(Log.INFO, "I");
    public final static LogLevel WARN = new LogLevel(Log.WARN, "W");
    public final static LogLevel ERROR = new LogLevel(Log.ERROR, "E");
    public final static LogLevel ASSERT = new LogLevel(Log.ASSERT, "WTF");

    private final int level;
    private final String tag;

    public LogLevel(final int level, final String tag) {
        this.level = level;
        this.tag = tag;
    }

    public String tag() {
        return tag;
    }

    public boolean logAt(final LogLevel other) {
        return this.level >= other.level;
    }
}