package earthnetwork.syed.com.earthnetworkapp.activities;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import earthnetwork.syed.com.earthnetworkapp.Application.EarthNetworkApplication;
import earthnetwork.syed.com.earthnetworkapp.R;
import earthnetwork.syed.com.earthnetworkapp.Utils.Utils;
import earthnetwork.syed.com.earthnetworkapp.models.Image;
/**
 * Created by snaqvi on 9/27/15.
 */
public class DetailsActivity extends AppCompatActivity {
    public static final String EXTRA_IMAGE_INFO = "EXTRA_IMAGE_INFO";

    protected final String TAG = DetailsActivity.class.getSimpleName();

    private ImageView   mIvMainImage;
    private ProgressBar mProgressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // set <- button.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupUI();
    }

    private void setupUI(){
        mIvMainImage = (ImageView) findViewById(R.id.ivMainImage);
        TextView tvName = (TextView) findViewById(R.id.tvName);
        mProgressbar = (ProgressBar) findViewById(R.id.progressBar);

        TextView tvDescription = (TextView) findViewById(R.id.tvDescription);
        View vPalette = findViewById(R.id.vPalette);
        if(vPalette != null)
            vPalette.setBackgroundColor(getIntent().getExtras().getInt("color"));

        // Extract image from bundle
        Image image = (Image)getIntent().getExtras().getSerializable(EXTRA_IMAGE_INFO);

        if(tvName != null)
            tvName.setText(image.getName());

        if(tvDescription != null)
            tvDescription.setText(image.getDescription());

        // Fill views with data
        if(mIvMainImage != null) {
            LoadImage(image);
        }
    }

    private void LoadImage(Image image){
        if(!Utils.isNetworkAvailable()){
            // Toasts are not good but for sake of alert, I am showing toast.
            Toast.makeText(EarthNetworkApplication.getContext(), "Internet is not available", Toast.LENGTH_SHORT).show();
            return;
        }

        Picasso.with(this).load(Image.getImageBaseUrl() + image.getFilename()).into(mIvMainImage);

        // Define a listener for image loading
        Target target = new Target() {
            // Fires when Picasso finishes loading the bitmap for the target
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                if(mProgressbar != null)
                    mProgressbar.setVisibility(View.GONE);

                mIvMainImage.setImageBitmap(bitmap);
            }

            // Fires if bitmap fails to load
            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                if(mProgressbar != null)
                    mProgressbar.setVisibility(View.GONE);

                Picasso.with(DetailsActivity.this).load(R.drawable.image_not_found).into(mIvMainImage);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };

        // Store the target into the tag for the mainImage to ensure target isn't garbage collected prematurely
        mIvMainImage.setTag(target);

        // Instruct Picasso to load the bitmap into the target defined above
        Picasso.with(this).load(Image.getImageBaseUrl() + image.getFilename()).into(target);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

