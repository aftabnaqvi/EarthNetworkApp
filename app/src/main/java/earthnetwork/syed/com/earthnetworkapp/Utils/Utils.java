package earthnetwork.syed.com.earthnetworkapp.Utils;

import android.app.Application;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import earthnetwork.syed.com.earthnetworkapp.Application.EarthNetworkApplication;

/**
 * Created by snaqvi on 9/28/15.
 */
public class Utils {
    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) EarthNetworkApplication.getContext().getSystemService(Application.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}
