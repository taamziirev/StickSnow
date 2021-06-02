package com.project.data.usecases.category

import com.project.data.entities.Category
import com.project.data.repositories.CategoryRepository

class UnCategoryUseCase(private val categoryRepository: CategoryRepository) {
    suspend operator fun invoke(category: Category) = categoryRepository.unCategory(category)
}