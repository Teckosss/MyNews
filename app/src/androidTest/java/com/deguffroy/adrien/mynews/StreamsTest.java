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
        //2 - Create a new TestObserver
        TestObserver<NYTimesResultAPI> testObserver = new TestObserver<>();
        // 3 - Launch observable
        observableArticles.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();
        // 3.1 - Check if no errors .assertNoTimeout()
        // 3.2 - Check if no Timeout .awaitTerminalEvent();
        // 3.3 - Await the stream terminated before continue
        // 4 - Get list of user fetched
        NYTimesResultAPI articlesFetched = testObserver.values().get(0);
        //System.out.println(articlesFetched);
        // 5 - Verify if Jake Wharton follows only 12 users...
        assertEquals(true,articlesFetched.getResults().size() > 0);
    }
}
