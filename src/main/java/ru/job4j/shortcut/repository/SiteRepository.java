package ru.job4j.shortcut.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.shortcut.model.Site;

public interface SiteRepository extends JpaRepository<Site, Long> {
}
