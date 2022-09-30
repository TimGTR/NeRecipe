package ru.gtsystems.nerecipe.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.gtsystems.nerecipe.R
import ru.gtsystems.nerecipe.adapter.RecipesAdapter
import ru.gtsystems.nerecipe.databinding.EmptyFieldBinding
import ru.netology.nerecipe.ui.RecipeViewModel


class FeedRecipeFragment : Fragment() {

    private val viewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.favoriteFragment.observe(this) {
            //val direction = FeedRecipeFragmentDirections.favoriteFragment()
            findNavController().navigate(R.id.favoriteFragment)
        }

        viewModel.updateRecipeFragment.observe(this) {
            val updatedRecipe = viewModel.updateRecipe.value
            //val directions = FeedRecipeFragmentDirections.updateRecipeFragment(updatedRecipe)
            findNavController().navigate(R.id.updateRecipeFragment)
        }

        viewModel.createFragment.observe(this) {
            //val directions = FeedRecipeFragmentDirections.recipeCreateFragment()
            findNavController().navigate(R.id.recipeCreateFragment)
        }

        viewModel.singleFragment.observe(this) {
            val viewRecipe = viewModel.singleRecipe.value
            //val directions = FeedRecipeFragmentDirections.recipeViewFragment(viewRecipe)
            findNavController().navigate(R.id.recipeViewFragment)
        }

        viewModel.filterFragment.observe(this) {
            //val directions = FeedRecipeFragmentDirections.recipeFilterFragment()
            findNavController().navigate(R.id.recipeFilterFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = EmptyFieldBinding.inflate(layoutInflater, container, false).also { binding ->

        val adapter = RecipesAdapter(viewModel)
        binding.listRecipe.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner) { recipes ->
            adapter.submitList(recipes)
        }

        if (viewModel.filterIsActive) {

            binding.buttonClearFilter.isVisible = viewModel.filterIsActive
            binding.buttonClearFilter.setOnClickListener {
                viewModel.clearFilter()
                viewModel.filterIsActive = false
                binding.buttonClearFilter.visibility = View.GONE
                viewModel.data.observe(viewLifecycleOwner) { recipe ->
                    adapter.submitList(recipe)
                }
            }
        } else {
            binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {

                    if (newText.isNotBlank()) {
                        viewModel.onSearchClicked(newText)
                        viewModel.data.observe(viewLifecycleOwner) { recipe ->
                            adapter.submitList(recipe)
                        }
                    }
                    if (TextUtils.isEmpty(newText)){
                        viewModel.clearFilter()
                        viewModel.data.observe(viewLifecycleOwner) { recipe ->
                            adapter.submitList(recipe)
                        }
                    }
                    return false
                }
            })
        }

        binding.bottomToolbar.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.favorites -> {
                    viewModel.favoriteFragment.call()
                    true
                }
                R.id.filter -> {
                    viewModel.filterFragment.call()
                    true
                }
                else -> false
            }
        }

        binding.buttonAdd.setOnClickListener {
            viewModel.onCreateClicked()
        }

    }.root
}