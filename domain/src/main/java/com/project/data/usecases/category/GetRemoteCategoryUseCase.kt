package com.project.data.usecases.category

import com.project.data.repositories.CategoryRepository

class GetRemoteCategoryUseCase(private val categoryRepository: CategoryRepository) {
    suspend operator fun invoke() = categoryRepository.getRemoteCategories()
}