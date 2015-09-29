package earthnetwork.syed.com.earthnetworkapp.Helpers;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;

/**
 * Created by snaqvi on 9/27/15.
 */
public class Device {
    private static Context mContext;
    private static boolean mIsXLargeTablet;
    private static boolean mIsTablet;
    private static boolean mIsPhone;
    private static boolean mInitialize;

    public static void init(Context context){
        mContext = context;
        int screenSize = mContext.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK;

        switch (screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                mIsXLargeTablet = true;
                break;

            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                mIsTablet = true;
                break;

            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                mIsPhone = true;
                break;
            default:
                break;
        }

        mInitialize = true;
    }
    public static boolean isXLargeTablet(){
        if(mInitialize == false)
            Log.e("Error: ", "Device is not Initialized");

        return mIsXLargeTablet;
    }

    public static boolean isTablet(){
        if(mInitialize == false)
            Log.e("Error: ", "Device is not Initialized");

        return mIsTablet;
    }

    public static boolean isPhone(){
        if(mInitialize == false)
            Log.e("Error: ", "Device is not Initialized");

        return mIsPhone;
    }
}
