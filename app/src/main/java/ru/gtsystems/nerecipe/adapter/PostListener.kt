package ru.gtsystems.nerecipe.adapter

import ru.gtsystems.nerecipe.repository.Post

interface PostInteractionListener {
    fun onLikeClicked(post: Post)
    fun onShareClicked(post: Post)
    fun onRemoveClicked(post: Post)
    fun onEditClicked(post : Post)
    fun onSaveClicked(content: String, url: String?)
    fun onCancelClicked()
    fun onVideoClicked(post: Post)
    fun viewPostDetails(post : Post)

}