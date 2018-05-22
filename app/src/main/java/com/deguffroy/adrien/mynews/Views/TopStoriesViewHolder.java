package com.deguffroy.adrien.mynews.Views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.deguffroy.adrien.mynews.Models.ResultTopStories;
import com.deguffroy.adrien.mynews.R;

import java.text.DateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Adrien Deguffroy on 21/05/2018.
 */

public class TopStoriesViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.fragment_top_stories_image) ImageView mImageView;
    @BindView(R.id.fragment_top_stories_section) TextView mTextViewSection;
    @BindView(R.id.fragment_top_stories_date) TextView mTextViewDate;
    @BindView(R.id.fragment_top_stories_title) TextView mTextViewTitle;

    public TopStoriesViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void updateWithTopStoriesNews(ResultTopStories resultTopStories){

        if (resultTopStories.getSubsection().equals("")){
            this.mTextViewSection.setText(resultTopStories.getSection());
        }else{
            this.mTextViewSection.setText(resultTopStories.getSection() + " > " + resultTopStories.getSubsection());
        }
        Date date = new Date();
        this.mTextViewDate.setText(DateFormat.getDateInstance(DateFormat.SHORT).format(date));
        this.mTextViewTitle.setText(resultTopStories.getTitle());
    }
}
