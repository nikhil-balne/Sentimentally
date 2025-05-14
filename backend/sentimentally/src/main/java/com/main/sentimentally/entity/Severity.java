package com.main.sentimentally.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "severity")
@Getter
@Setter
@NoArgsConstructor
public class Severity {

    @Id
    private Integer id;

    @Column(nullable = false, length = 50)
    private String name;
}
