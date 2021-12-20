package me.iscle.paraulgic;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

public class Paraulogic extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }
}
