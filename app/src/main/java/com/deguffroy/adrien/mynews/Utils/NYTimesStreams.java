package com.deguffroy.adrien.mynews.Utils;

import com.deguffroy.adrien.mynews.Models.MostPopular.MostPopularNews;
import com.deguffroy.adrien.mynews.Models.NYTimesResultAPI;
import com.deguffroy.adrien.mynews.Models.Search.SearchNews;
import com.deguffroy.adrien.mynews.Models.TopStories.TopStoriesNews;

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

    public static Observable<NYTimesResultAPI> streamFetchBusinessNews(){
        NYTimesService nyTimesService = NYTimesService.retrofit.create(NYTimesService.class);
        return nyTimesService.getBusinessNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}
