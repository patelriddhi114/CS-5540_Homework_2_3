package com.example.rkjc.news_app_2.firebase;

import android.content.Context;

import com.example.rkjc.news_app_2.HW2.NewsItemRepository;
import com.firebase.jobdispatcher.JobService;
import android.os.AsyncTask;
//grabs from ldb
public class FirebaseJobService extends JobService {
    private AsyncTask mBackgroundTask;
    NewsItemRepository mNewsItemRepo;
    @Override
    public boolean onStartJob(final com.firebase.jobdispatcher.JobParameters job) {
        mBackgroundTask = new AsyncTask()
        {
            @Override
            protected Object doInBackground(Object[] objects)
            {
                Context context = FirebaseJobService.this;
                UpdateTaskNotif.executeTask(context, UpdateTaskNotif.UPDATE_NEWS);
                mNewsItemRepo = new NewsItemRepository(getApplication());
                mNewsItemRepo.makeNewsSearchQuery();
                return null;
            }
            @Override
            protected void onPostExecute(Object o)
            {
                jobFinished(job, false );
            }
        };

        mBackgroundTask.execute();
        return true;
    }
    @Override
    public boolean onStopJob(com.firebase.jobdispatcher.JobParameters job) {
        if(mBackgroundTask != null) mBackgroundTask.cancel(true);
        return true;
    }
}
