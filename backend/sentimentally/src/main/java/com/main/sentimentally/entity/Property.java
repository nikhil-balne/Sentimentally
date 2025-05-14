package com.main.sentimentally.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "property")
@Getter
@Setter
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    private String description;

    @Column(nullable = false, length = 255)
    private String coordinates;

    @Column(nullable = false, length = 255)
    private String state;

    @Column(nullable = false, length = 255)
    private String district;

    private String imageUrl;

    private String adminEmail;
}
