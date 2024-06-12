package com.demo.modules.paging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.demo.modules.paging.mock.MockService

class UserViewModel : ViewModel() {

    val flow = Pager(PagingConfig(PAGE_COUNT)) {
        UserDataSource(MockService())
    }.flow.cachedIn(viewModelScope)

    companion object {
        const val PAGE_COUNT = 30
    }

}