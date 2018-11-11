package com.example.rkjc.news_app_2;
//package com.example.murie.newsapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class JsonUtils {
    private static final String TAG = "JsonUtils";
    private static final String ARTICLES = "articles";

    public static ArrayList<NewsItem> parseNews(String jObject) {
        Log.d(TAG, "parseNews: Inside");
        ArrayList<NewsItem> newsItems = new ArrayList<NewsItem>();
        JSONArray array = null; //
        try {
            array = (new JSONObject(jObject)).getJSONArray(ARTICLES);// all the children under articles comes under
            Log.d(TAG, "parseNews: Got "+array.length() +" articles to parse");
            for (int i = 0; i < array.length(); i++) {
//newsitems constructor called here
                newsItems.add(new NewsItem(array.getJSONObject(i).getString("author"),
                        array.getJSONObject(i).getString("title"),
                        array.getJSONObject(i).getString("description"),
                        array.getJSONObject(i).getString("url"),
                        array.getJSONObject(i).getString("urlToImage"),
                        array.getJSONObject(i).getString("publishedAt")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newsItems;
    }
}

