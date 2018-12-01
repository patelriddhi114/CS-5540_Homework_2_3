package com.example.rkjc.news_app_2;

import android.util.Log;

import com.example.rkjc.news_app_2.HW2.NewsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    public static List<NewsItem> parseNews(String JSONString){
        List<NewsItem> newsList = new ArrayList<>();
        try{
            JSONObject main = new JSONObject(JSONString);
            JSONArray list = main.getJSONArray("articles");

            for(int i = 0; i < list.length(); i++){
                JSONObject item = list.getJSONObject(i);
                newsList.add(new NewsItem(item.getString("author"),
                        item.getString("title"),
                        item.getString("description"),
                        item.getString("url"),
                        item.getString("urlToImage"),
                        item.getString("publishedAt")));
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
        return newsList;
    }

}