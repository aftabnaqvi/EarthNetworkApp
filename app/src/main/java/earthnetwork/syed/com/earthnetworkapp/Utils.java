package earthnetwork.syed.com.earthnetworkapp;

import android.content.Context;
import android.content.res.Configuration;

/**
 * Created by snaqvi on 9/27/15.
 */
public class Utils {
    static Context mContext;
    static boolean mIsXLargeTablet;
    static boolean mIsTablet;
    static boolean mIsPhone;

    public Utils(Context context){
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
    }

    public static boolean isXLargeTablet(){
        return mIsXLargeTablet;
    }

    public static boolean isTablet(){
        return mIsTablet;
    }

    public static boolean isPhone(){
        return mIsPhone;
    }
}
