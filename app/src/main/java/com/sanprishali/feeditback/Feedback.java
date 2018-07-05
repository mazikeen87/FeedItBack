package com.sanprishali.feeditback;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Feedback extends AppCompatActivity {

    LinearLayout feedbackLayout,viewLayout,profileLayout;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_feedback:
                    feedbackActivity();
                    return true;
                case R.id.navigation_view:
                    viewActivity();
                    return true;
                case R.id.navigation_profile:
                    profileActivity();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        feedbackLayout = (LinearLayout) findViewById(R.id.feedbackLayout);
        viewLayout = (LinearLayout) findViewById(R.id.viewLayout);
        profileLayout = (LinearLayout) findViewById(R.id.profileLayout);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void feedbackActivity(){
        feedbackLayout.setVisibility(View.VISIBLE);
        viewLayout.setVisibility(View.INVISIBLE);
        profileLayout.setVisibility(View.INVISIBLE);
    }

    public void viewActivity(){
        feedbackLayout.setVisibility(View.INVISIBLE);
        viewLayout.setVisibility(View.VISIBLE);
        profileLayout.setVisibility(View.INVISIBLE);
    }

    public void profileActivity(){
        feedbackLayout.setVisibility(View.INVISIBLE);
        viewLayout.setVisibility(View.INVISIBLE);
        profileLayout.setVisibility(View.VISIBLE);
    }
}
