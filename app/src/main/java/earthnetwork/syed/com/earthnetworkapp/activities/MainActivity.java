package earthnetwork.syed.com.earthnetworkapp.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import earthnetwork.syed.com.earthnetworkapp.Helpers.Device;
import earthnetwork.syed.com.earthnetworkapp.R;
import earthnetwork.syed.com.earthnetworkapp.fragments.ImagesFragment;
import earthnetwork.syed.com.earthnetworkapp.fragments.ImagesTabletFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUI();
    }

    private void setupUI() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content);

        if(Device.isPhone() || Device.isTablet()){
            fragment = createTargetFragment(ImagesFragment.class);
        } else if(Device.isXLargeTablet()) {
            fragment = createTargetFragment(ImagesTabletFragment.class);
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content, fragment).commit();
    }

    Fragment createTargetFragment(Class className){
        Fragment fragment = null;
        try {
            fragment =  (Fragment)className.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fragment;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
