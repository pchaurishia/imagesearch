package com.organization.imagesearch.controller;

import com.organization.imagesearch.exception.FileStorageException;
import com.organization.imagesearch.model.ImageSearchResponse;
import com.organization.imagesearch.service.ImageSearchService;
import com.organization.imagesearch.util.ErrorMessage;
import com.organization.imagesearch.util.ImageSearchErrorCodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ImageSearchController {

    private static final Logger logger = LoggerFactory.getLogger(ImageSearchController.class);

    @Autowired
    private ImageSearchService imageSearchService;

    @PostMapping("/uploadFile")
    public ImageSearchResponse uploadFile(@RequestParam("file") MultipartFile file, @RequestParam(value = "threshold", required = false) Double threshold) throws FileStorageException {

        return imageSearchService.storeFile(file,threshold);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    ErrorMessage exceptionHandler(FileStorageException e){
        return new ErrorMessage(ImageSearchErrorCodes.FILE_STORAGE_ERROR.getErrorCode(),e.getLocalizedMessage());
    }

//    @PostMapping("/uploadMultipleFiles")
//    public List<ImageSearchResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files,
//                                                         @RequestParam(value = "threshold", required = false) Double threshold)  {
//        return Arrays.asList(files)
//                .stream()
//                .map(file -> uploadFile(file, threshold))
//                .collect(Collectors.toList());
//    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = imageSearchService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}