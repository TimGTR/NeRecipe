package ru.gtsystems.nerecipe.repository

import kotlinx.serialization.Serializable
import java.net.URI

@Serializable
data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likedByMe: Boolean = false,
    val likesCount: Int = 999,
    val shareCount: Int = 995,
    val visibleCount: Int = 1000,
    val videoUrl: String? = null
)