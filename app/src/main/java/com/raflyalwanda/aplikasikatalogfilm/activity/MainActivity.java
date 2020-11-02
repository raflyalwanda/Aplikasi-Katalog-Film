package com.raflyalwanda.aplikasikatalogfilm.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.raflyalwanda.aplikasikatalogfilm.R;
import com.raflyalwanda.aplikasikatalogfilm.adapter.ViewPagerAdapter;
import com.raflyalwanda.aplikasikatalogfilm.fragment.NowPlayingFragment;
import com.raflyalwanda.aplikasikatalogfilm.fragment.UpcomingFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_title);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        setupTab();
    }

    private void setupTab() {
        TextView tabSatu = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabSatu.setText(getString(R.string.now_playing));
        tabLayout.getTabAt(0).setCustomView(tabSatu);

        TextView tabDua = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabDua.setText(getString(R.string.upcoming));
        tabLayout.getTabAt(1).setCustomView(tabDua);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new NowPlayingFragment(), getString(R.string.now_playing));
        adapter.addFragment(new UpcomingFragment(), getString(R.string.upcoming));
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.action_search :
                SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
                searchView.setOnQueryTextListener(this);
                break;

            case R.id.setting :
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                break;
        }
        return true;
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        Intent i = new Intent(MainActivity.this, SearchMovieActivity.class);
        i.putExtra("keyword", query);
        startActivity(i);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        return false;
    }

}
