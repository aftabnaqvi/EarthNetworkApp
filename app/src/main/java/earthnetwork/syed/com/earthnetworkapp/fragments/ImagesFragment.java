package earthnetwork.syed.com.earthnetworkapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by snaqvi on 9/27/15.
 */
public class ImagesFragment extends ImagesBaseFragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        LinearLayoutManager layout = new LinearLayoutManager(getActivity());
        mRvImages.setLayoutManager(layout);

        getImages();
        return view;
    }
}
