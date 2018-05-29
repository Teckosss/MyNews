package com.deguffroy.adrien.mynews.Views;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.deguffroy.adrien.mynews.Models.MostPopular.ResultMostPopular;
import com.deguffroy.adrien.mynews.Models.TopStories.ResultTopStories;
import com.deguffroy.adrien.mynews.Models.TopStories.TopStoriesNews;
import com.deguffroy.adrien.mynews.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Adrien Deguffroy on 21/05/2018.
 */

public class NewsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.fragment_main_item_image) ImageView mImageView;
    @BindView(R.id.fragment_main_item_section) TextView mTextViewSection;
    @BindView(R.id.fragment_main_item_date) TextView mTextViewDate;
    @BindView(R.id.fragment_main_item_title) TextView mTextViewTitle;

    public NewsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    /*public void updateWithTopStoriesNews(ResultTopStories resultTopStories, RequestManager glide){

        if (!resultTopStories.getMultimedia().isEmpty()){
            glide.load(resultTopStories.getMultimedia().get(1).getUrl()).apply(RequestOptions.centerCropTransform()).into(mImageView);
        }
        if (resultTopStories.getSubsection().equals("")){
            this.mTextViewSection.setText(resultTopStories.getSection());
        }else{
            this.mTextViewSection.setText(resultTopStories.getSection() + " > " + resultTopStories.getSubsection());
        }
        this.mTextViewDate.setText(formatDate(resultTopStories.getPublishedDate()));
        this.mTextViewTitle.setText(resultTopStories.getTitle());
    }

   /* public void updateWithMostPopularNews(ResultMostPopular result, RequestManager glide){

        if (!result.getUrl().isEmpty()) {
            glide.load(result.getUrl()).apply(RequestOptions.fitCenterTransform()).into(mImageView);
        }
        this.mTextViewSection.setText(result.getSection());
        this.mTextViewDate.setText(formatDate(result.getPublishedDate()));
        this.mTextViewTitle.setText(result.getTitle());
    }

    */

    public <T> void updateWithData(T result, RequestManager glide){
        if (result instanceof ResultTopStories){
            ResultTopStories results = ((ResultTopStories)result);
            if (!results.getMultimedia().isEmpty()){
                glide.load(results.getMultimedia().get(1).getUrl()).apply(RequestOptions.centerCropTransform()).into(mImageView);
            }
            if (results.getSubsection().equals("")){
                this.mTextViewSection.setText(results.getSection());
            }else{
                this.mTextViewSection.setText(results.getSection() + " > " + results.getSubsection());
            }
            this.mTextViewDate.setText(formatDate(results.getPublishedDate()));
            this.mTextViewTitle.setText(results.getTitle());
        }
    }

    private String formatDate(String dateString) {
        try {
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ" );
            Date d = sd.parse(dateString);
            sd = new SimpleDateFormat("dd/MM/yy");
            return sd.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
}
