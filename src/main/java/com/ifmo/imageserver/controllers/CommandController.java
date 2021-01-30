package com.ifmo.imageserver.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifmo.imageserver.Utils.FileWork;
import com.ifmo.imageserver.Utils.MyComponent;
import com.ifmo.imageserver.entity.Image;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;

@RestController
@RequestMapping(value = "/command")
public class CommandController {

    @GetMapping
    public BufferedImage startCommand(@RequestParam int command, @RequestBody Image image) {
        String filePath = "C:\\images\\" + image.getFileName();
        System.out.println(filePath);
        MyComponent component = new MyComponent(filePath, command);
        return component.getTstimg();
    }
}
