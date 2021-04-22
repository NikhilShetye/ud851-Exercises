package com.example.android.background.sync;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.android.background.utilities.NotificationUtils;

public class ReminderWorker extends Worker {
    private static final String TAG = "ReminderWorker";

    public ReminderWorker(
            @NonNull Context appContext,
            @NonNull WorkerParameters workerParams) {
        super(appContext, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {

            Log.i(TAG, "doWork is called");
            Context context=getApplicationContext();
            ReminderTasks.executeTask(context,ReminderTasks.ACTION_CHARGING_REMINDER);

            // If there were no errors, return SUCCESS
            return Result.success();
        } catch (Throwable throwable) {

            // Technically WorkManager will return Result.failure()
            // but it's best to be explicit about it.
            // Thus if there were errors, we're return FAILURE
            Log.e(TAG, "reminder successful", throwable);
            return Result.failure();
        }
    }

}
