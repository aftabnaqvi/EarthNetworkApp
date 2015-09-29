package earthnetwork.syed.com.earthnetworkapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import earthnetwork.syed.com.earthnetworkapp.R;
import earthnetwork.syed.com.earthnetworkapp.activities.DetailsActivity;
import earthnetwork.syed.com.earthnetworkapp.models.Image;
/**
 * Created by snaqvi on 9/27/15.
 */

// Provide the underlying view for an individual list item.
public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ViewHolder> {
    private Context     mContext;
    private List<Image> mImages;

    public ImagesAdapter(Activity context, List<Image> images) {
        mContext = context;
        if (images == null) {
            throw new IllegalArgumentException("images must not be null");
        }
        mImages = images;
    }

    public void addItems(List<Image> newItems){
        mImages.addAll(newItems);
    }

    // Inflate the view based on the viewType provided.
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new ViewHolder(itemView, mContext);
    }

    // Display data at the specified position
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Image image = mImages.get(position);
        holder.rootView.setTag(image);
        holder.tvName.setText(image.getName());
        Picasso.with(mContext).load(Image.getImageBaseUrl()+image.getFilename()).into(holder.ivThumbnail);

        // Define a listener for image loading
        Target target = new Target() {
            // Fires when Picasso finishes loading the bitmap for the target
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                if(holder.progressbar != null)
                    holder.progressbar.setVisibility(View.GONE);

                holder.ivThumbnail.setImageBitmap(bitmap);

                if(holder.vPalette != null) {
                    Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                        @Override
                        public void onGenerated(Palette palette) {
                            int color = palette.getVibrantColor(Color.LTGRAY);
                            holder.vPalette.setBackgroundColor(color);
                            holder.vPalette.setTag(color);
                        }
                    });
                }
            }

            // Fires if bitmap fails to load
            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                if(holder.progressbar != null)
                    holder.progressbar.setVisibility(View.GONE);
                Picasso.with(mContext).load(R.drawable.image_not_found).into(holder.ivThumbnail);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };

        // Store the target into the tag for the thumbnailImage to ensure target isn't garbage collected prematurely
        holder.ivThumbnail.setTag(target);

        // Instruct Picasso to load the bitmap into the target defined above
        Picasso.with(mContext).load(Image.getImageBaseUrl()+image.getFilename()).into(target);
    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }

    // Provide a reference to the views for each image item
    public final static class ViewHolder extends RecyclerView.ViewHolder {
        private final View rootView;
        private final ImageView ivThumbnail;
        private final TextView tvName;
        private final View vPalette;
        private final ProgressBar progressbar;

        public ViewHolder(View itemView, final Context context) {
            super(itemView);
            rootView = itemView;
            ivThumbnail = (ImageView)itemView.findViewById(R.id.ivThumbnail);
            tvName = (TextView)itemView.findViewById(R.id.tvName);
            vPalette = itemView.findViewById(R.id.vPalette);
            progressbar = (ProgressBar) itemView.findViewById(R.id.progressBar);

            // Navigate to Image details activity on click of card view.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                final Image image = (Image)v.getTag();
                if (image != null) {
                    // Fire an intent when an image is selected
                    Intent intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra(DetailsActivity.EXTRA_IMAGE_INFO, image);

                    context.startActivity(intent);
                }
                }
            });
        }
    }
}

