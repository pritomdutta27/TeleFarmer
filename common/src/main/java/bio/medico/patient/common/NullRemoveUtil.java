package bio.medico.patient.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Samiran Kumar on 10,August,2023
 **/
public class NullRemoveUtil {

    public static String getNotNull(String data) {
        return data == null ? "" : data;
    }

    public static int getNotNull(int data) {
        return data;
    }

    public static long getNotNull(long data) {
        return data;
    }

    public static Date getNotNull(Date data) {
        return data == null ? new Date() : data;
    }


    public static <T> List<T> getNotNull(List<T> data) {
        return data == null ? (List<T>) new ArrayList<>() : data;
    }
}
