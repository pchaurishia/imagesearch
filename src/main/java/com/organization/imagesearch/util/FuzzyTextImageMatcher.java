package com.organization.imagesearch.util;

import com.organization.imagesearch.exception.ImageMatcherException;
import com.organization.imagesearch.model.ImageTemplateMatcherDTO;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.apache.commons.text.similarity.LongestCommonSubsequence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Fuzzy search the text image
 * @author Priyanka
 */
@Service
public class FuzzyTextImageMatcher implements  ImageMatchingStrategy{

    private static final Logger log = LoggerFactory.getLogger(FuzzyTextImageMatcher.class);
    public static final int CAT_POSITION_START=0;
    public static final int CAT_POSITION_END=14;

    public ImageTemplateMatcherDTO compare(File file1, Integer threshold) throws ImageMatcherException {

        //validations
        if(file1==null){
            throw new ImageMatcherException("Input file provided is null");
        }

        //read the template file
        File file2 = getTemplateFile();

        if(threshold !=null && threshold<0){
            throw new ImageMatcherException("Invalid threshold value.");
        }
        String content1 = null;
        try {
            content1 = FileUtils.readFileToString(file1, "UTF-8");
        } catch (IOException e) {
            throw new ImageMatcherException("Error in reading the content of input file");
        }
        String content2 = null;
        try {
            content2 = FileUtils.readFileToString(file2, "UTF-8");
        } catch (IOException e) {
            throw new ImageMatcherException("Error in reading the content of template file");
        }

        log.info("content1="+content1);
        log.info("content2="+content2);

        double percentageMatch  =similarity(content1,content2,threshold)*100;

        long position = getPosition(content1, content2,CAT_POSITION_START,CAT_POSITION_END);

        return new ImageTemplateMatcherDTO(position,percentageMatch);
    }

    protected long getPosition(String content1, String content2,int start,int end) {
        CharSequence longestCommonSubsequence = new LongestCommonSubsequence().longestCommonSubsequence(content1, content2);
        if(end==-1){
            end=1;
        }
        if(longestCommonSubsequence.length()>=1){
            return StringUtils.indexOf(content1,longestCommonSubsequence.subSequence(start,end));
        }else{
            return -1;
        }
    }

    private File getTemplateFile() throws ImageMatcherException {
        File file2 = null;
        try {
            file2 = ResourceUtils.getFile("classpath:static/CatFace perfect_cat_image.txt");
        } catch (FileNotFoundException e) {
            throw new ImageMatcherException("Template file in the server is null");
        }
        return file2;
    }

    /**
     * Compare two contents using apache commons text Levenshtein Distance
     * @param s1
     * @param s2
     * @param threshold
     * @return
     */
    protected double similarity(String s1, String s2,Integer threshold) {
        String longer = s1, shorter = s2;
        if (s1.length() < s2.length()) { // longer should always have greater length
            longer = s2;
            shorter = s1;
        }
        int longerLength = longer.length();
        if (longerLength == 0) {
            return 1.0; /* both strings are zero length */
        }
        // using Apache Commons Text to calculate the edit distance:
        LevenshteinDistance levenshteinDistance = new LevenshteinDistance(threshold);

        int len =levenshteinDistance.apply(longer, shorter);
        return (longerLength - len) / (double) longerLength;
//        return (len) / (double) (longer.length()+shorter.length());

    }
}
