package ru.job4j.shortcut.service;

import org.springframework.stereotype.Service;
import ru.job4j.shortcut.model.Url;

@Service
public class SimpleUrlService implements UrlService {
    @Override
    public Url convert(Url urlToConvert) {
        return null;
    }

    @Override
    public Url redirect(String code) {
        return null;
    }

    @Override
    public Url getStatistic() {
        return null;
    }
}
