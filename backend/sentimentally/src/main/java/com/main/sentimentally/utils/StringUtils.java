package com.main.sentimentally.utils;

import org.springframework.stereotype.Component;

@Component
public class StringUtils {

	public static String joinWithComma(String[] input) {
		if (input == null || input.length == 0) {
			return "";
		}
		return String.join(", ", input);
	}

}
