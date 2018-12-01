package com.example.rkjc.news_app_2;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils
{
    public static final String Base_Url = "https://newsapi.org/v1/articles";
    // https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest&apiKey=d461273cdd584ca2902d587e5ac3cca3
    public static URL buildURL()
    {
        // COMPLETED (1) Fill in this method to build the proper Github query URL
        Uri builtUri = Uri.parse(Base_Url).buildUpon()
                .appendQueryParameter("source","the-next-web")
                .appendQueryParameter("sortBy","latest")
                .appendQueryParameter("apiKey","d461273cdd584ca2902d587e5ac3cca3")
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}