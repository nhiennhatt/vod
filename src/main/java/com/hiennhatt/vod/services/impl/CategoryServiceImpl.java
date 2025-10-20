package com.hiennhatt.vod.services.impl;

import com.hiennhatt.vod.models.Category;
import com.hiennhatt.vod.repositories.CategoryRepository;
import com.hiennhatt.vod.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories(Pageable pageable) {
        return categoryRepository.findAllBy(pageable);
    }

    @Override
    public List<Category> getCategoriesByName(String name, Pageable pageable) {
        return categoryRepository.searchCategoriesByNameLike("%" + name + "%", pageable);
    }
}
