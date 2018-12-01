package com.example.rkjc.news_app_2.firebase;

import android.app.IntentService;
import android.content.Intent;
//update service
public class UpdateIntentService extends IntentService
{
    public UpdateIntentService() {super("UpdateIntentService");}
    @Override
    protected void onHandleIntent(Intent intent)
    {
        String action = intent.getAction();
        UpdateTaskNotif.executeTask(this, action);
    }
}
