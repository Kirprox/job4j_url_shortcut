package ru.job4j.shortcut.service;

import ru.job4j.shortcut.model.Site;

public interface SiteService {
    Site createSite(Site siteToCreate);

    Site login(Site siteToLogin);
}
