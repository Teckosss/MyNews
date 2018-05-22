package com.deguffroy.adrien.mynews.Utils;

import com.deguffroy.adrien.mynews.Models.ResultTopStories;
import com.deguffroy.adrien.mynews.Models.TopStoriesNews;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Adrien Deguffroy on 21/05/2018.
 */

public interface NYTimesService {

    @GET("topstories/v2/{section}.json?api-key=70181eda313a4fc7bf8141b72d916516")
    Observable<TopStoriesNews> getTopStoriesNews(@Path("section") String section);

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
}
