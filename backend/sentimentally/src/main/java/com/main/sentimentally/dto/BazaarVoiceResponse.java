package com.main.sentimentally.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BazaarVoiceResponse {
        private String propertyId;
        //here author in google userId
        private String author;
        //here is it reviewData in google its review
        private String reviewData;
        private int rating;
        private OffsetDateTime date;
}
