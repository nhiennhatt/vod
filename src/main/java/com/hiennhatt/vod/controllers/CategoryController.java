package com.hiennhatt.vod.controllers;

import com.hiennhatt.vod.models.Category;
import com.hiennhatt.vod.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    List<Category> getCategories(@RequestParam(required = false) String q, @RequestParam(required = false) Integer page) {
        Pageable pageable = PageRequest.of((page == null || page < 0) ? 0 : page, 5);
        System.out.println(q);
        if (q == null || q.isBlank()) {
            return categoryService.getAllCategories(pageable);
        } else {
            return categoryService.getCategoriesByName(q, pageable);
        }
    }
}
