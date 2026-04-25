package ru.job4j.shortcut.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.shortcut.dto.response.RegistrationResponseDTO;
import ru.job4j.shortcut.model.Site;
import ru.job4j.shortcut.service.SiteService;

@RestController
public class SiteController {
    private final SiteService siteService;

    public SiteController(SiteService siteService) {
        this.siteService = siteService;
    }

    @PostMapping("/registration")
    public ResponseEntity<RegistrationResponseDTO> createSite(@Valid @RequestBody Site siteToCreate) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(siteService.createSite(siteToCreate));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Site site) {
        return siteService.login(site);
    }
}
