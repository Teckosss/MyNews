package com.deguffroy.adrien.mynews.Utils;

import com.deguffroy.adrien.mynews.Models.APIResponseDeserializer;
import com.deguffroy.adrien.mynews.Models.NYTimesResultAPI;
import com.deguffroy.adrien.mynews.Models.News.Result;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

    Gson userDeserializer = new GsonBuilder().setLenient().registerTypeAdapter(Result.class, new APIResponseDeserializer()).create();

    @GET("topstories/v2/{section}.json?api-key=70181eda313a4fc7bf8141b72d916516")
    Observable<NYTimesResultAPI> getTopStoriesNews(@Path("section") String section);

    @GET("mostpopular/v2/mostviewed/{section}/{time-period}.json?api-key=70181eda313a4fc7bf8141b72d916516")
    Observable<NYTimesResultAPI> getMostPopularNews(@Path("section") String section, @Path("time-period") String timePeriod);

    @GET("search/v2/articlesearch.json?q=business&sort=newest&api-key=70181eda313a4fc7bf8141b72d916516")
    Observable<NYTimesResultAPI> getBusinessNews();

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc/")
            .addConverterFactory(GsonConverterFactory.create(userDeserializer))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
}
