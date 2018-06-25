package com.deguffroy.adrien.mynews.Views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.deguffroy.adrien.mynews.Models.Search.Doc;
import com.deguffroy.adrien.mynews.Models.News.Result;
import com.deguffroy.adrien.mynews.R;

import java.util.List;

/**
 * Created by Adrien Deguffroy on 21/05/2018.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsViewHolder> {

    // FOR DATA
    private List mResults;


    // CONSTRUCTOR
    public NewsAdapter(List<Result> result) {
        this.mResults = result;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_main_item, parent,false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder viewHolder, int position) {
      viewHolder.updateWithData(this.mResults.get(position));
    }

    @Override
    public int getItemCount() {
        int itemCount = 0;
        if (mResults != null) itemCount = mResults.size();
        return itemCount;
    }

    public Result getUrl(int position){
        List<Result> result = mResults;
        return result.get(position);
    }

    public Doc getUrlFromSearch(int position){
        List<Doc> result = mResults;
        return result.get(position);
    }
}
