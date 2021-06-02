package com.project.data.usecases.category

import com.project.data.repositories.CategoryRepository

class GetCategoryUseCase(private val categoryRepository: CategoryRepository) {
    suspend operator fun invoke() = categoryRepository.getCategories()
}