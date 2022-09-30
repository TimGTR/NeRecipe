package ru.gtsystems.nerecipe.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.gtsystems.nerecipe.R
import ru.gtsystems.nerecipe.databinding.PostCardBinding
import ru.gtsystems.nerecipe.repository.Post
import ru.gtsystems.nerecipe.repository.Recipe

import java.math.RoundingMode
import java.text.DecimalFormat



class RecipesAdapter(
    private val interactionListener: RecipeListener
) : ListAdapter<Recipe, RecipesAdapter.ViewHolder>(DiffCallback) {


    private object DiffCallback : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Recipe, newItem:Recipe) =
            oldItem == newItem

    }

    class ViewHolder(
        private val binding: PostCardBinding,
        listener: RecipeListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var recipe: Recipe
        private val popupMenu by lazy {
            PopupMenu(itemView.context, binding.options).apply {
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

        init {
            binding.like.setOnClickListener {
                listener.onLikeClicked(recipe)
            }

            binding.text.setOnClickListener {
                listener.viewRecipeDetails(recipe)
            }
            binding.video.setOnClickListener {
                listener.onVideoC

            }



        }

        fun bind(recipe: Recipe) {
            this.recipe = recipe
            with(binding) {
                authorName.text = recipe.author
                name.text = recipe.name
                name.text = recipe.content


                options.setOnClickListener { popupMenu.show() }

            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("PostsAdapter", "onCreate:")
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostCardBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, interactionListener)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("PostsAdapter", "onBind: $position")
        val post = getItem(position)
        holder.bind(post)
    }


}