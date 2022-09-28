package ru.gtsystems.nerecipe.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.gtsystems.nerecipe.databinding.FragmentNewPostBinding
import ru.gtsystems.nerecipe.util.StringArg
import ru.gtsystems.nerecipe.viewmodel.PostViewModel

class NewPostFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewPostBinding.inflate(inflater, container, false)
        arguments?.textArg?.let(binding.edit::setText)
        val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)
        binding.edit.requestFocus()
        binding.ok.setOnClickListener() {


            if (!binding.edit.text.isNullOrBlank()) {
                val content = binding.edit.text.toString()
                val url = binding.editURL.text.toString()
                viewModel.onSaveClicked(content, url)

            }
            findNavController().navigateUp()


        }



        return binding.root
    }
    companion object {
        var Bundle.textArg : String? by StringArg
    }

}
