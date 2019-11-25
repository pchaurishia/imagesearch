package com.organization.imagesearch.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ParameterizedTestForPosition {
    @Parameterized.Parameter(0)
    public String str1;
    @Parameterized.Parameter(1)
    public String str2;
    @Parameterized.Parameter(2)
    public long result;
    // creates the test data
    @Parameterized.Parameters(name = "{index}: Test with m1={0}, m2 ={1}, result is:{2} ")
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][]{{"BCAD", "XYZ", -1},
                {"BACD", "A", 1}, {"ABCD", "A", 0},
                {"ABCD", "AC", 0},{"AAAAAA   BCCD", "ACE", 0},
                {"ABCDAD", "AD", 0}};
        return Arrays.asList(data);
    }
    @Test
    public void positionTest() {
        FuzzyTextImageMatcher fuzzyTextImageMatcher = new FuzzyTextImageMatcher();
        assertEquals( "Results",result, fuzzyTextImageMatcher.getPosition(str1, str2,0,-1));
    }
}