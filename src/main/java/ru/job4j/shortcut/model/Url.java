package ru.job4j.shortcut.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "url")
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String originalUrl;
    private String code;
    private int total;
    @ManyToOne()
    @JoinColumn(name = "site_id")
    private Site site;

    public Url(String originalUrl, String code, Site site) {
        this.originalUrl = originalUrl;
        this.code = code;
        this.site = site;
    }
}
