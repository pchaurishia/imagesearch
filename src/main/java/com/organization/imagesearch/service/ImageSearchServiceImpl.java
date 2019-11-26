package com.organization.imagesearch.service;


import com.organization.imagesearch.exception.FileStorageException;
import com.organization.imagesearch.exception.ImageMatcherException;
import com.organization.imagesearch.exception.ImageNotFoundException;
import com.organization.imagesearch.model.ImageSearchResponse;
import com.organization.imagesearch.model.ImageTemplateMatcherDTO;
import com.organization.imagesearch.properties.FileStorageProperties;
import com.organization.imagesearch.util.FuzzyTextImageMatcher;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * This class is main class where file is stored in a persistant location and compared against the template file
 */
@Service
public class ImageSearchServiceImpl implements ImageSearchService {
    public static final String TXT = "txt";
    private final Path fileStorageLocation;
    @Autowired
    private FuzzyTextImageMatcher imageMatcher;

    @Autowired
    public ImageSearchServiceImpl(FileStorageProperties fileStorageProperties) throws FileStorageException {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Override
    public ImageSearchResponse storeAndCompareFile(MultipartFile file, Integer threshold) throws FileStorageException {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            validateFileExtension(fileName);
            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            File file1 = new File(this.fileStorageLocation.toString(), fileName);

            ImageTemplateMatcherDTO imageTemplateMatcherDTO= imageMatcher.compare(file1,threshold);

            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/downloadFile/")
                    .path(fileName)
                    .toUriString();

            return new ImageSearchResponse(fileName, fileDownloadUri,
                    file.getContentType(), file.getSize(),imageTemplateMatcherDTO.getPosition(),imageTemplateMatcherDTO.getPercentageMatch());

        } catch (IOException | ImageMatcherException ex) {
            ex.printStackTrace();
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    protected void validateFileExtension(String fileName) throws FileStorageException {
        String ext1 = FilenameUtils.getExtension(fileName);
        //If file extension is anything else apart from text throw error
        if(!org.apache.commons.lang3.StringUtils.equalsIgnoreCase(ext1, TXT)){
            throw new FileStorageException("Sorry! Only text files supported at this time " + fileName);
        }
    }

    @Override
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new ImageNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new ImageNotFoundException("File not found " + fileName, ex);
        }
    }
}