package com.theroyalsoft.mydoc.apputil.log;

import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import com.theroyalsoft.mydoc.apputil.BuildConfig;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import timber.log.Timber;

/**
 * Created by Samiran on 5/12/2018.
 *
 * @dependencies implementation 'com.jakewharton.timber:timber:4.7.1'
 */
public class LogUtil {


    public static void initializeLog(String tagName) {

        if (BuildConfig.DEBUG) {

            Timber.plant(new Timber.DebugTree() {
                @Override
                protected @Nullable
                String createStackElementTag(@NotNull StackTraceElement element) {
                    return tagName + ":[" + super.createStackElementTag(element) + ":" + element.getLineNumber() + "]";
                }
            });

        } else {
            //
        }
    }


    static String separator = "==============================================================\n";

    public static class TimberLoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            long t1 = System.nanoTime();
            Timber.v(separator + "Sending request %s %s on %s%n%s", request.method(), request.url(), chain.connection(), request.headers());

            if (request.method().compareToIgnoreCase("post") == 0){
                Timber.v(separator + "REQUEST BODY BEGIN=================\n%s\nREQUEST BODY END=================", BeautifulJson.beautiful(bodyToString(request)));
            }


            Response response = chain.proceed(request);

            ResponseBody responseBody = response.body();
            String responseBodyString = response.body().string();

            // now we have extracted the response body but in the process
            // we have consumed the original response and can't read it again
            // so we need to build a new one to return from this method

            Response newResponse = response.newBuilder().body(ResponseBody.create(responseBody.contentType(), responseBodyString.getBytes())).build();

            long t2 = System.nanoTime();

           /* String responseJson = " \nRESPONSE BODY BEGIN============\n" +
                    BeautifulJson.beautiful(responseBodyString) +
                    "\nRESPONSE BODY END=============";

            if (responseBodyString.isEmpty()) {
                responseJson = "";
            }

            Timber.e("HKHR TEST:[" + responseJson + "]");

            String log = separator
                    + "Received response for " + response.request().url() +
                    " [" + response.request().method().toUpperCase() + "|" + response.code() + "]" +
                    "in %.1fms%n%s" +
                    responseJson;

            try {
               // Timber.v(log, (t2 - t1) / 1e6d, response.headers());

            } catch (Exception e) {
                Timber.e("LOG Error:" + e.toString());
            }*/

               Timber.v(separator + "Received response for %s [%s|%s] in %.1fms%n%s", response.request().url(), response.request().method().toUpperCase(), response.code(), (t2 - t1) / 1e6d, response.headers());

             Timber.v("RESPONSE BODY BEGIN============\n%s\nRESPONSE BODY END=============", BeautifulJson.beautiful(responseBodyString));

            return newResponse;
        }

    }

    public static String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();


            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }


    //================================================================
    public static class Logger {
        private static boolean DEBUG = true;

        public static void d(String tag, String arg) {
            if (isEnable()) {
                log(tag, arg);
            }
        }

        public static void d(String logMsg) {
            if (isEnable()) {
                log(getCurrentClassName(), getCurrentMethodName() + "(): " + logMsg);
            }
        }

        public static void dd(String tag, Object source) {
            if (isEnable()) {
                Object o = getJsonObjFromStr(source);
                if (o != null) {
                    try {
                        if (o instanceof JSONObject) {
                            format(tag, ((JSONObject) o).toString(2));
                        } else if (o instanceof JSONArray) {
                            format(tag, ((JSONArray) o).toString(2));
                        } else {
                            format(tag, source);
                        }
                    } catch (JSONException e) {
                        format(tag, source);
                    }
                } else {
                    format(tag, source);
                }
            }
        }

        private static void log(String tag, String msg) {
            // Log.d(tag, "##===================================>>");
            if (msg.isEmpty()) {
                Timber.e("msg.isEmpty()");
                return;
            }
            Log.d(tag, msg);

        }

        private static String getSplitter(int length) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < length; i++) {
                builder.append(".");
            }
            return builder.toString();
        }

        private static void format(String tag, Object source) {
            tag = " " + tag + " ";
            // log(" ", " ");
            log(" ", getSplitter(50) + tag + getSplitter(50));
            log(" ", "" + source);
            log(" ", getSplitter(100 + tag.length()));
            //log(" ", " ");
        }

        private static String getCurrentMethodName() {
            return Thread.currentThread().getStackTrace()[4].getMethodName();
        }

        private static String getCurrentClassName() {
            String className = Thread.currentThread().getStackTrace()[4].getClassName();
            String[] temp = className.split("[\\.]");
            className = temp[temp.length - 1];
            return className;
        }

        private static Object getJsonObjFromStr(Object test) {
            Object o = null;
            try {
                o = new JSONObject(test.toString());
            } catch (JSONException ex) {
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        o = new JSONArray(test);
                    }
                } catch (JSONException ex1) {
                    return null;
                }
            }
            return o;
        }

        public static boolean isEnable() {
            return DEBUG;
        }

        public static void setEnable(boolean flag) {
            Logger.DEBUG = flag;
        }
    }


}