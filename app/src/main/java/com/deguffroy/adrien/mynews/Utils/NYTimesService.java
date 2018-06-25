package com.deguffroy.adrien.mynews.Utils;

import android.support.annotation.Nullable;

import com.deguffroy.adrien.mynews.Models.APIResponseDeserializer;
import com.deguffroy.adrien.mynews.Models.NYTimesResultAPI;
import com.deguffroy.adrien.mynews.Models.News.Result;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Adrien Deguffroy on 21/05/2018.
 */

public interface NYTimesService {

    Gson userDeserializer = new GsonBuilder().setLenient().registerTypeAdapter(Result.class, new APIResponseDeserializer()).create();

    String API_KEY = "api-key=70181eda313a4fc7bf8141b72d916516";

    @GET("topstories/v2/{section}.json?" + API_KEY)
    Observable<NYTimesResultAPI> getTopStoriesNews(@Path("section") String section);

    @GET("mostpopular/v2/mostviewed/{section}/{time-period}.json?" + API_KEY)
    Observable<NYTimesResultAPI> getMostPopularNews(@Path("section") String section, @Path("time-period") String timePeriod);

    @GET("search/v2/articlesearch.json?sort=newest&" + API_KEY)
    Observable<NYTimesResultAPI> getSearchResultFilterDate(@Query("q") String toSearch, @Nullable @Query("fq") List<String> filterQuery, @Nullable @Query("begin_date") String beginDate, @Nullable @Query("end_date") String endDate);



    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc/")
            .addConverterFactory(GsonConverterFactory.create(userDeserializer))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
}
