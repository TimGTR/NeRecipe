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
import ru.gtsystems.nerecipe.databinding.FragmentFeedBinding
import ru.gtsystems.nerecipe.repository.Post
import ru.gtsystems.nerecipe.ui.FragmentPostDetail.Companion.IntArg
import ru.gtsystems.nerecipe.ui.NewPostFragment.Companion.textArg
import ru.gtsystems.nerecipe.viewmodel.PostViewModel


class FeedFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFeedBinding.inflate(
            inflater, container, false
        )
        val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)


        val adapter = RecipesAdapter(object : PostInteractionListener {
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
                    R.id.action_feedFragment_to_newPostFragment,
                    Bundle().apply { textArg = post.content }
                )
            }

            override fun onSaveClicked(content: String, url: String?) {
                viewModel.onSaveClicked(content, url)
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
                viewModel.viewPostDetails(post)
                findNavController().navigate(
                    R.id.action_feedFragment_to_fragmentPostDetail,
                    Bundle().apply { IntArg = post.id.toInt() }
                )

            }

        })
        binding.postsRecyclerView.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)

        }


        binding.fab.setOnClickListener() {
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
        }

        return binding.root
    }


}