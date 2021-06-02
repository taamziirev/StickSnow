package com.project.data.repositories.category

import com.project.data.entities.Category
import kotlinx.coroutines.flow.Flow

interface CategoryLocalDataSource {
    suspend fun category(category: Category)
    suspend fun unCategory(category: Category)
    suspend fun getCategories(): Flow<List<Category>>
}