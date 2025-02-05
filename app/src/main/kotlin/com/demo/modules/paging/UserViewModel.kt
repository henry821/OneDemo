package com.demo.modules.paging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn

class UserViewModel : ViewModel() {

    val flow = Pager(PagingConfig(PAGE_COUNT)) {
        UserDataSource()
    }.flow.cachedIn(viewModelScope)

}