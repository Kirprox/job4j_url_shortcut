package ru.job4j.shortcut.service;

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
import java.util.UUID;
@Transactional
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
        Url existingUrl = urlRepository.findByOriginalUrl(requestDto.getUrl());
        if (existingUrl != null) {
            code = existingUrl.getCode();
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

    @Override
    public RedirectResponseDTO redirect(String code) {
        Url existingUrl = urlRepository.findByCode(code);
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
