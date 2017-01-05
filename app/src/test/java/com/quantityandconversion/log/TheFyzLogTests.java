/*
 * Copyright (c) 2016 Fyzxs
 *
 * Licensed under The MIT License (MIT)
 */

package com.quantityandconversion.log;

import android.util.Log;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.rules.ExpectedException;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


/**
 * !!! DO NOT USE THIS TEST CLASS AS AN EXAMPLE !!!
 * <p>
 * Predominate issues with this test class
 * <p>
 * + Prefixed with 'The'
 * A proper name for this class would be "FyzLogTests".
 * Doing so triggers a condition internal, which shouldn't be re-written to bypass unit tests.
 * <p>
 * + Using System.out
 * Don't do this.
 */
@SuppressWarnings("ConstantConditions")
public class TheFyzLogTests {

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * This MUST reflect the {@link FyzLog#getLevelTag(int)} method
     */
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

    @Before
    public void setup() {
        FyzLog.logLevel = Log.VERBOSE;
        FyzLog.doPrint = false;
        systemOutRule.clearLog();
    }

    private String configureSystemTest(final String prefix, final int level) {
        systemOutRule.clearLog();
        FyzLog.doPrint = true;

        final String logLevel = prefix + "@" + getLevelTag(level) + "/ ";
        final String tag = "FYZ:TheFyzLogTests ";
        final String thread = "[main] ";
        return logLevel + tag + thread;
    }

    private String message() {
        return "the message";
    }

    private String messageFormat() {
        return "%s %d %b %s";
    }

    private Object[] messageArgs() {
        return new Object[]{"it", 2357, true, "is"};
    }

    @Test
    public void massiveTestOfAllPrintMessageCombinations() {
        //Tracks how many tests we performed
        int ctr = 0;//Looping over the available Log calls
        for (final String s : new String[]{"V", "D", "I", "W", "E", "WTF"}) {
            //Looping over each logLevel, with an extra setting on each side
            for (int i = Log.VERBOSE - 1; i <= Log.ASSERT + 1; i++) {
                //Arrange
                final String prefix = configureSystemTest(s, i);
                final String msg = message();
                final String method = Thread.currentThread().getStackTrace()[1].getMethodName() + " : ";

                //Set the Log Level
                FyzLog.logLevel = i;

                //Prep the expected value
                final String expected = prefix + method + msg;

                //Act
                switch (s) {
                    case "V":
                        FyzLog.v(msg);
                        break;
                    case "D":
                        FyzLog.d(msg);
                        break;
                    case "I":
                        FyzLog.i(msg);
                        break;
                    case "W":
                        FyzLog.w(msg);
                        break;
                    case "E":
                        FyzLog.e(msg);
                        break;
                    case "WTF":
                        FyzLog.wtf(msg);
                        break;
                }

                //Assert
                final String target = systemOutRule.getLog();
                assertEquals("Failed at [s=" + s + "][i=" + i + "]", expected + "\n", target);

                //A test ran, inc
                ctr++;
            }
        }

        //Confirm we ran the expected number of tests
        assertThat(ctr).isEqualTo(6 * (6 + 2));
    }

    @Test
    public void printMessageThrowsNullPointerExceptionGivenNull() {
        //Tracks how many tests we performed
        int ctr = 0;
        //We're printing
        FyzLog.doPrint = true;
        //Looping over each logLevel
        for (int i = Log.VERBOSE; i <= Log.ASSERT; i++) {
            try {
                switch (i) {
                    case Log.VERBOSE:
                        FyzLog.v(null);
                        break;
                    case Log.DEBUG:
                        FyzLog.d(null);
                        break;
                    case Log.INFO:
                        FyzLog.i(null);
                        break;
                    case Log.WARN:
                        FyzLog.w(null);
                        break;
                    case Log.ERROR:
                        FyzLog.e(null);
                        break;
                    case Log.ASSERT:
                        FyzLog.wtf(null);
                        break;
                }
                //We're expecting exceptions, make sure we don't get here
                fail("Should Have Thrown");
            } catch (final IllegalArgumentException e) {
                //Make sure it's the exception we wanted
                assertThat(e.getMessage()).isEqualTo("FyzLog message can not be null");
            }
            //inc the test performed count
            ctr++;
        }
        //Verify we performed the expected tests
        assertThat(ctr).isEqualTo(6);
    }

    @Test
    public void massiveTestOfAllPrintMessageFormatCombinations() {
        //Tracks how many tests we performed
        int ctr = 0;
        //Looping over the available Log calls
        for (final String s : new String[]{"V", "D", "I", "W", "E", "WTF"}) {
            //Looping over each logLevel, with an extra setting on each side
            for (int i = Log.VERBOSE - 1; i <= Log.ASSERT + 1; i++) {
                //Arrange
                final String prefix = configureSystemTest(s, i);
                final String msg = messageFormat();
                final String method = Thread.currentThread().getStackTrace()[1].getMethodName() + " : ";

                //Set the Log Level
                FyzLog.logLevel = i;

                //Prep the expected value
                final String expected = prefix + method + msg;

                //Act
                switch (s) {
                    case "V":
                        FyzLog.v(messageFormat(), messageArgs());
                        break;
                    case "D":
                        FyzLog.d(messageFormat(), messageArgs());
                        break;
                    case "I":
                        FyzLog.i(messageFormat(), messageArgs());
                        break;
                    case "W":
                        FyzLog.w(messageFormat(), messageArgs());
                        break;
                    case "E":
                        FyzLog.e(messageFormat(), messageArgs());
                        break;
                    case "WTF":
                        FyzLog.wtf(messageFormat(), messageArgs());
                        break;
                }

                //Assert
                final String target = systemOutRule.getLog();
                assertEquals("Failed at [s=" + s + "][i=" + i + "]",
                        target,
                        String.format(expected, messageArgs()) + "\n");

                //A test ran, inc
                ctr++;
            }
        }
        //Confirm we ran the expected number of tests
        assertThat(ctr).isEqualTo(6 * (6 + 2));
    }

    /**
     * Can't (easily) assert that it formats, so this just tests logging or not functionality of the
     * log level setting.
     */
    @Test
    public void massiveAndroidLogging() {
        //Tracks how many tests we performed
        int ctr = 0;
        //Used to check if an exception should be thrown.
        final int offset = Log.VERBOSE;
        //The variation in the exception message
        final String[] tags = new String[]{"v", "d", "i", "w", "e", "wtf"};
        //Looping over the available Log calls
        for (int idx = 0; idx < tags.length; idx++) {
            //Looping over each logLevel, with an extra setting on each side
            for (int i = Log.VERBOSE - 1; i <= Log.ASSERT + 1; i++) {
                //Set the Log Level
                FyzLog.logLevel = i;
                //Get the tag
                final String tag = tags[idx];
                //Determine if logging will be attempted, which will result in an exception
                final boolean shouldTryToLog = i <= idx + offset;
                //Show what's about to be performed. If this breaks, it's HUGELY useful.
                System.out.println("[idx=" + idx + ";" + tag + "][i=" + i + "][shouldTryToLog=" + shouldTryToLog + "]");
                //INVOKE
                try {
                    switch (tag) {
                        case "v":
                            FyzLog.v(messageFormat(), messageArgs());
                            break;
                        case "d":
                            FyzLog.d(messageFormat(), messageArgs());
                            break;
                        case "i":
                            FyzLog.i(messageFormat(), messageArgs());
                            break;
                        case "w":
                            FyzLog.w(messageFormat(), messageArgs());
                            break;
                        case "e":
                            FyzLog.e(messageFormat(), messageArgs());
                            break;
                        case "wtf":
                            FyzLog.wtf(messageFormat(), messageArgs());
                            break;
                    }
                    //If we got here, we did not expect an exception, prove it
                    assertFalse("[idx=" + idx + ";" + tag + "][i=" + i + "]", shouldTryToLog);
                } catch (final RuntimeException e) {
                    //If we got here, we expected to, prove it
                    assertThat(shouldTryToLog).isTrue();
                    //Since this is kinda a catch all exception, make sure it's what we wanted
                    assertThat(e.getMessage()).isEqualTo("Method " + tag + " in android.util.Log not mocked. See http://g.co/androidstudio/not-mocked for details.");
                }
                //A test ran, inc
                ctr++;
            }
        }
        //Confirm we ran the expected number of tests
        assertThat(ctr).isEqualTo(6 * (6 + 2));
    }

    @Test
    public void androidLogDoesNothingGivenNull() {
        FyzLog.doPrint = false;
        systemOutRule.clearLog();
        FyzLog.logLevel = Log.VERBOSE - 1;
        //All calls should bail w/o doing anything
        FyzLog.v(null);
        FyzLog.d(null);
        FyzLog.i(null);
        FyzLog.w(null);
        FyzLog.e(null);
        FyzLog.wtf(null);

        //Also should not have logged via printing
        assertThat(systemOutRule.getLog()).isNullOrEmpty();
        assertTrue("It should have gotten here", true);
    }

}