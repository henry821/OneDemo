package com.demo.modules.recyclerview

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * [RecyclerViewFragment]使用的ViewModel
 */
class RecyclerViewViewModel : ViewModel() {

    private val _userList = MutableStateFlow(listOf<User>())
    val userList = _userList.asStateFlow()

    init {
        _userList.value = List(10) { User("", "用户名：$it") }
    }

    fun remove(position: Int) {
        _userList.update { it.filterIndexed { index, _ -> index != position } }
    }

}

data class User(val avatar: String, val name: String)