package com.demo.modules.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.demo.base.sharedpreference.getAppSharedPreference
import com.demo.base.sharedpreference.putAppSharedPreferenceValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Description 登录模块
 * Author henry
 * Date   2023/3/1
 */
class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val _loginState = MutableStateFlow(false)
    val loginState: StateFlow<Boolean> get() = _loginState

    init {
        queryLoginState()
    }

    private fun queryLoginState() {
        viewModelScope.launch {
            _loginState.value = withContext(Dispatchers.IO) {
                getAppSharedPreference(getApplication()).getBoolean(LOGIN_KEY, false)
            }
        }
    }

    fun login() {
        viewModelScope.launch {
            _loginState.value = withContext(Dispatchers.IO) {
                putAppSharedPreferenceValue(getApplication(), LOGIN_KEY, true)
                true
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            _loginState.value = withContext(Dispatchers.IO) {
                putAppSharedPreferenceValue(getApplication(), LOGIN_KEY, false)
                false
            }
        }
    }

    companion object {
        private const val LOGIN_KEY = "login_state"
    }

}