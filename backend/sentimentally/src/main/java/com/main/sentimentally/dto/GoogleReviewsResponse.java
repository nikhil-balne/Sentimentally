package com.main.sentimentally.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoogleReviewsResponse {

	private String propertyId;

	private String userId;

	private String review;

	private int rating;

	private OffsetDateTime date;

}
