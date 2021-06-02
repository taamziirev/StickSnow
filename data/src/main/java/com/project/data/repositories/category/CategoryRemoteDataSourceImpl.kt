package com.project.data.repositories.category

import com.project.data.api.category.CategoryApi
import com.project.data.common.Result
import com.project.data.entities.Category
import com.project.data.mappers.category.CategoryApiResponseMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CategoryRemoteDataSourceImpl(
    private val service: CategoryApi,
    private val mapper: CategoryApiResponseMapper
) : CategoryRemoteDataSource {
    override suspend fun getRemoteCategories(): Result<List<Category>> =
        withContext(Dispatchers.IO) {
            try {
                val response = service.getCategories()
                if (response.isSuccessful) {
                    return@withContext Result.Success(mapper.toCategoryList(response.body()!!))
                } else {
                    return@withContext Result.Error(Exception(response.message()))
                }
            } catch (e: Exception) {
                return@withContext Result.Error(e)
            }
        }
}