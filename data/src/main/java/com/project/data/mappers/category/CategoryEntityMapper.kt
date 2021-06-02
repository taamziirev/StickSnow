package com.project.data.mappers.category

import com.project.data.entities.Category
import com.project.data.entities.category.CategoryEntity

class CategoryEntityMapper {
    fun toCategoryEntity(category: Category): CategoryEntity {
        return CategoryEntity(
            id = category.id,
            slug = category.slug,
            name = category.name
        )
    }

    fun toCategory(categoryEntity: CategoryEntity): Category {
        return Category(
            categoryEntity.id,
            categoryEntity.slug,
            categoryEntity.name
        )
    }
}