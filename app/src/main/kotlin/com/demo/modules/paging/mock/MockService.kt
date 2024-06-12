package com.demo.modules.paging.mock

import androidx.recyclerview.widget.DiffUtil
import kotlinx.coroutines.delay
import kotlin.math.min

/**
 * 模拟分页数据服务
 */
class MockService {
    /**
     * 初始化1000个用户数据
     */
    private val userList = List(1000) { User(it, "用户: $it") }

    suspend fun loadNext(nextId: Int, count: Int): List<User> {
        delay(500)
        return userList.subList(nextId, nextId + count)
    }

    suspend fun loadPrev(prevId: Int, count: Int): List<User> {
        delay(500)
        val startIndex = min(prevId - count, 0)
        return userList.subList(startIndex, startIndex + count)
    }
}

/**
 * 用户信息
 */
data class User(val id: Int, val name: String) {
    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }
    }
}