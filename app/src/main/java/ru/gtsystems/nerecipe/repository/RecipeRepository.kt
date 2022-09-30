package ru.gtsystems.nerecipe.repository

interface RecipeRepository {

    fun addToFavourites(recipe: Recipe)
    fun removeById(recipe: Recipe)
    fun save(recipe: Recipe)



}