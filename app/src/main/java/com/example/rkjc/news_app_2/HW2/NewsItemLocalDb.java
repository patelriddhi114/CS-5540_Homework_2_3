package com.example.rkjc.news_app_2.HW2;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {NewsItem.class}, version = 1, exportSchema = false)
public abstract class NewsItemLocalDb extends RoomDatabase
{
    public abstract NewsItemDao newsItemDao();
    private static volatile NewsItemLocalDb INSTANCE;

   //instantiates+buildsdb
    static NewsItemLocalDb getDatabase(final Context context)
    {
        if (INSTANCE == null)
        {
            synchronized (NewsItemLocalDb.class)
            {
                if (INSTANCE == null)
                {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NewsItemLocalDb.class, "newsItem_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
