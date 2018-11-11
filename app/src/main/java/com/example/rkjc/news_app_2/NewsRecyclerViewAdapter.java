package com.example.rkjc.news_app_2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsItemViewHolder>{

    private static final String TAG = "NewsRecyclerViewAdapter";

    private ArrayList<NewsItem> mNewsItems = new ArrayList<NewsItem>();
    private Context mContext;//current state

    public NewsRecyclerViewAdapter(ArrayList<NewsItem> mNewsItems, Context mContext) {
        Log.d(TAG, "NewsRecyclerViewAdapter: Inside constructor");
        this.mNewsItems = mNewsItems;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public NewsItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.d(TAG, "onCreateViewHolder: Got Inside");
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_item, viewGroup , false);
        NewsItemViewHolder newsItemViewholder = new NewsItemViewHolder(view);
        return newsItemViewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsItemViewHolder newsItemViewHolder,final int i) {
        Log.d(TAG, "onBindViewHolder: Inside onBindViewHolder");

        Glide.with(mContext)
                .asBitmap()
                .load(mNewsItems.get(i).getUrlToImage())
                .into(newsItemViewHolder.newsImage);

        newsItemViewHolder.newsTitle.setText("Title:"+mNewsItems.get(i).getTitle());
        newsItemViewHolder.newsDescription.setText("Description:"+mNewsItems.get(i).getDescription());
        newsItemViewHolder.newsDate.setText("Date:"+mNewsItems.get(i).getPublishedAt());

        newsItemViewHolder.newsItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Clicked on : " + mNewsItems.get(i).getTitle());

                //Muriel : Send URL to browser
                Uri uri = Uri.parse(mNewsItems.get(i).getUrl()); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: Inside");
        return mNewsItems.size();
    }

    public class NewsItemViewHolder extends RecyclerView.ViewHolder{
        private static final String TAG = "NewsItemViewHolder";
        CircleImageView newsImage;
        TextView newsTitle;
        TextView newsDescription;
        TextView newsDate;
        RelativeLayout newsItemLayout;

        public NewsItemViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "NewsItemViewHolder: constructor");
            newsImage = itemView.findViewById(R.id.newsimage);
            newsTitle = itemView.findViewById(R.id.title);
            newsDescription = itemView.findViewById(R.id.description);
            newsDate = itemView.findViewById(R.id.date);
            newsItemLayout = itemView.findViewById(R.id.parent_news_layout);
        }
    }
}