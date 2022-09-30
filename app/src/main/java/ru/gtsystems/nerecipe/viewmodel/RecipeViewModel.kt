package ru.netology.nerecipe.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.gtsystems.nerecipe.adapter.RecipeInteractionListener
import ru.gtsystems.nerecipe.db.AppDb
import ru.gtsystems.nerecipe.repository.Recipe
import ru.gtsystems.nerecipe.repository.RecipeRepository
import ru.gtsystems.nerecipe.repository.RecipeRepositoryImpl
import ru.gtsystems.nerecipe.util.Event

class RecipeViewModel(application: Application) : AndroidViewModel(application),
    RecipeInteractionListener {

    private val repository: RecipeRepository =
        RecipeRepositoryImpl(dao = AppDb.getInstance(context = application).recipeDao)

    val data by repository::data
    var filterIsActive = false
    val favoriteFragment = Event<String>()
    val createFragment = Event<Unit>()
    val updateRecipeFragment = Event<String?>()
    val singleFragment = Event<Unit>()
    val filterFragment = Event<Unit>()
    val updateRecipe = MutableLiveData<Recipe>(null)
    val singleRecipe = MutableLiveData<Recipe?>(null)
    val feedFragment = data
    private val currentRecipe = MutableLiveData<Recipe?>(null)



    fun clearFilter() {
        repository.getData()
    }

    override fun updateContent(
        id: Long,
        title: String,
        authorNam: String,
        categoryRecipe: String,
        textRecipe: String
    ) {
        val recipe = Recipe(
            id = id,
            title = title,
            authorName = authorNam,
            categoryRecipe = categoryRecipe,
            textRecipe = textRecipe
        )
        repository.save(recipe)
    }

    override fun onRemoveClicked(recipe: Recipe) {
        repository.delete(recipe)
    }

    override fun onEditClicked(recipe: Recipe) {
        updateRecipe.value = recipe
        updateRecipeFragment.call()
    }

    override fun onFavoriteClicked(recipeId: Long) {
        repository.favorite(recipeId)
    }

    override fun onSearchClicked(text: String) {
        repository.searchText(text)
    }

    override fun onCreateClicked() {
        createFragment.call()
    }

    override fun onSaveClicked(title: String, authorNam: String, categoryRecipe: String, textRecipe: String) {

        val recipe = Recipe(
            id = RecipeRepository.NEW_ID,
            title = title,
            authorName = authorNam,
            categoryRecipe = categoryRecipe,
            textRecipe = textRecipe
        )
        repository.save(recipe)
        currentRecipe.value = null
    }

    override fun onSingleRecipeClicked(recipe: Recipe) {
        singleRecipe.value = recipe
        singleFragment.call()
    }

    fun showEuropean(categoryRecipe: String) {
        repository.showEuropean(categoryRecipe)
        filterIsActive = true
    }

    fun showAsian(categoryRecipe: String) {
        repository.showAsian(categoryRecipe)
        filterIsActive = true
    }

    fun showPanasian(categoryRecipe: String) {
        repository.showPanasian(categoryRecipe)
        filterIsActive = true
    }

    fun showEastern(categoryRecipe: String) {
        repository.showEastern(categoryRecipe)
        filterIsActive = true
    }

    fun showAmerican(categoryRecipe: String) {
        repository.showAmerican(categoryRecipe)
        filterIsActive = true
    }

    fun showRussian(categoryRecipe: String) {
        repository.showRussian(categoryRecipe)
        filterIsActive = true
    }

    fun showMediterranean(categoryRecipe: String) {
        repository.showMediterranean(categoryRecipe)
        filterIsActive = true
    }
}