package ru.gtsystems.nerecipe.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.gtsystems.nerecipe.adapter.PostInteractionListener
import ru.gtsystems.nerecipe.db.AppDb
import ru.gtsystems.nerecipe.repository.Post
import ru.gtsystems.nerecipe.repository.PostRepository
import ru.gtsystems.nerecipe.repository.PostRepositoryImpl
import ru.gtsystems.nerecipe.util.SingleLiveEvent


class PostViewModel(application: Application) :AndroidViewModel(application),
    PostInteractionListener {
    private val repository: PostRepository = PostRepositoryImpl(
        dao = AppDb.getInstance(
            context = application
        ).postDao
    )
    val data by repository::data
    val sharePostContent = SingleLiveEvent<String>()
    val editPostContent = SingleLiveEvent<String>()
    val playVideoContent = SingleLiveEvent<String?>()
    val navigateToPostDetails = SingleLiveEvent<Long>()

    val currentPost = MutableLiveData<Post?>(null)
    override fun onLikeClicked(post: Post) = repository.likeById(post.id)
    override fun onShareClicked(post: Post) {
        sharePostContent.value = post.content
        repository.getShareCountById(post.id)

    }
    override fun onRemoveClicked(post: Post) = repository.removeById(post.id)


    override fun onEditClicked(post: Post) {
        currentPost.value = post
        editPostContent.value = post.content

    }

    override fun onVideoClicked(post: Post) {
        playVideoContent.value = post.videoUrl

    }

    override fun onSaveClicked(content: String, url: String?) {
        if (content.isBlank() && url == null) return
        val post = currentPost.value?.copy(
            content = content,
            videoUrl = url


        ) ?: Post(
            id = PostRepository.NEW_POST_ID,
            author = "Me",
            content = content,
            published = "Today",
            likesCount = 0,
            shareCount = 0,
        )
        repository.save(post)
        currentPost.value = null
    }
    override fun viewPostDetails(post: Post) {
        currentPost.value = post
        navigateToPostDetails.value = post.id
    }

    override fun onCancelClicked() {
        currentPost.value = null
    }




}

