/*package org.example.controller;

import org.example.service.ClickUpService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clickup")
public class ClickUpController {

    private final ClickUpService clickUpService;

    public ClickUpController(ClickUpService clickUpService) {
        this.clickUpService = clickUpService;
    }

    @GetMapping("/sync-teams")
    public ResponseEntity<String> syncTeams() {
        clickUpService.fetchAndSaveTeams();
        return ResponseEntity.ok("ClickUp takımları başarıyla çekildi ve veritabanına kaydedildi!");
    }
} */