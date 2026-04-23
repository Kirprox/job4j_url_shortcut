package ru.job4j.shortcut.service;

import org.springframework.http.ResponseEntity;
import ru.job4j.shortcut.dto.response.RegistrationResponseDTO;
import ru.job4j.shortcut.model.Site;

public interface SiteService {
    RegistrationResponseDTO createSite(Site siteToCreate);

    ResponseEntity<?> login(Site siteToLogin);

    Site findByLogin(String login);
}
