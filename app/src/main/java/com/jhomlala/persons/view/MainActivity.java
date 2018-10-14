package com.jhomlala.persons.view;

import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;

import com.jhomlala.persons.data.R;
import com.jhomlala.persons.view.adapter.PageAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {
    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupBottomNavigation();
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        ViewPager viewPager = findViewById(R.id.viewpager);
        List<Fragment> fragmentsList = new ArrayList<>();
        fragmentsList.add(new PersonsFragment());
        fragmentsList.add(new AddPersonFragment());
        PageAdapter adapter = new PageAdapter(getSupportFragmentManager(), fragmentsList);
        viewPager.setAdapter(adapter);

        bottomNavigationView.setOnNavigationItemSelectedListener(element -> {
            Log.d(TAG, "Element clicked: " + element.getItemId());
            switch (element.getItemId()) {
                case R.id.action_list:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.action_add:
                    viewPager.setCurrentItem(1);
                    break;
                default:
                    viewPager.setCurrentItem(0);
            }
            return false;
        });


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}