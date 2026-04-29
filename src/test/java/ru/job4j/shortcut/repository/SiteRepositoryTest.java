package ru.job4j.shortcut.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.job4j.shortcut.model.Site;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@DataJpaTest
class SiteRepositoryTest {

    @Autowired
    private SiteRepository siteRepository;

    @Test
    void whenSaveSiteThenFindByLogin() {
        Site site = new Site();
        site.setSite("test.com");
        site.setLogin("user1");
        site.setPassword("pass");

        siteRepository.save(site);

        Optional<Site> result = siteRepository.findByLogin("user1");

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("test.com", result.get().getSite());
    }
}