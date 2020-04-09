package com.btplanner.btripex.ui.event.eventimeline.model;

import android.graphics.Bitmap;

public class Image {

    private String imageId;
    private Bitmap imageData;

    public Image(String imageId, Bitmap imageData) {
        this.imageId = imageId;
        this.imageData = imageData;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public Bitmap getImageData() {
        return imageData;
    }

    public void setImageData(Bitmap imageData) {
        this.imageData = imageData;
    }
}
