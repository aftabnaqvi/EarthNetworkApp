package earthnetwork.syed.com.earthnetworkapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import earthnetwork.syed.com.earthnetworkapp.Adapters.ImagesAdapter;
import earthnetwork.syed.com.earthnetworkapp.R;
import earthnetwork.syed.com.earthnetworkapp.models.Image;

/**
 * Created by snaqvi on 9/27/15.
 */
public class ImagesBaseFragment extends Fragment {
    // This is the base url for images. We will construct the complete url with this url by adding filename.
    protected final String mImagesBaseUrl = "http://entest-webappslab.rhcloud.com/images/";
    // We will get json from this url.
    protected final String mUrl = "http://entest-webappslab.rhcloud.com/data/data.json";

    protected ImagesAdapter mAdapter;
    protected RecyclerView mRvImages;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_images, container, false);

        // Find RecyclerView and bind to adapter
        mRvImages = (RecyclerView) view.findViewById(R.id.rvImages);

        // allows for optimizations
        mRvImages.setHasFixedSize(true);

        // create the list
        List<Image> images = new ArrayList<Image>();

        // Create an adapter
        mAdapter = new ImagesAdapter(getActivity(), images);

        // Bind adapter to list
        mRvImages.setAdapter(mAdapter);

        return view;
    }

    protected void getImages(){
        // creating  network client, to initiate the network request.
        AsyncHttpClient client = new AsyncHttpClient();

        // trigger the network request
        client.get(mUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    JSONArray array = response.getJSONArray("album");
                    List<Image> images = Image.fromJSONArray(array);
                    mAdapter.addItems(images);
                    mAdapter.notifyItemInserted(images.size()-1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("ERROR: Wrong response.", response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e("ERROR", errorResponse.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e("ERROR", errorResponse.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("ERROR", responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
            }
        });
    }
}
