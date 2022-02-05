package com.gymscircle.wordpressgymscircle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class TranslateMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, TranslateFragment.newInstance())
                    .commitNow();
        }
    }
}