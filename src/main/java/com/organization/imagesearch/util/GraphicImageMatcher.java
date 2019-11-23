package com.organization.imagesearch.util;

import com.organization.imagesearch.exception.MethodNotSupportedException;
import com.organization.imagesearch.model.ImageTemplateMatcherDTO;

import java.io.File;
import java.io.IOException;

public class GraphicImageMatcher implements  ImageMatchingStrategy {

    @Override
    public ImageTemplateMatcherDTO compare(File file1, Integer threshold) throws IOException {
        throw new MethodNotSupportedException("Currently Image Matching not supported");
    }
}
