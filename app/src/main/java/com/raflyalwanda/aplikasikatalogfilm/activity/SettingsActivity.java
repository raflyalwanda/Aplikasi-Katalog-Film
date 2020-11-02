package com.raflyalwanda.aplikasikatalogfilm.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.raflyalwanda.aplikasikatalogfilm.R;
import com.raflyalwanda.aplikasikatalogfilm.fragment.MyPreferenceFragment;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.title_activity_settings));

        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}
