package com.main.sentimentally.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "input_feedback")
public class InputFeedback {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String feedbackText;

	@Column(nullable = false)
	private int rating;

	@ManyToOne
	@JoinColumn(name = "property_id", nullable = false)
	private Property property;

	@Column(nullable = false)
	private OffsetDateTime createdTsz;

}
