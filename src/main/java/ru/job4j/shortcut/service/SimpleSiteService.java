package ru.job4j.shortcut.service;

import org.springframework.stereotype.Service;
import ru.job4j.shortcut.model.Site;

@Service
public class SimpleSiteService implements SiteService {
    @Override
    public Site createSite(Site siteToCreate) {
        return null;
    }

    @Override
    public Site login(Site siteToLogin) {
        return null;
    }
}
