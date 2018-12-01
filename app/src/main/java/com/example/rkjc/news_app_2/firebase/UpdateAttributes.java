package com.example.rkjc.news_app_2.firebase;

import android.content.Context;
import android.support.annotation.NonNull;

import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

public class UpdateAttributes
{
    private static final String UPDATE_JOB_TAG = "news_update_tag";
    private static boolean sInit;
//timer for updater, notif attributes
    synchronized public static void scheduleUpdate(@NonNull final Context context)
    {
        if(sInit)
        {
            return;
        }
        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);
        Job constraintReminderJob = dispatcher.newJobBuilder()
                .setService(FirebaseJobService.class)
                .setTag(UPDATE_JOB_TAG)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(10,10))
                .setReplaceCurrent(true)
                .build();
        dispatcher.schedule(constraintReminderJob);
        sInit = true;
    }
}
