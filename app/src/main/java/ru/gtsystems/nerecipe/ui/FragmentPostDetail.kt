package ru.gtsystems.nerecipe.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.gtsystems.nerecipe.R
import ru.gtsystems.nerecipe.adapter.PostInteractionListener
import ru.gtsystems.nerecipe.adapter.RecipesAdapter
import ru.gtsystems.nerecipe.databinding.FragmentPostBinding
import ru.gtsystems.nerecipe.repository.Post
import ru.gtsystems.nerecipe.ui.NewPostFragment.Companion.textArg
import ru.gtsystems.nerecipe.util.IntArg
import ru.gtsystems.nerecipe.viewmodel.PostViewModel


class FragmentPostDetail : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        val binding = FragmentPostBinding.inflate (inflater, container, false)
        val viewModel: PostViewModel by viewModels(ownerProducer = :: requireParentFragment)


        val postId = arguments?.IntArg ?: -1
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            val post = posts.find { it.id.toInt() == postId } ?: return@observe
            val viewHolder = RecipesAdapter.ViewHolder (binding.postLayout, object :
                PostInteractionListener {
                override fun onLikeClicked(post: Post) {
                    viewModel.onLikeClicked(post)
                }

                override fun onShareClicked(post: Post) {
                    viewModel.onShareClicked(post)
                    val intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, post.content)
                        type = "text/plain"
                    }
                    val shareIntent =
                        Intent.createChooser(intent, getString(R.string.chooser_share_post))
                    startActivity(shareIntent)
                }

                override fun onRemoveClicked(post: Post) {
                    viewModel.onRemoveClicked(post)
                }

                override fun onEditClicked(post: Post) {
                    viewModel.onEditClicked(post)
                    findNavController().navigate(
                        R.id.action_fragmentPostDetail_to_newPostFragment,
                        Bundle().apply {textArg = post.content}
                    )
                }

                override fun onSaveClicked(content: String, url: String?) {
                    viewModel.onSaveClicked(content, url)
                    findNavController().navigateUp()
                }

                override fun onCancelClicked() {
                    viewModel.onCancelClicked()
                }

                override fun onVideoClicked(post: Post) {
                    viewModel.onVideoClicked(post)
                    val intent = Intent().apply {
                        action = Intent.ACTION_VIEW
                        data = Uri.parse(post.videoUrl)

                    }
                    if (post.videoUrl == null) return
                    else startActivity(intent)
                }

                override fun viewPostDetails(post: Post) {

                }

            })

            viewHolder.bind(post)
        }




        return binding.root
    }
    companion object {
        var Bundle.IntArg: Int by IntArg
    }
}
