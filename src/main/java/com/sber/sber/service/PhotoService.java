package com.sber.sber.service;

import com.sber.sber.entity.Human;
import com.sber.sber.entity.Photo;
import com.sber.sber.exception.ErrorMessages;
import com.sber.sber.exception.NotFoundException;
import com.sber.sber.repository.HumanRepository;
import com.sber.sber.repository.PhotoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;

@Service
@Slf4j
public class PhotoService {

    @Autowired
    private PhotoRepository photoRepository;
    @Autowired
    private HumanRepository humanRepository;

    public void uploadPhoto(Long id, MultipartFile file) throws IOException {
        Photo photo;
        Human human = humanRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorMessages.PERSON_NOT_FOUND));
        if (file.getSize() != 0) {
            photo = fileToPhoto(file);
            human.getPhotos().add(photo);
        }
        log.info("Сохраняем фотографию для пользователя {} с id: {}", human.getName(), human.getId());
        humanRepository.save(human);
    }

    @Transactional
    public byte[] downloadPhoto(String originalName) {
        return photoRepository.findByOriginalFileName(originalName).getBytes();
    }

    public Photo fileToPhoto(MultipartFile file) throws IOException {
        Photo photo = new Photo();
        photo.setName(file.getName());
        photo.setOriginalFileName(file.getOriginalFilename());
        photo.setSize(file.getSize());
        photo.setContentType(file.getContentType());
        photo.setBytes(file.getBytes());
        return photo;
    }
}
