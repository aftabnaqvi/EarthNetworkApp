package earthnetwork.syed.com.earthnetworkapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import earthnetwork.syed.com.earthnetworkapp.R;
import earthnetwork.syed.com.earthnetworkapp.activities.DetailsActivity;
import earthnetwork.syed.com.earthnetworkapp.activities.MainActivity;
import earthnetwork.syed.com.earthnetworkapp.models.Image;
/**
 * Created by snaqvi on 9/27/15.
 */

// Provide the underlying view for an individual list item.
public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.VH> {

    // This is the base url for images. We will construct the complete url with this url by adding filename.
    protected final String mImagesBaseUrl = "http://entest-webappslab.rhcloud.com/images/";

    private Context mContext;
    private List<Image> mImages;

    public ImagesAdapter(Activity context, List<Image> images) {
        mContext = context;
        if (images == null) {
            throw new IllegalArgumentException("contacts must not be null");
        }
        mImages = images;
    }

    public void addItems(List<Image> newItems){
        mImages.addAll(newItems);
    }

    // Inflate the view based on the viewType provided.
    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new VH(itemView, mContext);
    }

    // Display data at the specified position
    @Override
    public void onBindViewHolder(final VH holder, int position) {

        Image image = mImages.get(position);
        holder.rootView.setTag(image);
        holder.tvName.setText(image.getName());
        Picasso.with(mContext).load(mImagesBaseUrl+image.getFilename()).into(holder.ivThumbnail);

        // Define a listener for image loading
        Target target = new Target() {
            // Fires when Picasso finishes loading the bitmap for the target
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                // TODO 1. Insert the bitmap into the profile image view
                holder.ivThumbnail.setImageBitmap(bitmap);;
                // TODO 2. Use generateAsync() method from the Palette API to get the vibrant color from the bitmap
                //      Set the result as the background color for `R.id.vPalette` view containing the image's name.
                Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        int color = palette.getVibrantColor(Color.LTGRAY);
                        holder.vPalette.setBackgroundColor(color);
                        holder.vPalette.setTag(color);
                    }
                });
            }

            // Fires if bitmap fails to load
            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                Picasso.with(mContext).load(R.drawable.image_not_found).into(holder.ivThumbnail);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };

        // Store the target into the tag for the profile to ensure target isn't garbage collected prematurely
        holder.ivThumbnail.setTag(target);
        // Instruct Picasso to load the bitmap into the target defined above
        Picasso.with(mContext).load(mImagesBaseUrl+image.getFilename()).into(target);
    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }

    // Provide a reference to the views for each contact item
    public final static class VH extends RecyclerView.ViewHolder {
        final View rootView;
        final ImageView ivThumbnail;
        final TextView tvName;
        final View vPalette;

        public VH(View itemView, final Context context) {
            super(itemView);
            rootView = itemView;
            ivThumbnail = (ImageView)itemView.findViewById(R.id.ivThumbnail);
            tvName = (TextView)itemView.findViewById(R.id.tvName);
            vPalette = itemView.findViewById(R.id.vPalette);


            // Navigate to contact details activity on click of card view.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Image image = (Image)v.getTag();
                    if (image != null) {
                        // Fire an intent when a contact is selected
                        // Pass contact object in the bundle and populate details activity.
                        Intent i = new Intent(context, DetailsActivity.class);
                        Bundle b = new Bundle();
                        b.putSerializable(DetailsActivity.EXTRA_IMAGE_INFO, image);

                        b.putInt("color", (int) vPalette.getTag());
                        i.putExtras(b);

                        // Create transition
                        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                ((MainActivity) context),
                                new Pair<View, String>(ivThumbnail, "thumbnail"),
                                new Pair<View, String>(vPalette, "palette"),
                                new Pair<View, String>(tvName, "name")
                        );

                        context.startActivity(i, options.toBundle());
                    }
                }
            });
        }
    }
}

