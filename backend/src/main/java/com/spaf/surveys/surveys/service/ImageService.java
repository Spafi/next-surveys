package com.spaf.surveys.surveys.service;


import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageService {

    public byte[] getImageWithMediaType(String imageName) throws IOException {
        Path destination = Paths.get("C:\\Users\\spafi\\IdeaProjects\\online-surveys\\src\\main\\java\\com\\spaf\\jwt\\jwt101\\surveys\\images\\" + imageName);// retrieve the image by its name

        return IOUtils.toByteArray(destination.toUri());
    }
}
