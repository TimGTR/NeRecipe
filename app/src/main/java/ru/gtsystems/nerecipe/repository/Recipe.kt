package ru.gtsystems.nerecipe.repository

import kotlinx.serialization.Serializable


@Serializable
data class Recipe(
    val id: Long,
    val name: String,
    val content: String,
    val author: String,
    val category: Category
)