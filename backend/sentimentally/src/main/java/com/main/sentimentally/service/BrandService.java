package com.main.sentimentally.service;

import com.main.sentimentally.entity.Brand;
import com.main.sentimentally.repository.BrandRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BrandService {

	private final BrandRepository brandRepository;

	public List<Brand> getAllBrands() {
		return brandRepository.findAll();
	}

}
