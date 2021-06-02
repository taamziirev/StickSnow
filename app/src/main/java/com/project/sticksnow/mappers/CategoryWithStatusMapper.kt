package com.project.sticksnow.mappers

import com.project.data.entities.Category
import com.project.sticksnow.entities.CategoryWithStatus

class CategoryWithStatusMapper {
    fun fromCategoryToCategoryWithStatus(
        categories: List<Category>,
        checked: List<Category>
    ): List<CategoryWithStatus> {
        val commonElements = categories.filter { it.id in checked.map { check -> check.id } }
        val categoriesWithStatus = arrayListOf<CategoryWithStatus>()
        for (category in categories) {
            if (category in commonElements) {
                categoriesWithStatus.add(
                    CategoryWithStatus(
                        category.id,
                        category.slug,
                        category.name,
                        false
                    )
                )
            } else {
                categoriesWithStatus.add(
                    CategoryWithStatus(
                        category.id,
                        category.slug,
                        category.name,
                        false
                    )
                )
            }
        }
        return categoriesWithStatus.sortedBy { it.id }
    }

    fun fromCategoryWithStatusToCategory(categoryWithStatus: CategoryWithStatus): Category {
        return Category(
            categoryWithStatus.id,
            categoryWithStatus.slug,
            categoryWithStatus.name
        )
    }
}