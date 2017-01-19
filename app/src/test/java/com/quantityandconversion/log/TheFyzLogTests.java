/*
 * Copyright (c) 2016 Fyzxs
 *
 * Licensed under The MIT License (MIT)
 */

package com.quantityandconversion.log;

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

    @Before
    public void setup() {
        FyzLog.updateCurrentLogLevel(LogLevel.VERBOSE);
        FyzLog.writeToLog();
        systemOutRule.clearLog();
    }

    private String configureSystemTest(final LogLevel currentLogLevel, final LogLevel attemptingLogLevel) {
        systemOutRule.clearLog();
        FyzLog.writeToSystem();

        final String logLevel = attemptingLogLevel.tag() + "@" + currentLogLevel.tag() + "/ ";
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
        int ctr = 0;
        final LogLevel[] logLevels = new LogLevel[]{LogLevel.VERBOSE, LogLevel.DEBUG, LogLevel.INFO, LogLevel.WARN, LogLevel.ERROR, LogLevel.ASSERT};
        for (final LogLevel currentLogLevel : logLevels) {
            //Set the Log Level
            FyzLog.updateCurrentLogLevel(currentLogLevel);
            //Looping over each logLevel
            for (final LogLevel attemptingLogLevel : logLevels) {
                //Arrange
                final String prefix = configureSystemTest(currentLogLevel, attemptingLogLevel);
                final String msg = message();
                final String method = Thread.currentThread().getStackTrace()[1].getMethodName() + " : ";


                //Prep the expected value
                final String expected = prefix + method + msg;

                //Act
                if (attemptingLogLevel == LogLevel.VERBOSE) FyzLog.v(msg);
                else if (attemptingLogLevel == LogLevel.DEBUG) FyzLog.d(msg);
                else if (attemptingLogLevel == LogLevel.INFO) FyzLog.i(msg);
                else if (attemptingLogLevel == LogLevel.WARN) FyzLog.w(msg);
                else if (attemptingLogLevel == LogLevel.ERROR) FyzLog.e(msg);
                else if (attemptingLogLevel == LogLevel.ASSERT) FyzLog.wtf(msg);

                //Assert
                final String target = systemOutRule.getLog();
                assertEquals("Failed at [currentLogLevel=" + currentLogLevel.tag() + "][attemptingLogLevel=" + attemptingLogLevel.tag() + "]", expected + "\n", target);

                //A test ran, inc
                ctr++;
            }
        }

        //Confirm we ran the expected number of tests
        assertThat(ctr).isEqualTo(6 * 6);
    }

    @Test
    public void printMessageThrowsNullPointerExceptionGivenNull() {
        //Tracks how many tests we performed
        int ctr = 0;
        //We're printing
        FyzLog.writeToSystem();
        //Looping over each logLevel
        for (final LogLevel logLevel : new LogLevel[]{LogLevel.VERBOSE, LogLevel.DEBUG, LogLevel.INFO, LogLevel.WARN, LogLevel.ERROR, LogLevel.ASSERT}) {
            try {
                if (logLevel == LogLevel.VERBOSE) FyzLog.v(null);
                else if (logLevel == LogLevel.DEBUG) FyzLog.d(null);
                else if (logLevel == LogLevel.INFO) FyzLog.i(null);
                else if (logLevel == LogLevel.WARN) FyzLog.w(null);
                else if (logLevel == LogLevel.ERROR) FyzLog.e(null);
                else if (logLevel == LogLevel.ASSERT) FyzLog.wtf(null);
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
        final LogLevel[] logLevels = new LogLevel[]{LogLevel.VERBOSE, LogLevel.DEBUG, LogLevel.INFO, LogLevel.WARN, LogLevel.ERROR, LogLevel.ASSERT};
        for (final LogLevel currentLogLevel : logLevels) {
            //Set the Log Level
            FyzLog.updateCurrentLogLevel(currentLogLevel);
            //Looping over each logLevel
            for (final LogLevel attemptingLogLevel : logLevels) {
                //Arrange
                final String prefix = configureSystemTest(currentLogLevel, attemptingLogLevel);
                final String msg = messageFormat();
                final String method = Thread.currentThread().getStackTrace()[1].getMethodName() + " : ";

                //Prep the expected value
                final String expected = prefix + method + msg;

                //Act

                if (attemptingLogLevel == LogLevel.VERBOSE)
                    FyzLog.v(messageFormat(), messageArgs());
                else if (attemptingLogLevel == LogLevel.DEBUG)
                    FyzLog.d(messageFormat(), messageArgs());
                else if (attemptingLogLevel == LogLevel.INFO)
                    FyzLog.i(messageFormat(), messageArgs());
                else if (attemptingLogLevel == LogLevel.WARN)
                    FyzLog.w(messageFormat(), messageArgs());
                else if (attemptingLogLevel == LogLevel.ERROR)
                    FyzLog.e(messageFormat(), messageArgs());
                else if (attemptingLogLevel == LogLevel.ASSERT)
                    FyzLog.wtf(messageFormat(), messageArgs());

                //Assert
                final String target = systemOutRule.getLog();
                assertEquals("Failed at [currentLogLevel=" + currentLogLevel.tag() + "][attemptingLogLevel=" + attemptingLogLevel.tag() + "]",
                        target,
                        String.format(expected, messageArgs()) + "\n");

                //A test ran, inc
                ctr++;
            }
        }
        //Confirm we ran the expected number of tests
        assertThat(ctr).isEqualTo(6 * 6);
    }

    /**
     * Can't (easily) assert that it formats, so this just tests logging or not functionality of the
     * log level setting.
     */
    @Test
    public void massiveAndroidLogging() {
        //Tracks how many tests we performed
        int ctr = 0;
        final LogLevel[] logLevels = new LogLevel[]{LogLevel.VERBOSE, LogLevel.DEBUG, LogLevel.INFO, LogLevel.WARN, LogLevel.ERROR, LogLevel.ASSERT};
        //Looping over the available Log calls
        for (final LogLevel currentLogLevel : logLevels) {
            //Set the Log Level
            FyzLog.updateCurrentLogLevel(currentLogLevel);
            //Looping over each logLevel
            for (final LogLevel attemptingLogLevel : logLevels) {
                //Determine if logging will be attempted, which will result in an exception
                final boolean shouldTryToLog = attemptingLogLevel.logAt(currentLogLevel);
                //Show what's about to be performed. If this breaks, it's HUGELY useful.
                System.out.println("[currentLogLevel=" + currentLogLevel.tag() + "][attemptingLogLevel=" + attemptingLogLevel.tag() + "][shouldTryToLog=" + shouldTryToLog + "]");
                //INVOKE
                try {
                    if (attemptingLogLevel == LogLevel.VERBOSE)
                        FyzLog.v(messageFormat(), messageArgs());
                    else if (attemptingLogLevel == LogLevel.DEBUG)
                        FyzLog.d(messageFormat(), messageArgs());
                    else if (attemptingLogLevel == LogLevel.INFO)
                        FyzLog.i(messageFormat(), messageArgs());
                    else if (attemptingLogLevel == LogLevel.WARN)
                        FyzLog.w(messageFormat(), messageArgs());
                    else if (attemptingLogLevel == LogLevel.ERROR)
                        FyzLog.e(messageFormat(), messageArgs());
                    else if (attemptingLogLevel == LogLevel.ASSERT)
                        FyzLog.wtf(messageFormat(), messageArgs());

                    //If we got here, we did not expect an exception, prove it
                    assertFalse("[currentLogLevel=" + currentLogLevel.tag() + "][attemptingLogLevel=" + attemptingLogLevel.tag() + "]", shouldTryToLog);
                } catch (final RuntimeException e) {
                    //If we got here, we expected to, prove it
                    assertThat(shouldTryToLog).isTrue();
                    //Since this is kinda a catch all exception, make sure it's what we wanted
                    assertThat(e.getMessage()).isEqualTo("Method " + attemptingLogLevel.tag().toLowerCase() + " in android.util.Log not mocked. See http://g.co/androidstudio/not-mocked for details.");
                }
                //A test ran, inc
                ctr++;
            }
        }
        //Confirm we ran the expected number of tests
        assertThat(ctr).isEqualTo(6 * 6);
    }

    @Test
    public void androidLogDoesNothingGivenNull() {
        FyzLog.writeToLog();
        systemOutRule.clearLog();
        FyzLog.updateCurrentLogLevel(LogLevel.VERBOSE);
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