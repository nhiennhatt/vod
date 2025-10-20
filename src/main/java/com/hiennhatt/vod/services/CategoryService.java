package com.hiennhatt.vod.services;

import com.hiennhatt.vod.models.Category;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface CategoryService {
    List<Category> getAllCategories(Pageable pageable);
    List<Category> getCategoriesByName(String name, Pageable pageable);
}
