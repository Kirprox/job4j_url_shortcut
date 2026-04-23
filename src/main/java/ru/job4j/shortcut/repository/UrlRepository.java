package ru.job4j.shortcut.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.job4j.shortcut.model.Site;
import ru.job4j.shortcut.model.Url;

import java.util.List;

public interface UrlRepository extends JpaRepository<Url, Long> {
    Url findByOriginalUrl(String url);

    Url findByCode(String code);

    @Modifying
    @Query("update Url u set u.total = u.total + 1 where u.code = :code")
    void incrementTotalByCode(String code);

    List<Url> findBySite(Site existingSite);
}

