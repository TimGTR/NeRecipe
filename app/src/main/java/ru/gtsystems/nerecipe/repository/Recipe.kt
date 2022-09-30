package ru.gtsystems.nerecipe.repository

import kotlinx.serialization.Serializable

//@Serializable
data class Recipe(
    val id: Long,
    val title: String,
    val authorName: String,
    val categoryRecipe: String,
    val textRecipe: String,
    val isFavorite: Boolean = false,
)