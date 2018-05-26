package com.deguffroy.adrien.mynews.Views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.deguffroy.adrien.mynews.Controllers.MainActivity;
import com.deguffroy.adrien.mynews.Models.MostPopular.ResultMostPopular;
import com.deguffroy.adrien.mynews.Models.TopStories.ResultTopStories;
import com.deguffroy.adrien.mynews.R;

import java.util.List;

/**
 * Created by Adrien Deguffroy on 21/05/2018.
 */

public class TopStoriesNewsAdapter extends RecyclerView.Adapter<TopStoriesViewHolder> {

    // FOR DATA
    private List<ResultTopStories> mResultTopStories;
    private List<ResultMostPopular> mResultMostPopulars;
    private RequestManager glide;
    private int identifierID;

    // CONSTRUCTOR
    public TopStoriesNewsAdapter(List result, RequestManager glide, int identifierID ) {
        if (identifierID == MainActivity.FRAGMENT_TOP_STORIES){
            this.mResultTopStories = result;
        }
        else if (identifierID == MainActivity.FRAGMENT_MOST_POPULAR){
            this.mResultMostPopulars = result;
        }

        this.glide = glide;
        this.identifierID = identifierID;
    }

    @Override
    public TopStoriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_main_item, parent,false);
        return new TopStoriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TopStoriesViewHolder viewHolder, int position) {
        if (identifierID == MainActivity.FRAGMENT_TOP_STORIES){
            viewHolder.updateWithTopStoriesNews(this.mResultTopStories.get(position), this.glide);
        }
        else if (identifierID == MainActivity.FRAGMENT_MOST_POPULAR){
            viewHolder.updateWithMostPopularNews(this.mResultMostPopulars.get(position), this.glide);
        }
        else if (identifierID == MainActivity.FRAGMENT_BUSINESS){

        }

    }

    @Override
    public int getItemCount() {
        int itemCount = 0;
        switch (identifierID){
            case MainActivity.FRAGMENT_TOP_STORIES:
                 itemCount = this.mResultTopStories.size();
                break;
            case MainActivity.FRAGMENT_MOST_POPULAR:
                itemCount = this.mResultMostPopulars.size();
                break;
            case MainActivity.FRAGMENT_BUSINESS:
                //itemCount = this.mResultTopStories.size();
                break;
        }
        return itemCount;
    }
}
