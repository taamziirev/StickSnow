package com.project.data.repositories

import com.project.data.common.Result
import com.project.data.entities.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository{
    suspend fun getRemoteCategories(): Result<List<Category>>

    suspend fun getCategories(): Flow<List<Category>>

    suspend fun category(category: Category)

    suspend fun unCategory(category: Category)
}