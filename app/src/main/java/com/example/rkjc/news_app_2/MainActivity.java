package com.example.rkjc.news_app_2;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;

import com.example.rkjc.news_app_2.HW2.NewsItem;
import com.example.rkjc.news_app_2.HW2.NewsItemViewModel;
import com.example.rkjc.news_app_2.firebase.UpdateAttributes;

import java.util.List;


public class MainActivity extends AppCompatActivity implements NewsRecyclerViewAdapter.ListItemClickListener
{
    private RecyclerView rView;
    private NewsRecyclerViewAdapter adapter;
    NewsItemViewModel newsItemViewModel;

    //default
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsItemViewModel = ViewModelProviders.of(this).get(NewsItemViewModel.class);
        rView = findViewById(R.id.news_recyclerview);
        adapter=new NewsRecyclerViewAdapter(newsItemViewModel, MainActivity.this);
        rView.setAdapter(adapter);
        rView.setLayoutManager(new LinearLayoutManager(this));
        newsItemViewModel.getAllNewsItems().observe(this, new Observer<List<NewsItem>>() {
            @Override
            public void onChanged(@Nullable final List<NewsItem> newsItems) {
                adapter.setNewsItems(newsItems);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rView.setLayoutManager(layoutManager);
        rView.setHasFixedSize(true);
        UpdateAttributes.scheduleUpdate(this);
    }

//create menu default
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
//updates news when button clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.get_news) {
            newsItemViewModel.update();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //open webpage when clicked item
    @Override
    public void onListItemClick(int clickedItemIndex)
    {
        Uri web_page = Uri.parse(newsItemViewModel.getAllNewsItems().getValue().get(clickedItemIndex).getUrl());
        Intent intent = new Intent(Intent.ACTION_VIEW, web_page);
        if(intent.resolveActivity(getPackageManager()) != null)
        {
            startActivity(intent);
        }
    }




}
