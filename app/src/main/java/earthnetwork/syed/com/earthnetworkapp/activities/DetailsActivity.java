package earthnetwork.syed.com.earthnetworkapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import earthnetwork.syed.com.earthnetworkapp.R;
import earthnetwork.syed.com.earthnetworkapp.models.Image;
/**
 * Created by snaqvi on 9/27/15.
 */
public class DetailsActivity extends AppCompatActivity {
    // This is the base url for images. We will construct the complete url with this url by adding filename.
    protected final String mImagesBaseUrl = "http://entest-webappslab.rhcloud.com/images/";

    public static final String TAG = DetailsActivity.class.getSimpleName();
    public static final String EXTRA_IMAGE_INFO = "EXTRA_IMAGE_INFO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // set <- button.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupUI();
    }

    private void setupUI(){
        ImageView ivMainImage = (ImageView) findViewById(R.id.ivMainImage);
        TextView tvDescription = (TextView) findViewById(R.id.tvDescription);
        View vPalette = findViewById(R.id.vPalette);
        if(vPalette != null)
            vPalette.setBackgroundColor(getIntent().getExtras().getInt("color"));

        // Extract image from bundle
        Image mImage = (Image)getIntent().getExtras().getSerializable(EXTRA_IMAGE_INFO);

        // Fill views with data
        if(ivMainImage != null)
            Picasso.with(this).load(mImagesBaseUrl+mImage.getFilename()).into(ivMainImage);

        if(tvDescription != null)
            tvDescription.setText(mImage.getDescription());
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

