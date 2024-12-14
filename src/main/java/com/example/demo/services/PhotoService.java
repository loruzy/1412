package com.example.demo.services;

import com.example.demo.model.Photo;
import com.example.demo.repo.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    public Page<Photo> getPhotos(Pageable pageable) {
        return photoRepository.findAll(pageable);
    }

    public void uploadPhoto(MultipartFile file, String description) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File must not be empty");
        }
        Photo photo = new Photo();
        photo.setPhotoUrl(file.getOriginalFilename());
        photo.setData(file.getBytes());
        photo.setDescription(description);
        photoRepository.save(photo);
    }

    public byte[] getPhotoData(Long id) {
        Photo photo = photoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Photo with ID " + id + " not found"));
        return photo.getData();
    }

    public void deletePhoto(Long id) {
        if (!photoRepository.existsById(id)) {
            throw new RuntimeException("Photo with ID " + id + " not found");
        }
        photoRepository.deleteById(id);
    }

    public void updatePhoto(Long id, String description, MultipartFile file) throws IOException {
        Photo photo = photoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Photo with ID " + id + " not found"));
        photo.setDescription(description);
        if (file != null && !file.isEmpty()) {
            photo.setData(file.getBytes());
            photo.setPhotoUrl(file.getOriginalFilename());
        }
        photoRepository.save(photo);
    }
}
