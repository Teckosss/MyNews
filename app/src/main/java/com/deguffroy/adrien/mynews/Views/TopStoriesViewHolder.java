package com.deguffroy.adrien.mynews.Views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.deguffroy.adrien.mynews.Models.MostPopular.ResultMostPopular;
import com.deguffroy.adrien.mynews.Models.TopStories.ResultTopStories;
import com.deguffroy.adrien.mynews.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Adrien Deguffroy on 21/05/2018.
 */

public class TopStoriesViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.fragment_main_item_image) ImageView mImageView;
    @BindView(R.id.fragment_main_item_section) TextView mTextViewSection;
    @BindView(R.id.fragment_main_item_date) TextView mTextViewDate;
    @BindView(R.id.fragment_main_item_title) TextView mTextViewTitle;

    public TopStoriesViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void updateWithTopStoriesNews(ResultTopStories resultTopStories, RequestManager glide){

        glide.load(resultTopStories.getMultimedia().get(0).getUrl()).apply(RequestOptions.fitCenterTransform()).into(mImageView);
        if (resultTopStories.getSubsection().equals("")){
            this.mTextViewSection.setText(resultTopStories.getSection());
        }else{
            this.mTextViewSection.setText(resultTopStories.getSection() + " > " + resultTopStories.getSubsection());
        }
        this.mTextViewDate.setText(formatDate(resultTopStories.getPublishedDate()));
        this.mTextViewTitle.setText(resultTopStories.getTitle());
    }

    public void updateWithMostPopularNews(ResultMostPopular result, RequestManager glide){

        glide.load(result.getUrl()).apply(RequestOptions.fitCenterTransform()).into(mImageView);
        this.mTextViewSection.setText(result.getSection());
        this.mTextViewDate.setText(formatDate(result.getPublishedDate()));
        this.mTextViewTitle.setText(result.getTitle());
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
