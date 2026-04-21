package ru.job4j.shortcut.service;

import org.springframework.stereotype.Service;
import ru.job4j.shortcut.dto.RegistrationResponseDTO;
import ru.job4j.shortcut.model.Site;
import ru.job4j.shortcut.repository.SiteRepository;

import java.util.Random;
import java.util.UUID;

@Service
public class SimpleSiteService implements SiteService {
    public SimpleSiteService(SiteRepository siteRepository) {
        this.siteRepository = siteRepository;
    }

    private final SiteRepository siteRepository;

    @Override
    public RegistrationResponseDTO createSite(Site siteToCreate) {
        String login;
        String password;
        boolean registration = true;
        Site existingSite = siteRepository.findBySite(siteToCreate.getSite());

        if (existingSite != null) {
            registration = false;
            login = existingSite.getLogin();
            password = existingSite.getPassword();

        } else {
            login = generateLogin();
            password = generatePassword();
            siteToCreate.setLogin(login);
            siteToCreate.setPassword(password);
            siteRepository.save(siteToCreate);
        }
        return new RegistrationResponseDTO(registration, login, password);
    }

    @Override
    public Site login(Site siteToLogin) {
        return null;
    }

    @Override
    public Site findByLogin(String login) {
        return siteRepository.findByLogin(login);
    }

    private String generateLogin() {
        String uuid = UUID.randomUUID().toString()
                .replace("-", "").substring(0, 10);
        return "user_" + uuid;
    }

    private String generatePassword() {
        Random random = new Random();
        return UUID.randomUUID().toString().replace("-", "")
                .substring(0, 10) + random.nextInt(500);
    }
}
