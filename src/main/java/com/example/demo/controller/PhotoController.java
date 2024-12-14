package com.example.demo.controller;

import com.example.demo.model.Photo;
import com.example.demo.services.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/photos")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @GetMapping
    public String listPhotos(Model model,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "5") int size) {
        Page<Photo> photos = photoService.getPhotos(PageRequest.of(page, size));
        model.addAttribute("photos", photos.getContent());
        model.addAttribute("totalPages", photos.getTotalPages());
        model.addAttribute("currentPage", page);
        return "photos";
    }

    @PostMapping("/upload")
    public String uploadPhoto(@RequestParam("title") String title,
                              @RequestParam("file") MultipartFile file) throws IOException {
        photoService.uploadPhoto(file, title);
        return "redirect:/photos";
    }

    @GetMapping("/{id}")
    @ResponseBody
    public byte[] getPhoto(@PathVariable Long id) {
        return photoService.getPhotoData(id);
    }

    @PostMapping("/delete/{id}")
    public String deletePhoto(@PathVariable Long id) {
        photoService.deletePhoto(id);
        return "redirect:/photos";
    }

    @PostMapping("/update/{id}")
    public String updatePhoto(@PathVariable Long id,
                              @RequestParam("title") String title,
                              @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        photoService.updatePhoto(id, title, file);
        return "redirect:/photos";
    }
}
