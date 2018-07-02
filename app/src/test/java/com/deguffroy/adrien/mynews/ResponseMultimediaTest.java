package com.deguffroy.adrien.mynews;

import com.deguffroy.adrien.mynews.Models.Search.ResponseMultimedia;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Adrien Deguffroy on 02/07/2018.
 */

public class ResponseMultimediaTest {
    @Test
    public void getUrlTest() throws Exception {
        ResponseMultimedia responseMultimedia = new ResponseMultimedia("test");

        assertEquals("test", responseMultimedia.getUrl());

    }
}
