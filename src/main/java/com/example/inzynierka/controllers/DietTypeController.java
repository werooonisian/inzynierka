package com.example.inzynierka.controllers;

import com.example.inzynierka.models.DietType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/dietType")
public class DietTypeController {
    @GetMapping("/all")
    public ResponseEntity<DietType[]> getAllDietTypes(){
        return ResponseEntity.ok().body(DietType.values());
    }
}
