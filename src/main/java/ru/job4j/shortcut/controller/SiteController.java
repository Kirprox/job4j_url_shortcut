package ru.job4j.shortcut.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.shortcut.config.JwtUtil;
import ru.job4j.shortcut.dto.RegistrationResponseDTO;
import ru.job4j.shortcut.model.Site;
import ru.job4j.shortcut.service.SiteService;

import java.util.Map;

@RestController
public class SiteController {
    private final SiteService siteService;

    private final JwtUtil jwtUtil;

    public SiteController(SiteService siteService, JwtUtil jwtUtil) {
        this.siteService = siteService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/registration")
    public ResponseEntity<RegistrationResponseDTO> createSite(@Valid @RequestBody Site siteToCreate) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(siteService.createSite(siteToCreate));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Site site) {
        ResponseEntity<?> response;
        Site existingSite = siteService.findByLogin(site.getLogin());
        if (existingSite == null || !existingSite.getPassword().equals(site.getPassword())) {
            response = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            String token = jwtUtil.generateToken(existingSite.getLogin());
            response = ResponseEntity.ok(Map.of("token", token));
        }
        return response;
    }
}
