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
	private String id;

	@Column(nullable = false, length = 100)
	private String name;

	@Column
	private String description;

	@Column(nullable = false, length = 50)
	private String coordinates;

	@ManyToOne
	@JoinColumn(name = "brand_id", nullable = false)
	private Brand brand;

	@Column(nullable = false)
	private String state;

	@Column(nullable = false)
	private String district;

	private String imageUrl;

	@Column
	private String adminEmail;

	@Column(nullable = false)
	private String incidentSummary;

	@Column(nullable = false)
	private String feedbackSummary;

	@Column(nullable = false)
	private int feedbackRating;

}
