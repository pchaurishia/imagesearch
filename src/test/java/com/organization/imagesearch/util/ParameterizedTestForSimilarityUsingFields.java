package com.organization.imagesearch.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ParameterizedTestForSimilarityUsingFields {
    @Parameterized.Parameter(0)
    public String str1;
    @Parameterized.Parameter(1)
    public String str2;
    @Parameterized.Parameter(2)
    public double result;
    // creates the test data
    @Parameterized.Parameters(name = "{index}: Test with m1={0}, m2 ={1}, result is:{2} ")
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][]{{"AXYZ", "BCDEF", 0.0}, {"ABCD", "A", .25}, {"ABCD", "AC", .50},{"AAAAAA   BCCD", "AC", 0.15384615384615385}};
        return Arrays.asList(data);
    }
    @Test
    public void similarityTest() {
        FuzzyTextImageMatcher fuzzyTextImageMatcher = new FuzzyTextImageMatcher();
        assertEquals( "Results",result, fuzzyTextImageMatcher.similarity(str1, str2,null),0);
    }
}