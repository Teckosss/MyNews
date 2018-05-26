package com.deguffroy.adrien.mynews;

import com.deguffroy.adrien.mynews.Models.TopStories.ResultTopStories;
import com.deguffroy.adrien.mynews.Models.TopStories.TopStoriesNews;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TopStoriesUnitTest {

    @Test
    public void createTopStories() throws Exception {
        List<ResultTopStories> result = null;

        TopStoriesNews topStoriesNews = new TopStoriesNews("ok","NYTimes","home","2018-05-26",1, result);

        assertEquals("ok", topStoriesNews.getStatus());
        assertEquals("NYTimes", topStoriesNews.getCopyright());
        assertEquals("home", topStoriesNews.getSection());
        assertEquals("2018-05-26", topStoriesNews.getLastUpdated());
        assertEquals(1, topStoriesNews.getNumResults(),0.001);
        assertEquals(null, topStoriesNews.getResults());
    }

    @Test
    public void setTopStories() throws Exception {
        List<ResultTopStories> result = new ArrayList<>();

        ResultTopStories resultTopStories = new ResultTopStories("","","","","","","","","","","","",null,null,null,null,null,"");

        TopStoriesNews topStoriesNews = new TopStoriesNews("","","","",0, result);

        result.add(resultTopStories);

        topStoriesNews.setStatus("ok");
        topStoriesNews.setCopyright("NYTimes");
        topStoriesNews.setSection("home");
        topStoriesNews.setLastUpdated("2018-05-26");
        topStoriesNews.setNumResults(1);
        topStoriesNews.setResults(result);

        assertEquals("ok", topStoriesNews.getStatus());
        assertEquals("NYTimes", topStoriesNews.getCopyright());
        assertEquals("home", topStoriesNews.getSection());
        assertEquals("2018-05-26", topStoriesNews.getLastUpdated());
        assertEquals(1, topStoriesNews.getNumResults(),0.001);
        assertEquals(result, topStoriesNews.getResults());
    }
}