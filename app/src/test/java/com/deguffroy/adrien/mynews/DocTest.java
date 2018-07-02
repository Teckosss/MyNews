package com.deguffroy.adrien.mynews;

import com.deguffroy.adrien.mynews.Models.Search.Doc;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Adrien Deguffroy on 02/07/2018.
 */

public class DocTest {
    @Test
    public void createAndGetTest() throws Exception {
        Doc doc = new Doc("testWebURL",null,"testPubDate","testSectionName","testSubSectionName");

        assertEquals("testWebURL", doc.getWebUrl());
        assertEquals(null, doc.getHeadline());
        assertEquals("testPubDate", doc.getPubDate());
        assertEquals("testSectionName", doc.getSectionName());
        assertEquals("testSubSectionName", doc.getSubsectionName());

    }
}
