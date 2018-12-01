package com.example.rkjc.news_app_2.HW2;


import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.rkjc.news_app_2.JsonUtils;
import com.example.rkjc.news_app_2.NetworkUtils;
import com.example.rkjc.news_app_2.NewsRecyclerViewAdapter;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class NewsItemRepository
{
    private NewsItemDao mNewsItemsDao;
    private LiveData<List<NewsItem>> mAllNewsItems;
    private NewsItemViewModel newsItemViewModel;
    NewsRecyclerViewAdapter mNewsAdapter;
    List<NewsItem> articles;
//start
    public NewsItemRepository(Application application)
    {
        NewsItemLocalDb db = NewsItemLocalDb.getDatabase(application.getApplicationContext());
        mNewsItemsDao = db.newsItemDao();
        mAllNewsItems = mNewsItemsDao.getAllNewsItems();
    }

//grabs json
    LiveData<List<NewsItem>> getAllWords()
    {
        return mAllNewsItems;
    }

    //new row into localdb
    public void insert (List<NewsItem> newsItems)
    {
        new insertAsyncTask(mNewsItemsDao).execute(newsItems);
    }

    //delete
    public void delete()
    {
        new deleteAsyncTask(mNewsItemsDao).execute();
    }

    //asynctask insert,
    private static class insertAsyncTask extends AsyncTask<List<NewsItem>, Void, Void>
    {
        private NewsItemDao tempAsyncTaskDao;
        insertAsyncTask(NewsItemDao dao)
        {
            tempAsyncTaskDao = dao;
        }
        //
        @Override
        protected Void doInBackground(final List<NewsItem>... params)
        {
            for(int i = 0; i < params.length; i++)
            {
                tempAsyncTaskDao.insert(params[i]);
            }
            return null;
        }
    }

    //clear db
    private static class deleteAsyncTask extends AsyncTask<List<NewsItem>, Void, Void> {
        private NewsItemDao mAsyncTaskDao;
        deleteAsyncTask(NewsItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<NewsItem>... params) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    public void makeNewsSearchQuery(){
        URL newsSearchUrl = NetworkUtils.buildURL();
        new NewsQueryTask().execute(newsSearchUrl);
    }

    public class NewsQueryTask extends AsyncTask<URL, Void, String>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            delete();
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String newsSearchResults = null;
            try {
                newsSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return newsSearchResults;
        }
        @Override
        protected void onPostExecute(String newsSearchResults) {
            super.onPostExecute(newsSearchResults);
            articles = JsonUtils.parseNews(newsSearchResults);
            insert(articles);
        }
    }
}
