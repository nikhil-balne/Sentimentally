package com.main.sentimentally.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "brand")
@Getter
@Setter
@NoArgsConstructor
public class Brand {
    @Id
    private String id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column
    private String url;
}
