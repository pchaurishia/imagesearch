package com.organization.imagesearch.service;

import com.organization.imagesearch.exception.FileStorageException;
import com.organization.imagesearch.model.ImageSearchResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ImageSearchService {
    /**
     * Stores the file in server
     * @param file
     * @param threshold
     * @return ImageSearchResponse
     * @throws FileStorageException
     */
    ImageSearchResponse storeFile(MultipartFile file, Double threshold) throws FileStorageException;

    /**
     * Loads the file
     * @param fileName
     * @return
     */
    Resource loadFileAsResource(String fileName);
}
