package com.project.data.repositories.category

import com.project.data.common.Result
import com.project.data.entities.Category

interface CategoryRemoteDataSource {
    suspend fun getRemoteCategories(): Result<List<Category>>
}