package com.organization.imagesearch.service;


import com.organization.imagesearch.exception.FileStorageException;
import com.organization.imagesearch.exception.ImageNotFoundException;
import com.organization.imagesearch.model.ImageSearchResponse;
import com.organization.imagesearch.model.ImageTemplateMatcherDTO;
import com.organization.imagesearch.properties.FileStorageProperties;
import com.organization.imagesearch.util.ImageTemplateMatcher;
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

@Service
public class ImageSearchServiceImpl implements ImageSearchService {

    private final Path fileStorageLocation;
    @Autowired
    private ImageTemplateMatcher imageTemplateMatcher;

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
    public ImageSearchResponse storeAndCompareFile(MultipartFile file, Double threshold) throws FileStorageException {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/downloadFile/")
                    .path(fileName)
                    .toUriString();

            File file1 = new File(this.fileStorageLocation.toString(), fileName);
//            ImageTemplateMatcher imageTemplateMatcher = new ImageTemplateMatcher();
            ImageTemplateMatcherDTO imageTemplateMatcherDTO= imageTemplateMatcher.compare(file1,threshold);

            return new ImageSearchResponse(fileName, fileDownloadUri,
                    file.getContentType(), file.getSize(),imageTemplateMatcherDTO.getPosition(),imageTemplateMatcherDTO.getPercentageMatch());

        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
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