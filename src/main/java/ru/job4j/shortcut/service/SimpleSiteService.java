package ru.job4j.shortcut.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.job4j.shortcut.config.JwtUtil;
import ru.job4j.shortcut.dto.response.RegistrationResponseDTO;
import ru.job4j.shortcut.exception.UnauthorizedException;
import ru.job4j.shortcut.model.Site;
import ru.job4j.shortcut.repository.SiteRepository;

import java.util.Map;
import java.util.Optional;
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
        Optional<Site> existingSite = siteRepository.findBySite(siteToCreate.getSite());
        if (existingSite.isPresent()) {
            registration = false;
            login = existingSite.get().getLogin();
            password = existingSite.get().getPassword();

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
        Optional<Site> existingSite = siteRepository.findByLogin(site.getLogin());
        if (existingSite.isEmpty() || !existingSite.get().getPassword().equals(site.getPassword())) {
            throw new UnauthorizedException("Incorrect login or password");
        }
            String token = jwtUtil.generateToken(existingSite.get().getLogin());
            response = ResponseEntity.ok(Map.of("token", token));
        return response;
    }

    @Override
    public Site findByLogin(String login) {
        return siteRepository.findByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException("Not found Site by login "
                        + login));
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
