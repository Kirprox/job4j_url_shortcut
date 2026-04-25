package ru.job4j.shortcut.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.job4j.shortcut.dto.request.UrlRequestDto;
import ru.job4j.shortcut.dto.response.RedirectResponseDTO;
import ru.job4j.shortcut.dto.response.ShortUrlResponseDTO;
import ru.job4j.shortcut.dto.response.StatisticResponceDTO;
import ru.job4j.shortcut.model.Site;
import ru.job4j.shortcut.model.Url;
import ru.job4j.shortcut.repository.UrlRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SimpleUrlService implements UrlService {
    private final UrlRepository urlRepository;
    private final SiteService siteService;

    public SimpleUrlService(UrlRepository urlRepository, SiteService siteService) {
        this.urlRepository = urlRepository;
        this.siteService = siteService;
    }

    @Override
    public ShortUrlResponseDTO convert(UrlRequestDto requestDto) {
        String code;
        String login = getLogin();
        Site existingSite = siteService.findByLogin(login);
        Optional<Url> existingUrl = urlRepository.findByOriginalUrl(requestDto.getUrl());

        if (existingUrl.isPresent()) {
            code = existingUrl.get().getCode();
        } else {
            code = generateCode();
            Url urlEntity = new Url(
                    requestDto.getUrl(),
                    code,
                    existingSite
            );
            urlRepository.save(urlEntity);
        }
        return new ShortUrlResponseDTO(code);
    }

    @Transactional
    @Override
    public RedirectResponseDTO redirect(String code) {
        var existingUrl = urlRepository.findByCode(code)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Not found Url by code:" + code
                ));
        urlRepository.incrementTotalByCode(code);
        return new RedirectResponseDTO(existingUrl.getOriginalUrl());
    }

    @Override
    public List<StatisticResponceDTO> getStatistic() {
        String login = getLogin();
        Site existingSite = siteService.findByLogin(login);
        return urlRepository.findBySite(existingSite).stream()
                .map(url -> new StatisticResponceDTO(
                        url.getOriginalUrl(), url.getTotal()
                )).toList();
    }

    private String getLogin() {
        return (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }

    private String generateCode() {
        return UUID.randomUUID().toString()
                .replace("-", "").substring(0, 7);
    }
}
