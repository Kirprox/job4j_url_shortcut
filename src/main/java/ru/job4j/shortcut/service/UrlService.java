package ru.job4j.shortcut.service;

import ru.job4j.shortcut.model.Url;

public interface UrlService {
    Url convert(Url urlToConvert);

    Url redirect(String code);

    Url getStatistic();
}
