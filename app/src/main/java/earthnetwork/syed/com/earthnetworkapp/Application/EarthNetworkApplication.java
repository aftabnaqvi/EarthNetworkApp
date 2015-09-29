package earthnetwork.syed.com.earthnetworkapp.Application;

import android.app.Application;
import android.content.Context;

import earthnetwork.syed.com.earthnetworkapp.Helpers.Device;

/**
 * Created by snaqvi on 9/28/15.
 */
public class EarthNetworkApplication extends Application{
    private static Context mContext;
    @Override
    public void onCreate() {
        mContext = this;
        super.onCreate();
        Device.init(this);
    }

    public static Context getContext(){
        return mContext;
    }
}
