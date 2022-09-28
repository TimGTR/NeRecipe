package ru.gtsystems.nerecipe.db

import ru.gtsystems.nerecipe.repository.Post


internal fun PostEntity.toModel() = Post(
    id = id,
    author = author,
    content = content,
    published = published,
    likesCount = likes,
    likedByMe = likedByMe,
    shareCount = shareCount

)

internal fun Post.toEntity() = PostEntity(
    id = id,
    author = author,
    content = content,
    published = published,
    likedByMe = likedByMe,
    likes = likesCount,
    shareCount = shareCount
)