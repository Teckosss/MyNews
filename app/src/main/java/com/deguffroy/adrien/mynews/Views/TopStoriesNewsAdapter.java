package com.deguffroy.adrien.mynews.Views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.deguffroy.adrien.mynews.Models.ResultTopStories;
import com.deguffroy.adrien.mynews.R;

import java.util.List;

/**
 * Created by Adrien Deguffroy on 21/05/2018.
 */

public class TopStoriesNewsAdapter extends RecyclerView.Adapter<TopStoriesViewHolder> {

    // FOR DATA
    private List<ResultTopStories> mResultTopStories;
    private RequestManager glide;

    // CONSTRUCTOR
    public TopStoriesNewsAdapter(List<ResultTopStories> resultTopStories, RequestManager glide) {
        this.mResultTopStories = resultTopStories;
        this.glide = glide;
    }

    @Override
    public TopStoriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_top_stories_item, parent,false);
        return new TopStoriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TopStoriesViewHolder viewHolder, int position) {
        viewHolder.updateWithTopStoriesNews(this.mResultTopStories.get(position), this.glide);
    }

    @Override
    public int getItemCount() {
        return this.mResultTopStories.size();
    }
}
