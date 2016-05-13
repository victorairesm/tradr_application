package br.com.victor.tradr.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import br.com.victor.tradr.R;
import br.com.victor.tradr.fragments.AboutDialog;
import br.com.victor.tradr.fragments.FeedFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolbar();
        setupNavDrawer();
        replaceFragment(new FeedFragment());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_about) {
            AboutDialog.showAbout(getSupportFragmentManager());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
