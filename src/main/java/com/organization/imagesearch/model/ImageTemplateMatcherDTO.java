package com.organization.imagesearch.model;

public class ImageTemplateMatcherDTO {
    private long position;
    private double percentageMatch;

    public ImageTemplateMatcherDTO() {
    }

    public ImageTemplateMatcherDTO(long position, double percentageMatch) {
        this.position = position;
        this.percentageMatch = percentageMatch;
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
