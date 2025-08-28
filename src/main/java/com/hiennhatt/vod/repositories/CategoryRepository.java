package com.hiennhatt.vod.repositories;

import com.hiennhatt.vod.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findCategoryById(Integer id);
    Category findCategoryBySlug(String slug);
    Set<Category> findCategoriesByNameLike(String name);
}
