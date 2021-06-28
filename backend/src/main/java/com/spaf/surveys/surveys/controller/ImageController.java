package com.spaf.surveys.surveys.controller;

import com.spaf.surveys.surveys.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(path = "api/v1/images")
@AllArgsConstructor
@CrossOrigin
public class ImageController {

    @Autowired
    public ImageService imageService;

    @GetMapping(
            value = "getImage/{imageName:.+}",
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE}
    )
    public @ResponseBody
    byte[] getImageWithMediaType(@PathVariable(name = "imageName") String fileName) throws IOException {
        return imageService.getImageWithMediaType(fileName);
    }
}

