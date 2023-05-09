package com.sber.sber.controller;

import com.sber.sber.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/photo")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @PostMapping("/upload/{id}")
    public ResponseEntity uploadPhoto(@PathVariable Long id,
                                      @RequestParam("file") MultipartFile file) throws IOException {
        photoService.uploadPhoto(id, file);
        return ResponseEntity.ok("Загрузка фотографии выполнена успешно!");
    }

    @GetMapping("/download")
    public ResponseEntity downloadPhoto(@RequestParam(name = "name") String originalName) {
        byte[] bytes = photoService.downloadPhoto(originalName);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_PNG)
                .body(bytes);
    }
}
