package com.organization.imagesearch.service;

import com.organization.imagesearch.exception.FileStorageException;
import com.organization.imagesearch.properties.FileStorageProperties;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class ImageSearchServiceImplTest {

    private ImageSearchServiceImpl imageSearchService;
    private FileStorageProperties fileStorageProperties;


    @BeforeEach
    public void setup() throws FileStorageException {
        fileStorageProperties = new FileStorageProperties();
        fileStorageProperties.setUploadDir("text");
        imageSearchService = new ImageSearchServiceImpl(fileStorageProperties);
    }

    @Test
    void validateGoodFileExtension() {
        try {
            imageSearchService.validateFileExtension("abc.txt");
        }catch(FileStorageException e){
            fail();
        }
    }

    @Test
    void validateBadFileExtension() {
        String fileName="abc.jpeg";
        try {
            imageSearchService.validateFileExtension("abc.jpeg");
            fail();
        }catch(FileStorageException e){
           assertEquals("Sorry! Only text files supported at this time " + fileName,e.getLocalizedMessage());
        }
    }
}