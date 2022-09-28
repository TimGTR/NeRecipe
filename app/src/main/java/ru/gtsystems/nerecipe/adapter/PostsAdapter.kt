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

import java.math.RoundingMode
import java.text.DecimalFormat



class PostsAdapter(
    private val interactionListener: PostInteractionListener
) : ListAdapter<Post, PostsAdapter.ViewHolder>(DiffCallback) {


    private object DiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Post, newItem: Post) =
            oldItem == newItem

    }

    class ViewHolder(
        private val binding: PostCardBinding,
        listener: PostInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var post: Post
        private val popupMenu by lazy {
            PopupMenu(itemView.context, binding.options).apply {
                inflate(R.menu.options_post)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.remove -> {
                            listener.onRemoveClicked(post)
                            true
                        }
                        R.id.edit -> {
                            listener.onEditClicked(post)
                            true
                        }
                        else -> false
                    }

                }
            }
        }

        init {
            binding.like.setOnClickListener {
                listener.onLikeClicked(post)
            }
            binding.share.setOnClickListener {
                listener.onShareClicked(post)
            }
            binding.text.setOnClickListener {
                listener.viewPostDetails(post)
            }
            binding.video.setOnClickListener {
                listener.onVideoClicked(post)
            }



        }

        fun bind(post: Post) {
            this.post = post
            with(binding) {
                authorName.text = post.author
                date.text = post.published
                text.text = post.content
                like.text = remakeCount(post.likesCount)
                share.text = remakeCount(post.shareCount)
                visibleCount.text = remakeCount(post.visibleCount)
                like.isChecked = post.likedByMe
                url.text = post.videoUrl
                options.setOnClickListener { popupMenu.show() }

            }
        }

        private fun remakeCount(count: Int) =
            if (count < 1000) {
                count.toString()
            } else if (count < 1_000_000) {
                val df = DecimalFormat("#.#")
                df.roundingMode = RoundingMode.CEILING
                "${df.format((count / 1000.0))}K"
            } else {
                val df = DecimalFormat("#.#")
                df.roundingMode = RoundingMode.CEILING
                "${df.format((count / 1000000.0))}M"
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