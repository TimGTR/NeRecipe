package ru.gtsystems.nerecipe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.gtsystems.nerecipe.R
import ru.gtsystems.nerecipe.databinding.RecipeCardBinding
import ru.gtsystems.nerecipe.repository.Recipe


class RecipesAdapter(
    private val interactionListener: RecipeInteractionListener

) : ListAdapter<Recipe, RecipesAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecipeCardBinding.inflate(inflater, parent, false)

        return ViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: RecipeCardBinding,
        private val listener: RecipeInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var recipe: Recipe

        private val popupMenu by lazy {
            PopupMenu(itemView.context, binding.menuOptions).apply {
                inflate(R.menu.options_post)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.remove -> {
                            listener.onRemoveClicked(recipe)
                            true
                        }
                        R.id.edit -> {
                            listener.onEditClicked(recipe)
                            true
                        }
                        else -> false
                    }
                }
            }
        }

        fun bind(recipe: Recipe) {
            this.recipe = recipe
            with(binding) {
                title.text = recipe.title
                authorName.text = recipe.authorName
                categoryRecipe.text = recipe.categoryRecipe
                textRecipe.text = recipe.textRecipe
                buttonFavorite.setImageResource(getFavoriteIconResId(recipe.isFavorite))
                buttonFavorite.setOnClickListener {
                    listener.onFavoriteClicked(recipe.id)
                }
                title.setOnClickListener {
                    listener.onSingleRecipeClicked(recipe)
                }
                textRecipe.setOnClickListener {
                    listener.onSingleRecipeClicked(recipe)
                }
                authorName.setOnClickListener {
                    listener.onSingleRecipeClicked(recipe)
                }
                categoryRecipe.setOnClickListener {
                    listener.onSingleRecipeClicked(recipe)
                }
                menuOptions.setOnClickListener {
                    popupMenu.show()
                }
            }
        }

        @DrawableRes
        private fun getFavoriteIconResId(liked: Boolean) =
            if (liked) R.drawable.icon_favourites else R.drawable.icon_not_favourite
    }
}

private object DiffCallback : DiffUtil.ItemCallback<Recipe>() {

    override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean =
        oldItem == newItem
}