package com.project.data.usecases.category

import com.project.data.entities.Category
import com.project.data.repositories.CategoryRepository

class CategoryUseCase(private val categoryRepository: CategoryRepository) {
    suspend operator fun invoke(category: Category) = categoryRepository.category(category)
}