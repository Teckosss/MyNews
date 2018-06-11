package com.deguffroy.adrien.mynews.Utils;

import com.deguffroy.adrien.mynews.Models.NYTimesResultAPI;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.Nullable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Adrien Deguffroy on 21/05/2018.
 */

public class NYTimesStreams {

    public static Observable<NYTimesResultAPI> streamFetchTopStoriesNews(String section){
        NYTimesService nyTimesService = NYTimesService.retrofit.create(NYTimesService.class);
        return nyTimesService.getTopStoriesNews(section)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    public static Observable<NYTimesResultAPI> streamFetchMostPopularNews(String section, String timePeriod){
        NYTimesService nyTimesService = NYTimesService.retrofit.create(NYTimesService.class);
        return nyTimesService.getMostPopularNews(section, timePeriod)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    public static Observable<NYTimesResultAPI> streamFetchSearchResultFilterDate(String toSearch, @Nullable List<String> filterQuery, @Nullable String beginDate, @Nullable String endDate){
        NYTimesService nyTimesService = NYTimesService.retrofit.create(NYTimesService.class);
        return nyTimesService.getSearchResultFilterDate(toSearch, filterQuery, beginDate, endDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}
