package bio.medico.patient.common;

import android.app.Activity;

/**
 * Created by Samiran Kumar on 08,August,2023
 **/
public class UiNavigation {

    public static boolean isCallButtonClicked = false;
    public static INavigation iNavigation;

    public static void setConfig(INavigation iNavigation) {
        UiNavigation.iNavigation = iNavigation;
    }


    public interface INavigation {

        void localLoginInfoRemove();

        void goBlockUserActivity(String message);

        void showInstructionDialog(Activity activity);
    }
}
