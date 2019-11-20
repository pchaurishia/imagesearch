package com.organization.imagesearch.exception;

/**
 * Exception class to throw errors while saving the file
 */
public class FileStorageException extends Exception {
    public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}