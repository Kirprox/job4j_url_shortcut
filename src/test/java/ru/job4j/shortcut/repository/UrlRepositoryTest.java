package ru.job4j.shortcut.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.job4j.shortcut.model.Site;
import ru.job4j.shortcut.model.Url;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@DataJpaTest
class UrlRepositoryTest {

    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private SiteRepository siteRepository;

    @Test
    void whenSaveUrlThenFindByOriginalUrl() {
        Site site = createSite();
        Url url = createUrl(site);
        Optional<Url> result = urlRepository.findByOriginalUrl(url.getOriginalUrl());
        Assertions.assertEquals("test.com", result.get().getOriginalUrl());
        Assertions.assertEquals("qwerty", result.get().getCode());
    }

    @Test
    void whenSaveUrlThenFindByCode() {
        Site site = createSite();
        Url url = createUrl(site);
        Optional<Url> result = urlRepository.findByCode(url.getCode());
        Assertions.assertEquals(url, result.get());
    }

    @Test
    void whenSaveUrlThenIncrementTotalByCode() {
        Site site = createSite();
        Url url = createUrl(site);
        urlRepository.incrementTotalByCode(url.getCode());
        Optional<Url> result = urlRepository.findByCode(url.getCode());
        Assertions.assertEquals(2, result.get().getTotal());
    }

    @Test
    void whenSaveUrlThenFindBySite() {
        Site site = createSite();
        Url url = createUrl(site);
        List<Url> result = urlRepository.findBySite(site);
        Assertions.assertEquals(url, result.get(0));
    }

    private Site createSite() {
        Site site = new Site();
        site.setSite("test.com");
        site.setLogin("user1");
        site.setPassword("pass");
        return siteRepository.save(site);
    }

    private Url createUrl(Site site) {
        Url url = new Url();
        url.setOriginalUrl("test.com");
        url.setCode("qwerty");
        url.setTotal(1);
        url.setSite(site);
        return urlRepository.save(url);
    }
}