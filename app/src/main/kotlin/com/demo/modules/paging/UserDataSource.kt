package com.demo.modules.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.demo.modules.paging.mock.MockService
import com.demo.modules.paging.mock.User

class UserDataSource(private val service: MockService) : PagingSource<Int, User>() {
    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val nextId = params.key ?: 0
        val userList = service.loadNext(nextId, params.loadSize)
        return LoadResult.Page(userList, null, userList.last().id + 1)
    }
}