package ru.gtsystems.nerecipe.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
class PostEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    @ColumnInfo(name = "liked_by_me")
    val likedByMe: Boolean,
    val likes: Int = 0,
    val shareCount: Int = 0

)