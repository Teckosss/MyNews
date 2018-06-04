package com.deguffroy.adrien.mynews.Views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.deguffroy.adrien.mynews.Controllers.MainActivity;
import com.deguffroy.adrien.mynews.Models.Doc;
import com.deguffroy.adrien.mynews.Models.Response;
import com.deguffroy.adrien.mynews.Models.Result;
import com.deguffroy.adrien.mynews.Models.TopStories.ResultTopStories;
import com.deguffroy.adrien.mynews.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adrien Deguffroy on 21/05/2018.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsViewHolder> {

    // FOR DATA
    private RequestManager glide;
    private List mResults;


    // CONSTRUCTOR
    public NewsAdapter(List<Result> result, RequestManager glide ) {
        this.glide = glide;
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
      viewHolder.updateWithData(this.mResults.get(position), this.glide);
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
