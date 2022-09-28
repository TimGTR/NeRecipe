package ru.gtsystems.nerecipe.repository

import androidx.lifecycle.map
import ru.gtsystems.nerecipe.db.PostDao
import ru.gtsystems.nerecipe.db.toEntity
import ru.gtsystems.nerecipe.db.toModel


class PostRepositoryImpl(
    private val dao: PostDao
) : PostRepository {

    override val data = dao.getAll().map { entities ->
        entities.map {
            it.toModel()
        }
    }


    override fun likeById(id: Long) {
        dao.likeById(id)
    }

    override fun getShareCountById(id: Long) {
        dao.share(id)
    }

    override fun removeById(id: Long) {
        dao.removeById(id)
    }

    override fun save(post: Post) =
        if (post.id == PostRepository.NEW_POST_ID) dao.insert(post.toEntity()) else dao.updateContentById(post.id, post.content)

}