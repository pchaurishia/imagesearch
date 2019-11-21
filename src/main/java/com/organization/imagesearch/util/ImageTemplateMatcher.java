package com.organization.imagesearch.util;

import com.organization.imagesearch.model.ImageTemplateMatcherDTO;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.apache.commons.text.similarity.LongestCommonSubsequence;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;

@Service
public class ImageTemplateMatcher {
//    @Value("classpath:CatFace perfect_cat_image.txt")
//    Resource resourceFile;

    public ImageTemplateMatcherDTO compare(File file1, Integer threshold) throws IOException {
        File file2 = ResourceUtils.getFile("classpath:static/CatFace perfect_cat_image.txt");
        String content1 = FileUtils.readFileToString(file1, "UTF-8");
        String content2 = FileUtils.readFileToString(file2, "UTF-8");

        System.out.println("content1="+content1);
        System.out.println("content2="+content2);

        double percentageMatch  =similarity(content1,content2,threshold)*100;

        CharSequence longestCommonSubsequence = new LongestCommonSubsequence().longestCommonSubsequence(content1, content2);
        long position = StringUtils.indexOf(content1,longestCommonSubsequence.subSequence(0,14));

        return new ImageTemplateMatcherDTO(position,percentageMatch);
    }

    public double similarity(String s1, String s2,Integer threshold) {
        String longer = s1, shorter = s2;
        if (s1.length() < s2.length()) { // longer should always have greater length
            longer = s2;
            shorter = s1;
        }
        int longerLength = longer.length();
        if (longerLength == 0) {
            return 1.0; /* both strings are zero length */
        }
        // If you have Apache Commons Text, you can use it to calculate the edit distance:
        LevenshteinDistance levenshteinDistance = new LevenshteinDistance(threshold);

        int len =levenshteinDistance.apply(longer, shorter);
        return (longerLength - len) / (double) longerLength;

    }
}
