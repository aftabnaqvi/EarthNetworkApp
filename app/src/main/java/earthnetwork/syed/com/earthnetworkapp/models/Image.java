package earthnetwork.syed.com.earthnetworkapp.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by snaqvi on 9/27/15.
 */
// Container class to hold Image information.
public class Image implements Serializable{
    private String mName;
    private String mDescription;
    private String mFilename;

    public Image(JSONObject imageJson) {
        try{
            mName = imageJson.getString("img_name");
            mDescription = imageJson.getString("img_description");
            mFilename = imageJson.getString("img_filename");

        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    public static ArrayList<Image> fromJSONArray(JSONArray array){
        ArrayList<Image> images = new ArrayList<Image>();

        int size = array.length();
        for(int i=0; i<size; i++){
            try{
                images.add(new Image(array.getJSONObject(i)));
            }catch(JSONException e){
                e.printStackTrace();
            }
        }
        return images;
    }

    public String toString(){
        return "Name: " + mName + "\nDescription: " + mDescription + "\nFilename: " + mFilename+"\n";
    }

    // getters
    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getFilename() {
        return mFilename;
    }

    public static String getImageBaseUrl(){
        // This is the base url for images. We will construct the complete url with this url by adding filename.
        return "http://entest-webappslab.rhcloud.com/images/";
    }
}

