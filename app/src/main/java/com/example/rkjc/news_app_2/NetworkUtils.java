package com.example.rkjc.news_app_2;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    private static final String TAG = "NetworkUtils";

    public static final String PROTOCOL = "https";
    public static final String HOST = "newsapi.org";
    public static final String PATH = "v1/articles";
    public static final String SOURCE = "the-next-web";
    public static final String SORT_OPTION = "latest";
    public static final String API_KEY_MURIEL = "36555a8a1710493597be293ea93de657";
    private URL newsApiURL; //getters and setters

    public NetworkUtils() {
        setNewsApiURL(buildURL());
    }

    public void setNewsApiURL(URL newsApiURL) {
        this.newsApiURL = newsApiURL;
    }

    public URL getNewsApiURL() {
        return newsApiURL;
    }

    public static URL buildURL(){
        Log.d(TAG, "buildURL: Inside");
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(PROTOCOL)//http or https
                .authority(HOST)// company name
                .appendPath(PATH)//site path
                .appendQueryParameter("source",SOURCE)//
                .appendQueryParameter("sortBy", SORT_OPTION)
                .appendQueryParameter("apiKey",API_KEY_MURIEL)
                .build();
        try {
            Log.d(TAG, "buildURL: URL - " + builder.toString());
            //return new URL("https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest&apiKey=36555a8a1710493597be293ea93de657");
            return new URL(builder.toString());  //url object
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException { //given a URL getting the text opens a connection.
        Log.d(TAG, "getResponseFromHttpUrl: Inside");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try{
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if(hasInput){
                Log.d(TAG, "getResponseFromHttpUrl: Sending response...");
                return scanner.next();
            }else{
                Log.d(TAG, "getResponseFromHttpUrl: Didn't get any response...");
                return null;
            }
        }finally{
            urlConnection.disconnect();
        }
    }
}