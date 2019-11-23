package com.organization.imagesearch.util;

public enum ImageSearchErrorCodes {

    FILE_STORAGE_ERROR("100"),
    FILE_FORMAT_NOT_SUPPORTED_ERROR("101");

    String errorCode;
    ImageSearchErrorCodes(String errorCode){
        this.errorCode=errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
