package com.deguffroy.adrien.mynews.Utils;

import com.deguffroy.adrien.mynews.Models.TopStoriesNews;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Adrien Deguffroy on 21/05/2018.
 */

public class NYTimesStreams {

    public static Observable<List<TopStoriesNews>> streamFetchTopStoriesNews(String section){
        NYTimesService nyTimesService = NYTimesService.retrofit.create(NYTimesService.class);
        return nyTimesService.getTopStoriesNews(section)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}
