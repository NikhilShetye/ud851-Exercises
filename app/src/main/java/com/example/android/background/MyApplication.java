package com.example.android.background;


import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.Build;

import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.android.background.sync.ReminderWorker;

import java.util.concurrent.TimeUnit;

public class MyApplication extends Application {


    private static MyApplication instance;

    public static MyApplication getInstance(){
        if (instance== null) {
            synchronized(MyApplication.class) {
                if (instance == null)
                    instance = new MyApplication();
            }
        }
        // Return the instance
        return instance;
    }


    @SuppressLint("RestrictedApi")
    @Override
    public void onCreate() {
        super.onCreate();






    }
}
