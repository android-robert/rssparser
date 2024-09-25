package com.robert.rssparser;

import android.util.Log;

/**
 * Created by Robert Hoang on 2015/08/27.
 */
public class LogUtil {

    private static final String TAG = "develop";
    private static final boolean isDebug = BuildConfig.DEBUG;

    public static void v() {
        if (isDebug) {
            Log.v(TAG, getMetaInfo());
        }
    }

    public static void v(String message) {
        if (isDebug) {
            Log.v(TAG, getMetaInfo() + null2str(message));
        }
    }

    public static void d() {
        if (isDebug) {
            Log.d(TAG, getMetaInfo());
        }
    }

    public static void d(String message) {
        if (isDebug) {
            Log.d(TAG, getMetaInfo() + null2str(message));
        }
    }

    public static void d(String tag, String message) {
        if (isDebug) {
            Log.d(tag, message);
        }
    }

    public static void d(String format, Object... args) {
        if (isDebug) {
            Log.d(TAG, getMetaInfo() + null2str(String.format(format, args)));
        }
    }

    public static void i() {
        if (isDebug) {
            Log.i(TAG, getMetaInfo());
        }
    }

    public static void i(String message) {
        if (isDebug) {
            Log.i(TAG, getMetaInfo() + null2str(message));
        }
    }

    public static void i(String format, Object... args) {
        if (isDebug) {
            Log.i(TAG, getMetaInfo() + null2str(String.format(format, args)));
        }
    }

    public static void w(String message) {
        if (isDebug) {
            Log.w(TAG, getMetaInfo() + null2str(message));
        }
    }

    public static void w(String format, Object... args) {
        if (isDebug) {
            Log.w(TAG, getMetaInfo() + null2str(String.format(format, args)));
        }
    }

    public static void w(String message, Throwable e) {
        if (isDebug) {
            Log.w(TAG, getMetaInfo() + null2str(message), e);
            printThrowable(e);
            if (e.getCause() != null) {
                printThrowable(e.getCause());
            }
        }
    }

    public static void e(String message) {
        if (isDebug) {
            Log.e(TAG, getMetaInfo() + null2str(message));
        }
    }

    public static void e(String format, Object... args) {
        if (isDebug) {
            Log.e(TAG, getMetaInfo() + null2str(String.format(format, args)));
        }
    }

    public static void e(String message, Throwable e) {
        if (isDebug) {
            Log.e(TAG, getMetaInfo() + null2str(message), e);
            printThrowable(e);
            if (e.getCause() != null) {
                printThrowable(e.getCause());
            }
        }
    }

    public static void logLongJsonStringIfDebuggable(String tag, String json) {
        if (!isDebug) {
            return;
        }
        int length = json.length();
        for (int i = 0; i < length; i += 1024) {
            if (i + 1024 < length)
                Log.d(tag, json.substring(i, i + 1024));
            else
                Log.d(tag, json.substring(i, length));
        }
    }

    public static void e(Throwable e) {
        if (isDebug) {
            printThrowable(e);
            if (e.getCause() != null) {
                printThrowable(e.getCause());
            }
        }
    }

    private static String null2str(String string) {
        if (string == null) {
            return "(null)";
        }
        return string;
    }

    private static void printThrowable(Throwable e) {
        Log.e(TAG, e.getClass().getName() + ": " + e.getMessage());
        for (StackTraceElement element : e.getStackTrace()) {
            Log.e(TAG, "  at " + LogUtil.getMetaInfo(element));
        }
    }

    private static String getMetaInfo() {
        final StackTraceElement element = Thread.currentThread().getStackTrace()[4];
        return LogUtil.getMetaInfo(element);
    }

    public static String getMetaInfo(StackTraceElement element) {
        final String fullClassName = element.getClassName();
        final String simpleClassName = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
        final String methodName = element.getMethodName();
        final int lineNumber = element.getLineNumber();
        final String metaInfo = "[" + simpleClassName + "#" + methodName + ":" + lineNumber + "]";
        return metaInfo;
    }

}