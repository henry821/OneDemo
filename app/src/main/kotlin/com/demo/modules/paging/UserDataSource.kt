package com.demo.modules.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.demo.DatabaseHolder

const val PAGE_COUNT = 30

class UserDataSource : PagingSource<Int, User>() {

    private val userDao = DatabaseHolder.db.userDao()

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val nextId = params.key ?: 0
        val userList = userDao.loadNextPageUsers(nextId, PAGE_COUNT)
        return LoadResult.Page(userList, null, userList.last().id)
    }
}