package com.example.rkjc.news_app_2;
import android.support.v7.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private ArrayList<NewsItem> newsItems = new ArrayList<NewsItem>();
    private NetworkUtils networkUtils = new NetworkUtils();
    Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) { //default function
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Calling...");

        mToast= Toast.makeText( this  , "" , Toast.LENGTH_SHORT );
        initNewsApp();
    }

    private class NewsQueryTask extends AsyncTask<String, String, String> {
        private static final String TAG = "NewsQueryTask";
        private String responseFromHttpUrl;
        @Override
        protected String doInBackground(String... strings) {
            Log.d(TAG, "doInBackground: Start");
            responseFromHttpUrl="";
            try {
                responseFromHttpUrl = NetworkUtils.getResponseFromHttpUrl(networkUtils.getNewsApiURL());
                Log.d(TAG, "doInBackground: responseFromHttpUrl => "+responseFromHttpUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return responseFromHttpUrl;
        }
        @Override
        protected void onPreExecute() {
            mToast.setText("Updating...");
            mToast.show();
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d(TAG, "onPostExecute: Inside");
            newsItems=JsonUtils.parseNews(result);
            initRecyclerView();
            mToast.setText("Refreshed");
            mToast.show();
        }
    }

    //Muriel Menu Code Starts >>>
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Handle item selection
        switch (item.getItemId()) {
            case R.id.action_search:
                NewsQueryTask newsQueryTask = new NewsQueryTask();
                newsQueryTask.execute();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //Muriel Menu Code Ends <<<

    private void initNewsApp(){
        Log.d(TAG, "initNewsApp: Started");
        NewsQueryTask newsQueryTask = new NewsQueryTask();
        newsQueryTask.execute();
        initRecyclerView();
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: Started");
        RecyclerView recyclerView =findViewById(R.id.news_recyclerview);
        NewsRecyclerViewAdapter adapter = new NewsRecyclerViewAdapter(newsItems, this );
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
