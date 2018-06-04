package com.deguffroy.adrien.mynews.Views;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.deguffroy.adrien.mynews.Models.News.Result;
import com.deguffroy.adrien.mynews.Models.Search.Doc;
import com.deguffroy.adrien.mynews.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

    private final static String BASE_URL = "https://nytimes.com/";

    public NewsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public <T> void updateWithData(T result, RequestManager glide){
        if (result instanceof Result){
            Result results = ((Result)result);

            // FOR TOPSTORIES
            if ((results.getMultimedia() != null) && (!results.getMultimedia().isEmpty())){
                glide.load(results.getMultimedia().get(1).getUrl()).apply(RequestOptions.centerCropTransform()).into(mImageView);
            }
            // FOR MOSTPOPULAR
            else if( results.getUrlImageMedia() != null){
                glide.load(results.getUrlImageMedia()).apply(RequestOptions.centerCropTransform()).into(mImageView);
            }
            // NO IMAGE FOUND
            else{
                glide.load(R.drawable.ic_no_image_available).apply(RequestOptions.centerCropTransform()).into(mImageView);
            }

            this.mTextViewDate.setText(formatDate(results.getPublishedDate()));

            if ((results.getSubsection() != null) && (!results.getSubsection().isEmpty())){
                this.mTextViewSection.setText(results.getSection() + " > " + results.getSubsection());
            }else{
                this.mTextViewSection.setText(results.getSection());
            }

            this.mTextViewTitle.setText(results.getTitle());
        }
        else if (result instanceof Doc){
            Doc results = ((Doc)result);
            if (!results.getMultimedia().isEmpty()){
                String mUrl = BASE_URL + results.getMultimedia().get(0).getUrl();
                glide.load(mUrl).apply(RequestOptions.centerCropTransform()).into(mImageView);
            }else{
                glide.load(R.drawable.ic_no_image_available).apply(RequestOptions.centerCropTransform()).into(mImageView);
            }
            this.mTextViewSection.setText(results.getSectionName());
            this.mTextViewDate.setText(formatDate(results.getPubDate()));
            this.mTextViewTitle.setText(results.getHeadline().getMain());
        }else{
            Log.e("TAG", "updateWithData: No Instance Found" + result.getClass());
        }
    }

    private String formatDate(String dateString) {
        List<String> formatStrings = Arrays.asList("yyyy-MM-dd","yyyy-MM-dd'T'HH:mm:ssZ");

        for (String formatString : formatStrings)
        {
            try
            {
                SimpleDateFormat sd = null;
                Date d = new SimpleDateFormat(formatString).parse(dateString);
                sd = new SimpleDateFormat("dd/MM/yy");
                return sd.format(d);
            }
            catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return "";
    }
}
