package com.main.sentimentally.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "incident")
public class Incident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
