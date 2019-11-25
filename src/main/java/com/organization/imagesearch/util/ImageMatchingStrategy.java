package com.organization.imagesearch.util;

import com.organization.imagesearch.exception.ImageMatcherException;
import com.organization.imagesearch.model.ImageTemplateMatcherDTO;

import java.io.File;
import java.io.IOException;

public interface ImageMatchingStrategy {

    /**
     * Compare the images
     * @param file1
     * @param threshold
     * @return
     * @throws ImageMatcherException
     */
    ImageTemplateMatcherDTO compare(File file1, Integer threshold) throws ImageMatcherException;
}
