package ru.job4j.shortcut.service;

import ru.job4j.shortcut.dto.request.UrlRequestDto;
import ru.job4j.shortcut.dto.response.RedirectResponseDTO;
import ru.job4j.shortcut.dto.response.ShortUrlResponseDTO;
import ru.job4j.shortcut.dto.response.StatisticResponceDTO;

import java.util.List;

public interface UrlService {
    ShortUrlResponseDTO convert(UrlRequestDto urlToConvert);

    RedirectResponseDTO redirect(String code);

    List<StatisticResponceDTO> getStatistic();
}
