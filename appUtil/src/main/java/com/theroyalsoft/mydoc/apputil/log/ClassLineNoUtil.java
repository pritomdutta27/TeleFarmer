package com.theroyalsoft.mydoc.apputil.log;

import timber.log.Timber;

/**
 * Created by Samiran Kumar on 10,August,2022
 **/
public class ClassLineNoUtil {
    public static String getLineNumber(Exception exception1) {

        String lineInfo = "";
        try {
            StackTraceElement traceElement = exception1.getStackTrace()[0];

            lineInfo = traceElement.getClassName() + ":" + traceElement.getMethodName() + "[" + traceElement.getLineNumber() + "]";

        } catch (Exception exception) {
            Timber.e("Error:" + exception.toString());
            lineInfo = "LINE_INFO_NOT_FOUND";
        }

        return lineInfo;
    }


    public static String getLineNumber(Throwable throwable) {

        String lineInfo = "";
        try {
            StackTraceElement traceElement = throwable.getStackTrace()[0];

            lineInfo = traceElement.getClassName() + ":" + traceElement.getMethodName() + "[" + traceElement.getLineNumber() + "]";

        } catch (Exception exception) {
            Timber.e("Error:" + exception.toString());
            lineInfo = "LINE_INFO_NOT_FOUND";
        }

        return lineInfo;
    }

}
