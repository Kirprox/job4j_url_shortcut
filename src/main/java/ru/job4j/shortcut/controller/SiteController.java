package ru.job4j.shortcut.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.shortcut.model.Site;
import ru.job4j.shortcut.service.SiteService;

@RestController
public class SiteController {
    private final SiteService siteService;

    public SiteController(SiteService siteService) {
        this.siteService = siteService;
    }

    @PostMapping("/registration")
    public ResponseEntity<Site> createSite(@RequestBody Site siteToCreate) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(siteService.createSite(siteToCreate));
    }

    @PostMapping("/login")
    public ResponseEntity<Site> login(@RequestBody Site siteToLogin) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(siteService.login(siteToLogin));
    }
}
