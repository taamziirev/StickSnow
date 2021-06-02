package com.project.data.mappers.category

import com.project.data.api.category.CategoryApiResponse
import com.project.data.entities.Category


class CategoryApiResponseMapper {
    fun toCategoryList(response: List<CategoryApiResponse>): List<Category> {
        return response.map {
            Category(
                it.id,it.slug,it.name
            )
        }
    }
}