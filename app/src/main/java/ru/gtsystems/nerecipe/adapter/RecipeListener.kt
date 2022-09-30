package ru.gtsystems.nerecipe.adapter

import ru.gtsystems.nerecipe.repository.Recipe

interface RecipeListener {
    fun onLikeClicked(recipe: Recipe)
    fun onRemoveClicked(recipe: Recipe)
    fun onEditClicked(recipe: Recipe)
    fun viewRecipeDetails(recipe: Recipe)

}