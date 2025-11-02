package com.hiennhatt.vod.controllers;

import com.hiennhatt.vod.dtos.VideoOverviewDTO;
import com.hiennhatt.vod.models.Category;
import com.hiennhatt.vod.services.CategoryService;
import com.hiennhatt.vod.services.VideoCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private VideoCategoryService videoCategoryService;

    @GetMapping("")
    List<Category> getCategories(@RequestParam(required = false) String q, @RequestParam(required = false) Integer page) {
        Pageable pageable = PageRequest.of((page == null || page < 0) ? 0 : page, 5);
        if (q == null || q.isBlank()) {
            return categoryService.getAllCategories(pageable);
        } else {
            return categoryService.getCategoriesByName(q, pageable);
        }
    }

    @GetMapping("/{slug}")
    List<VideoOverviewDTO> getVideoByCategory(@PathVariable("slug") String slug, @RequestParam(required = false) Integer page) {
        Pageable pageable = PageRequest.of((page == null || page < 0) ? 0 : page, 6);
        List<VideoOverviewDTO> a = videoCategoryService.getVideosByCategory(slug, pageable).stream().map(VideoOverviewDTO::new).toList();
        System.out.println(a);
        return a;
    }
}
