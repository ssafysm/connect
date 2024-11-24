package com.ssafy.smartstore_jetpack.presentation.views.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.smartstore_jetpack.domain.model.Status
import com.ssafy.smartstore_jetpack.domain.usecase.GetAppThemeUseCase
import com.ssafy.smartstore_jetpack.domain.usecase.GetCookieUseCase
import com.ssafy.smartstore_jetpack.domain.usecase.GetUserIdUseCase
import com.ssafy.smartstore_jetpack.domain.usecase.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val getUserIdUseCase: GetUserIdUseCase,
    private val getCookieUseCase: GetCookieUseCase,
    private val getAppThemeUseCase: GetAppThemeUseCase
) : ViewModel() {

    private val _appThemeName = MutableStateFlow<String>("기본")
    val appThemeName = _appThemeName.asStateFlow()

    private val _splashUiEvent = MutableSharedFlow<SplashUiEvent>()
    val splashUiEvent = _splashUiEvent.asSharedFlow()

    init {
        viewModelScope.launch {
            _appThemeName.value = getAppTheme().first()
            tryLogin()
        }
    }

    private suspend fun getUserId(): Flow<String> = flow {
        val userId = getUserIdUseCase.getUserId().firstOrNull() ?: ""
        emit(userId)
    }

    private suspend fun getCookies(): Flow<Set<String>> = flow {
        val cookies = getCookieUseCase.getLoginCookie().firstOrNull() ?: emptySet()
        emit(cookies)
    }

    private suspend fun getAppTheme(): Flow<String> = flow {
        val appTheme = getAppThemeUseCase.getAppTheme().first()
        emit(appTheme)
    }

    private fun tryLogin() {
        viewModelScope.launch {
            val cookies = getCookies().first()
            val userId = getUserId().first()
            when (cookies.isNotEmpty() && userId.isNotBlank()) {
                true -> {
                    val response = getUserUseCase.getIsUsedId(userId)

                    when (response.status) {
                        Status.SUCCESS -> {
                            when (response.data) {
                                true -> {
                                    _splashUiEvent.emit(SplashUiEvent.LoginSuccess)
                                }

                                else -> {
                                    _splashUiEvent.emit(SplashUiEvent.LoginFailed)
                                }
                            }
                        }

                        else -> {
                            _splashUiEvent.emit(SplashUiEvent.LoginFailed)
                        }
                    }
                }

                else -> {
                    _splashUiEvent.emit(SplashUiEvent.LoginFailed)
                }
            }
        }
    }
}