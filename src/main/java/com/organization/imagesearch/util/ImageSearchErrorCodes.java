package com.organization.imagesearch.util;

public enum ImageSearchErrorCodes {

    FILE_STORAGE_ERROR("100");

    String errorCode;
    ImageSearchErrorCodes(String errorCode){
        this.errorCode=errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
