package ru.job4j.shortcut.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.shortcut.dto.request.UrlRequestDto;
import ru.job4j.shortcut.dto.response.RedirectResponseDTO;
import ru.job4j.shortcut.dto.response.ShortUrlResponseDTO;
import ru.job4j.shortcut.dto.response.StatisticResponceDTO;
import ru.job4j.shortcut.service.UrlService;

import java.util.List;

@RestController
public class UrlController {
    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/convert")
    public ResponseEntity<ShortUrlResponseDTO> convertUrl(@RequestBody UrlRequestDto urlToConvert) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(urlService.convert(urlToConvert));
    }

    @GetMapping("/redirect/{code}")
    public ResponseEntity<Void> redirectUrl(@PathVariable String code) {
        String url = urlService.redirect(code).getUrl();
        System.out.println(url);
        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", url)
                .build();
    }

    @GetMapping("/statistic")
    public ResponseEntity<List<StatisticResponceDTO>> getStatistic() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(urlService.getStatistic());
    }
}
