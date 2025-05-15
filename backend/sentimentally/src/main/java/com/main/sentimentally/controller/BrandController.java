package com.main.sentimentally.controller;

import com.main.sentimentally.entity.Brand;
import com.main.sentimentally.service.BrandService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/brands")
@AllArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @GetMapping("/")
    public List<Brand> getAllBrands(){return brandService.getAllBrands();}
}
