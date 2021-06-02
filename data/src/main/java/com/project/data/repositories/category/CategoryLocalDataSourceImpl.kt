package com.project.data.repositories.category

import com.project.data.entities.Category
import com.project.data.mappers.category.CategoryEntityMapper
import com.project.data.db.category.CategoryDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class CategoryLocalDataSourceImpl(
    private val categoryDao: CategoryDao,
    private val dispatcher: CoroutineDispatcher,
    private val categoryEntityMapper: CategoryEntityMapper
) : CategoryLocalDataSource {

    override suspend fun category(category: Category) = withContext(dispatcher){
        categoryDao.insertCategories(categoryEntityMapper.toCategoryEntity(category))
    }

    override suspend fun unCategory(category: Category) = withContext(dispatcher){
        categoryDao.deleteCategory(categoryEntityMapper.toCategoryEntity(category))
    }

    override suspend fun getCategories(): Flow<List<Category>> {
        val savedFlow = categoryDao.getAllCategories()
        return savedFlow.map { list ->
            list.map { element ->
                categoryEntityMapper.toCategory(element)
            }
        }
    }
}