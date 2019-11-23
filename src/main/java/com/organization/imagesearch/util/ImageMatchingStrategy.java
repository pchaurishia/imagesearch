package com.organization.imagesearch.util;

import com.organization.imagesearch.model.ImageTemplateMatcherDTO;

import java.io.File;
import java.io.IOException;

public interface ImageMatchingStrategy {

    ImageTemplateMatcherDTO compare(File file1, Integer threshold) throws IOException;
}
