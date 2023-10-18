package bio.medico.patient.data;

import java.util.Date;

/**
 * Created by Samiran Kumar on 27,July,2022
 **/
public class JwModel {
    private long iat;
    private long exp;

    public long getIat() {
        return iat;
    }

    public long getExp() {
         return exp;
        //return (new Date().getTime()-1000 )/ 1000;
    }
}
