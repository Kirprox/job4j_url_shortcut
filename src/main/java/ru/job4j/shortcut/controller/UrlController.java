package ru.job4j.shortcut.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.shortcut.model.Url;
import ru.job4j.shortcut.service.UrlService;

@RestController
public class UrlController {
    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/convert")
    public ResponseEntity<Url> convertUrl(@RequestBody Url urlToConvert) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(urlService.convert(urlToConvert));
    }

    @GetMapping("/redirect/{code}")
    public ResponseEntity<Url> redirectUrl(@PathVariable("code") String code) {
        return ResponseEntity.status(302)
                .header("Location", "supreme")
                .body(urlService.redirect(code));
    }

    @GetMapping("/statistic")
    public ResponseEntity<Url> getStatistic() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(urlService.getStatistic());
    }
}
