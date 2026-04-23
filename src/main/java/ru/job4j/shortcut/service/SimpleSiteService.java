package ru.job4j.shortcut.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.job4j.shortcut.config.JwtUtil;
import ru.job4j.shortcut.dto.response.RegistrationResponseDTO;
import ru.job4j.shortcut.model.Site;
import ru.job4j.shortcut.repository.SiteRepository;

import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Service
public class SimpleSiteService implements SiteService {
    private final SiteRepository siteRepository;
    private final JwtUtil jwtUtil;

    public SimpleSiteService(SiteRepository siteRepository, JwtUtil jwtUtil) {
        this.siteRepository = siteRepository;
        this.jwtUtil = jwtUtil;
    }

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
    public ResponseEntity<?> login(Site site) {
        ResponseEntity<?> response;
        Site existingSite = siteRepository.findByLogin(site.getLogin());
        if (existingSite == null || !existingSite.getPassword().equals(site.getPassword())) {
            response = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            String token = jwtUtil.generateToken(existingSite.getLogin());
            response = ResponseEntity.ok(Map.of("token", token));
        }
        return response;
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
