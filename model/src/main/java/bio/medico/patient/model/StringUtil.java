package bio.medico.patient.model;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * @DEV #SamiranKumar11
 * @Created by Samiran on 15/07/2019.
 */

/**
 * Created by Samiran Kumar on 10,August,2023
 **/
public class StringUtil {


    //==============================================================================================
    public static String getCapitalizeSentences(String beforeSentences) {
        String text = beforeSentences == null ? "" : beforeSentences;
        int pos = 0;
        boolean capitalize = true;
        StringBuilder sb = new StringBuilder(text);
        while (pos < sb.length()) {
            if (sb.charAt(pos) == '.') {
                capitalize = true;
            } else if (capitalize && !Character.isWhitespace(sb.charAt(pos))) {
                sb.setCharAt(pos, Character.toUpperCase(sb.charAt(pos)));
                capitalize = false;
            }
            pos++;
        }
        String sentences = sb.toString();

        return sentences;
    }

    //==============================================================================================
    public static String getFirstWord(String beforeSentences) {
        String text = beforeSentences == null ? "" : beforeSentences;
        return text.split(" ")[0];
    }

    //==============================================================================================
    public static String getCamelCase(String beforeText) {
        String text = beforeText == null ? "" : beforeText;

        if (text.isEmpty()) {
            return text;
        }

        StringBuilder builder = new StringBuilder(text);

        boolean isLastSpace = true;

        // Iterate String from beginning to end.
        for (int i = 0; i < builder.length(); i++) {
            char ch = builder.charAt(i);

            if (isLastSpace && ch >= 'a' && ch <= 'z') {
                // Character need to be converted to uppercase
                builder.setCharAt(i, (char) (ch + ('A' - 'a')));
                isLastSpace = false;
            } else if (ch != ' ')
                isLastSpace = false;
            else
                isLastSpace = true;
        }

        return builder.toString();
    }

    //==============================================================================================
    public static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }

    //==============================================================================================
    public static List<String> getListFromString(String data, String split) {
        data = data + "".trim();
        // data = data.replace("\\\"", "\"");
        return Arrays.asList(data.split(split));
    }

    //==============================================================================================
    public static boolean isValidURL(String urlStr) {
        try {
            URL url = new URL(urlStr);
            return true;
        } catch (MalformedURLException e) {
            // Log.e("Error:" + e.toString());
            return false;
        }
    }

    //==============================================================================================
 /*   public static String replaceChar(String str) {
        String newString = str.replaceAll(" ", "");
        //Timber.e("Old String;; "+str);
        //Timber.e("Replace String:: "+newString);
        return newString;
    }*/

    //==============================================================================================
 /*   public static String replaceAllChar(String str, String replace) {
        return str.replaceAll(" ", replace);
    }*/


    //==============================================================================================
    public static String getNotNullString(String data) {
        return data == null ? "" : data;
    }


    //==============================================================================================
  /*  public static String replaceChar(String str, String c) {
        String newString = str.replaceAll(c, "");
        return newString;
    }*/

    //==============================================================================================
    public static boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    //==============================================================================================
    public static String replaceAllChar(String mainText, String replaceBy, String replaceAfter) {
        return mainText.replaceAll(replaceBy, replaceAfter);
    }

    //==============================================================================================
    public static String capitalizeFirstWord(String type) {
        if (type.isEmpty() || type == null) {
            return type;
        }

        return type.substring(0, 1).toUpperCase() + type.substring(1);
    }

    //==============================================================================================
    public static String removeExtraSpace(String text) {
        return text.trim().replaceAll("\\s+", " ");
    }
}

