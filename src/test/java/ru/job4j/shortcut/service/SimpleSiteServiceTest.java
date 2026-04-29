package ru.job4j.shortcut.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import ru.job4j.shortcut.config.JwtUtil;
import ru.job4j.shortcut.dto.response.RegistrationResponseDTO;
import ru.job4j.shortcut.exception.UnauthorizedException;
import ru.job4j.shortcut.model.Site;
import ru.job4j.shortcut.repository.SiteRepository;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@ExtendWith(MockitoExtension.class)
class SimpleSiteServiceTest {

    @Mock
    private SiteRepository siteRepository;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private SimpleSiteService siteService;

    @Test
    void whenSiteNotExistsThenCreateAndSave() {
        Site site = new Site();
        site.setSite("test.com");
        when(siteRepository.findBySite("test.com"))
                .thenReturn(Optional.empty());

        RegistrationResponseDTO result = siteService.createSite(site);
        Assertions.assertTrue(result.isRegistration());
        Assertions.assertNotNull(result.getLogin());
        Assertions.assertNotNull(result.getPassword());
        verify(siteRepository).save(site);
    }

    @Test
    void whenSiteExistsThenReturnExistingData() {
        Site existing = new Site();
        existing.setSite("test.com");
        existing.setLogin("login");
        existing.setPassword("pass");
        when(siteRepository.findBySite("test.com"))
                .thenReturn(Optional.of(existing));
        RegistrationResponseDTO result = siteService.createSite(existing);
        Assertions.assertFalse(result.isRegistration());
        Assertions.assertEquals("login", result.getLogin());
        Assertions.assertEquals("pass", result.getPassword());
        verify(siteRepository, never()).save(any());
    }

    @Test
    void whenLoginSuccessThenReturnToken() {
        Site site = new Site();
        site.setLogin("user");
        site.setPassword("pass");
        Site existing = new Site();
        existing.setLogin("user");
        existing.setPassword("pass");
        when(siteRepository.findByLogin("user"))
                .thenReturn(Optional.of(existing));
        when(jwtUtil.generateToken("user"))
                .thenReturn("token123");
        ResponseEntity<?> response = siteService.login(site);
        Assertions.assertEquals(200, response.getStatusCodeValue());
        Assertions.assertTrue(response.getBody().toString().contains("token123"));
    }

    @Test
    void whenLoginWrongPasswordThenThrowException() {
        Site site = new Site();
        site.setLogin("user");
        site.setPassword("wrong");
        Site existing = new Site();
        existing.setLogin("user");
        existing.setPassword("pass");
        when(siteRepository.findByLogin("user"))
                .thenReturn(Optional.of(existing));
        assertThrows(UnauthorizedException.class,
                () -> siteService.login(site));
    }

    @Test
    void whenLoginUserNotFoundThenThrowException() {
        Site site = new Site();
        site.setLogin("user");
        site.setPassword("pass");

        when(siteRepository.findByLogin("user"))
                .thenReturn(Optional.empty());

        assertThrows(UnauthorizedException.class,
                () -> siteService.login(site));
    }

    @Test
    void whenFindByLoginExistsThenReturnSite() {
        Site site = new Site();
        site.setLogin("user");

        when(siteRepository.findByLogin("user"))
                .thenReturn(Optional.of(site));

        Site result = siteService.findByLogin("user");

        Assertions.assertEquals("user", result.getLogin());
    }

    @Test
    void whenFindByLoginNotExistsThenThrowException() {
        when(siteRepository.findByLogin("user"))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> siteService.findByLogin("user"));
    }
}