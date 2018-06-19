package com.deguffroy.adrien.mynews;

import android.support.test.runner.AndroidJUnit4;

import com.deguffroy.adrien.mynews.Models.NYTimesResultAPI;
import com.deguffroy.adrien.mynews.Utils.NYTimesStreams;

import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class StreamsTest {
    @Test
    public void checkHttpRequest() throws Exception {
        Observable<NYTimesResultAPI> observableArticles = NYTimesStreams.streamFetchTopStoriesNews("home");
        TestObserver<NYTimesResultAPI> testObserver = new TestObserver<>();
        observableArticles.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();
        NYTimesResultAPI articlesFetched = testObserver.values().get(0);
        assertEquals(true,articlesFetched.getResults().size() > 0);
    }
}
