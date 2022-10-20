package com.example.inzynierka.models;

import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public class ImageBytesList {
    private Set<MultipartFile> images;

    public Set<MultipartFile> getImages() {
        return images;
    }

    public void setImages(Set<MultipartFile> images) {
        this.images = images;
    }
}
