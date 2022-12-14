package ru.gtsystems.nerecipe.repository

import androidx.lifecycle.map
import ru.gtsystems.nerecipe.db.RecipeDao

class RecipeRepositoryImpl(
    private val dao: RecipeDao
) : RecipeRepository {

    override var data = dao.getAll().map { entities ->
        entities.map { it.toModel() }
    }

    override fun getData() {
        data = dao.getAll().map { entities ->
            entities.map { it.toModel() }
        }
    }

    override fun getCategory(id: Long) =
        dao.searchCategory(id)


    override fun save(recipe: Recipe) {
        if (recipe.id == RecipeRepository.NEW_ID) dao.save(recipe = recipe.toEntity())
        else dao.updateContentById(
            recipe.id, recipe.title, recipe.authorName,
            recipe.categoryRecipe, recipe.textRecipe
        )
    }

    override fun update(recipe: Recipe) {
        save(recipe)
    }

    override fun delete(recipe: Recipe) {
        dao.removeById(recipe.id)
    }

    override fun favorite(long: Long) {
        dao.favById(long)
    }

    override fun searchText(Text: String) {
        data = dao.searchByText(Text).map { entities ->
            entities.map { it.toModel() }
        }
    }

    override fun showEuropean(type: String) {
        data = data.map {
            it.filter { it.categoryRecipe != type }
        }
    }

    override fun showAsian(type: String) {
        data = data.map {
            it.filter { it.categoryRecipe != type }
        }
    }

    override fun showPanasian(type: String) {
        data = data.map {
            it.filter { it.categoryRecipe != type }
        }
    }

    override fun showEastern(type: String) {
        data = data.map {
            it.filter { it.categoryRecipe != type }
        }
    }

    override fun showAmerican(type: String) {
        data = data.map {
            it.filter { it.categoryRecipe != type }
        }
    }


    override fun showRussian(type: String) {
        data = data.map {
            it.filter { it.categoryRecipe != type }
        }
    }


    override fun showMediterranean(type: String) {
        data = data.map {
            it.filter { it.categoryRecipe != type}
        }
    }
}