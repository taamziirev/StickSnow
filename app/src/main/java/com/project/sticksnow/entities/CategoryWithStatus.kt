package com.project.sticksnow.entities

data class CategoryWithStatus(
    val id: Int,
    val slug: String,
    val name: String,
    val checked: Boolean
)