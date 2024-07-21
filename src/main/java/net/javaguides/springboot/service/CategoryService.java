package net.javaguides.springboot.service;

import net.javaguides.springboot.model.Category;
import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    Category getCategoryById(int id);
}
