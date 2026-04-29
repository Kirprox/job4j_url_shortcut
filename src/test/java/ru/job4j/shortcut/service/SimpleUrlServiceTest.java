package ru.job4j.shortcut.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.job4j.shortcut.dto.request.UrlRequestDto;
import ru.job4j.shortcut.dto.response.ShortUrlResponseDTO;
import ru.job4j.shortcut.dto.response.StatisticResponceDTO;
import ru.job4j.shortcut.model.Site;
import ru.job4j.shortcut.model.Url;
import ru.job4j.shortcut.repository.UrlRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class SimpleUrlServiceTest {

    @Mock
    private UrlRepository urlRepository;

    @Mock
    private SiteService siteService;

    @InjectMocks
    private SimpleUrlService urlService;

    @BeforeEach
    void setupSecurity() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn("user");

        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(context);
    }

    @Test
    void whenUrlNotExistsThenCreateNew() {
        UrlRequestDto dto = new UrlRequestDto("http://test.com");

        Site site = new Site();
        site.setLogin("user");

        when(siteService.findByLogin("user")).thenReturn(site);
        when(urlRepository.findByOriginalUrl(dto.getUrl()))
                .thenReturn(Optional.empty());

        ShortUrlResponseDTO result = urlService.convert(dto);

        Assertions.assertNotNull(result.getCode());
        verify(urlRepository).save(any(Url.class));
    }

    @Test
    void whenUrlExistsThenReturnExistingCode() {
        UrlRequestDto dto = new UrlRequestDto("http://test.com");

        Site site = new Site();
        site.setLogin("user");

        Url existingUrl = new Url();
        existingUrl.setCode("abc123");

        when(siteService.findByLogin("user")).thenReturn(site);
        when(urlRepository.findByOriginalUrl(dto.getUrl()))
                .thenReturn(Optional.of(existingUrl));

        ShortUrlResponseDTO result = urlService.convert(dto);

        Assertions.assertEquals("abc123", result.getCode());
        verify(urlRepository, never()).save(any());
    }

    @Test
    void whenGetStatisticThenReturnList() {
        Site site = new Site();
        site.setLogin("user");

        Url url = new Url();
        url.setOriginalUrl("http://test.com");
        url.setTotal(5);

        when(siteService.findByLogin("user")).thenReturn(site);
        when(urlRepository.findBySite(site))
                .thenReturn(List.of(url));

        List<StatisticResponceDTO> result = urlService.getStatistic();

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("http://test.com", result.get(0).getUrl());
        Assertions.assertEquals(5, result.get(0).getTotal());
    }
}