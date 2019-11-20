package com.organization.imagesearch.model;

public class ImageSearchResponse {
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;

    private long position;
    private double percentageMatch;

    public ImageSearchResponse(String fileName, String fileDownloadUri, String fileType, long size, long position, double percentageMatch) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
        this.position = position;
        this.percentageMatch = percentageMatch;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileDownloadUri() {
        return fileDownloadUri;
    }

    public void setFileDownloadUri(String fileDownloadUri) {
        this.fileDownloadUri = fileDownloadUri;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public double getPercentageMatch() {
        return percentageMatch;
    }

    public void setPercentageMatch(double percentageMatch) {
        this.percentageMatch = percentageMatch;
    }

}