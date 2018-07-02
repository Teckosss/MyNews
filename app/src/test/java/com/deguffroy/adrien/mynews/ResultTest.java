package com.deguffroy.adrien.mynews;

import com.deguffroy.adrien.mynews.Models.News.Result;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Adrien Deguffroy on 02/07/2018.
 */

public class ResultTest {
    @Test
    public void createAndGetTest() throws Exception {
        Result result = new Result("testSection","testSubSection","testTitle","testUrl","testPublishedDate","testUrlImageMedia");

        assertEquals("testSection", result.getSection());
        assertEquals("testSubSection", result.getSubsection());
        assertEquals("testTitle", result.getTitle());
        assertEquals("testUrl", result.getUrl());
        assertEquals("testPublishedDate", result.getPublishedDate());
        assertEquals("testUrlImageMedia", result.getUrlImageMedia());
    }
}
