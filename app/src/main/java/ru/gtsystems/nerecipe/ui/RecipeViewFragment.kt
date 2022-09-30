package ru.gtsystems.nerecipe.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.gtsystems.nerecipe.R
import ru.gtsystems.nerecipe.databinding.FragmentViewRecipeBinding


class RecipeViewFragment : Fragment() {
    private val args by navArgs<FeedRecipeFragment>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentViewRecipeBinding.inflate(layoutInflater, container, false).also { binding ->
        incomingArg(binding)
        binding.bottomToolbar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.feed -> findNavController().popBackStack()
            }
            true
        }
    }.root

    private fun incomingArg (binding: FragmentViewRecipeBinding) {
        binding.title.text = args.viewRecipe?.title.toString()
        binding.authorName.text = args.viewRecipe?.authorName.toString()
        binding.categoryRecipe.text = args.viewRecipe?.categoryRecipe.toString()
        binding.textRecipe.text = args.viewRecipe?.textRecipe.toString()
    }
}