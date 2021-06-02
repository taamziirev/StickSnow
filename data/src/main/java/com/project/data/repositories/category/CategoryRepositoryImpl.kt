package com.project.data.repositories.category

import com.project.data.common.Result
import com.project.data.entities.Category
import com.project.data.repositories.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryRepositoryImpl(
    private val localDataSource: CategoryLocalDataSource,
    private val remoteDataSource: CategoryRemoteDataSource
) : CategoryRepository {
    override suspend fun getRemoteCategories(): Result<List<Category>> {
        return remoteDataSource.getRemoteCategories()
    }

    override suspend fun getCategories(): Flow<List<Category>> {
        return localDataSource.getCategories()
    }

    override suspend fun category(category: Category) {
        localDataSource.category(category)
    }

    override suspend fun unCategory(category: Category) {
        localDataSource.category(category)
    }
}