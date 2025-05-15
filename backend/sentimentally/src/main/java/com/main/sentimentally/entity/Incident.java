package com.main.sentimentally.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Table(name = "incident")
@Getter
@Setter
public class Incident {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String incidentText;

	@ManyToOne
	@JoinColumn(name = "severity_id", nullable = false)
	private Severity severity;

	@ManyToOne
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;

	@ManyToOne
	@JoinColumn(name = "property_id", nullable = false)
	private Property property;

	@Column(nullable = false)
	private Boolean isResolved = false;

	@Column(name = "created_tsz", nullable = false)
	private OffsetDateTime createdTsz;

}
