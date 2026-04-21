package ru.job4j.shortcut.service;

import ru.job4j.shortcut.dto.RegistrationResponseDTO;
import ru.job4j.shortcut.model.Site;

public interface SiteService {
    RegistrationResponseDTO createSite(Site siteToCreate);

    Site login(Site siteToLogin);

    Site findByLogin(String login);
}
