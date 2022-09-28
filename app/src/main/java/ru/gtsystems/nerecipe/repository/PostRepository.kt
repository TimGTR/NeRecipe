package ru.gtsystems.nerecipe.repository

import androidx.lifecycle.LiveData

interface PostRepository {

    fun likeById(id: Long)
    fun getShareCountById(id: Long)
    fun removeById(id: Long)
    fun save(post: Post)


    companion object {
        const val NEW_POST_ID = 0L
    }


    val data: LiveData<List<Post>>
}