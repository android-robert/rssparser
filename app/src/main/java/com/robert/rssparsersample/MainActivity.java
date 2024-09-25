package com.robert.rssparsersample;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.rlContainer, RssFragment.newInstance("https://vnexpress.net/rss/the-gioi.rss"))
                .commit();


    }
}
