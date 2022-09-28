package ru.gtsystems.nerecipe.repository

data class Recipe(
    val id: Long,
    val name: String,
    val author: String,
    val category: Category
)