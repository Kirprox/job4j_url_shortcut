package ru.job4j.shortcut.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.shortcut.model.Site;

import java.util.Optional;

public interface SiteRepository extends JpaRepository<Site, Long> {
    Optional<Site> findBySite(String url);

    Optional<Site> findByLogin(String login);
}
