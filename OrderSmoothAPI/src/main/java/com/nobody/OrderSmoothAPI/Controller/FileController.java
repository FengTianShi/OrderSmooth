package com.nobody.OrderSmoothAPI.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {

    @PostMapping("/upload")
    public String upload(String name, MultipartFile file, HttpServletRequest request)
            throws IllegalStateException, IOException {
        System.out.println(name);
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getContentType());
        System.out.println("file size : " + file.getSize());

        String path = request.getServletContext().getRealPath("/upload/");
        System.out.println(path);
        saveFile(file, path);

        return "success";
    }

    private void saveFile(MultipartFile file, String path) throws IllegalStateException, IOException {
        File uploadPath = new File(path);
        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }

        file.transferTo(new File(path + file.getOriginalFilename()));
    }
}
