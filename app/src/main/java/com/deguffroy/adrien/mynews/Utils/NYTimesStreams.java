package com.deguffroy.adrien.mynews.Utils;

import com.deguffroy.adrien.mynews.Models.NYTimesResultAPI;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
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

    public static Observable<NYTimesResultAPI> streamFetchBusinessNews(String toSearch){
        NYTimesService nyTimesService = NYTimesService.retrofit.create(NYTimesService.class);
        return nyTimesService.getBusinessNews(toSearch)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    public static Observable<NYTimesResultAPI> streamFetchSearchResult(String toSearch){
        NYTimesService nyTimesService = NYTimesService.retrofit.create(NYTimesService.class);
        return nyTimesService.getSearchResult(toSearch)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    public static Observable<NYTimesResultAPI> streamFetchSearchResultFilterQuery(String toSearch, List<String> filterQuery){
        NYTimesService nyTimesService = NYTimesService.retrofit.create(NYTimesService.class);
        return nyTimesService.getSearchResultFilterQuery(toSearch, filterQuery)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    public static Observable<NYTimesResultAPI> streamFetchSearchResultFilterDate(String toSearch, List<String> filterQuery, String beginDate, String endDate){
        NYTimesService nyTimesService = NYTimesService.retrofit.create(NYTimesService.class);
        return nyTimesService.getSearchResultFilterDate(toSearch, filterQuery, beginDate, endDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    public static Observable<NYTimesResultAPI> streamFetchSearchResultWithDate(String toSearch, String beginDate, String endDate){
        NYTimesService nyTimesService = NYTimesService.retrofit.create(NYTimesService.class);
        return nyTimesService.getSearchResultWithDate(toSearch, beginDate, endDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}
