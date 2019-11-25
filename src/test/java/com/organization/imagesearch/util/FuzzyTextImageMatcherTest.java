package com.organization.imagesearch.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
//@RunWith(SpringRunner.class)

@RunWith(SpringRunner.class)
class FuzzyTextImageMatcherTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void testIfInputFileNull() {
        FuzzyTextImageMatcher imageMatcher  = new FuzzyTextImageMatcher();
        try {
            imageMatcher.compare(null, 85);
            fail();
        } catch (Exception e) {
            assertEquals(e.getLocalizedMessage(),"Input file provided is null");
        }
    }

    @Test
    void testNegativeThreshold() {
        FuzzyTextImageMatcher imageMatcher  = new FuzzyTextImageMatcher();
        try {
            File file= new File("text/txt");
            imageMatcher.compare(file, -85);
            fail();
        } catch (Exception e) {
            assertEquals("Invalid threshold value.",e.getLocalizedMessage());
        }
    }

    @Test
    void testEmptyInput() {
        FuzzyTextImageMatcher imageMatcher  = new FuzzyTextImageMatcher();
        try {
            File file= new File("text/txt");
            imageMatcher.compare(file, -85);
            fail();
        } catch (Exception e) {
            assertEquals("Invalid threshold value.",e.getLocalizedMessage());
        }
    }

    @Test
    void testGetPosition() {
        FuzzyTextImageMatcher imageMatcher  = new FuzzyTextImageMatcher();
        long position = imageMatcher.getPosition("BCAD","A",0,1);
        assertEquals(2,position);
    }

    @Test
    void testSimilarity() {
        FuzzyTextImageMatcher imageMatcher  = new FuzzyTextImageMatcher();
        double score = imageMatcher.similarity("ABCD","A",null);
        assertEquals(.25,score);
    }


}