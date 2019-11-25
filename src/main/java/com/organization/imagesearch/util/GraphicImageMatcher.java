package com.organization.imagesearch.util;

import com.organization.imagesearch.exception.ImageMatcherException;
import com.organization.imagesearch.model.ImageTemplateMatcherDTO;

import java.io.File;

public class GraphicImageMatcher implements  ImageMatchingStrategy {

    @Override
    public ImageTemplateMatcherDTO compare(File file1, Integer threshold) throws ImageMatcherException {
        throw new ImageMatcherException("only text matching supported at the moment");
    }
}
