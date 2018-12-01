package com.example.rkjc.news_app_2.firebase;

import android.content.Context;


public class UpdateTaskNotif {

    public static final String ACTION_DISMISS_NOTIFICATION = "dismiss-notification";
    public static final String UPDATE_NEWS = "update_news";
    public static void executeTask(Context context, String action)
    {
        if(ACTION_DISMISS_NOTIFICATION.equals(action))
        {
            NotificationUtils.clearAllNotifications(context);
        } else if(UPDATE_NEWS.equals(action)){
            UpdateTaskNotif instance = new UpdateTaskNotif();
            instance.update();
            NotificationUtils.notifyNews(context);
        }
    }
        public void update()
        {

        }
}
