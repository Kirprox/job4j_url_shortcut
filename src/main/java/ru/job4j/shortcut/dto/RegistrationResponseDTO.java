package ru.job4j.shortcut.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistrationResponseDTO {
    boolean registration;
    String login;
    String password;
}
