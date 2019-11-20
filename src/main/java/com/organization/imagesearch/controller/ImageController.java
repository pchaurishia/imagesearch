package com.organization.imagesearch.controller;

import com.organization.imagesearch.model.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {



    @GetMapping("/message")
    Message send(){
        return new Message("content");
    }

    @PostMapping("/message")
    Message ping(@RequestBody Message message){
        return message;
    }
}
