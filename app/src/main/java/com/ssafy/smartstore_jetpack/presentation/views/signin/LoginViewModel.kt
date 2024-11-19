package com.ssafy.smartstore_jetpack.presentation.views.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.smartstore_jetpack.domain.model.Status
import com.ssafy.smartstore_jetpack.domain.model.User
import com.ssafy.smartstore_jetpack.domain.usecase.GetCookieUseCase
import com.ssafy.smartstore_jetpack.domain.usecase.GetUserUseCase
import com.ssafy.smartstore_jetpack.domain.usecase.SetCookieUseCase
import com.ssafy.smartstore_jetpack.domain.usecase.SetUserIdUseCase
import com.ssafy.smartstore_jetpack.presentation.views.signin.join.DuplicateState
import com.ssafy.smartstore_jetpack.presentation.views.signin.join.InputValidState
import com.ssafy.smartstore_jetpack.presentation.views.signin.join.JoinClickListener
import com.ssafy.smartstore_jetpack.presentation.views.signin.join.JoinUiEvent
import com.ssafy.smartstore_jetpack.presentation.views.signin.join.JoinUiState
import com.ssafy.smartstore_jetpack.presentation.views.signin.login.LoginClickListener
import com.ssafy.smartstore_jetpack.presentation.views.signin.login.LoginUiEvent
import com.ssafy.smartstore_jetpack.presentation.views.signin.login.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val setUserIdUseCase: SetUserIdUseCase,
    private val getCookieUseCase: GetCookieUseCase,
    private val setCookieUseCase: SetCookieUseCase
) : ViewModel(), LoginClickListener, JoinClickListener {

    /****** Data ******/
    private val _userId = MutableStateFlow<String>("")
    val userId = _userId

    private val _userPass = MutableStateFlow<String>("")
    val userPass = _userPass

    private val _joinId = MutableStateFlow<String>("")
    val joinId = _joinId

    private val _joinPass = MutableStateFlow<String>("")
    val joinPass = _joinPass

    private val _joinName = MutableStateFlow<String>("")
    val joinName = _joinName

    /****** Ui State ******/
    private val _loginUiState = MutableStateFlow<LoginUiState>(LoginUiState())
    val loginUiState = _loginUiState.asStateFlow()

    private val _joinUiState = MutableStateFlow<JoinUiState>(JoinUiState())
    val joinUiState = _joinUiState.asStateFlow()

    /****** Ui Event ******/
    private val _loginUiEvent = MutableSharedFlow<LoginUiEvent>()
    val loginUiEvent = _loginUiEvent.asSharedFlow()

    private val _joinUiEvent = MutableSharedFlow<JoinUiEvent>()
    val joinUiEvent = _joinUiEvent.asSharedFlow()

    override fun onClickLogin() {
        viewModelScope.launch {
            val response = getUserUseCase.postUserForLogin(
                User(
                    id = _userId.value,
                    name = "",
                    pass = _userPass.value,
                    stamps = "0",
                    stampList = emptyList()
                )
            )

            when (response.status) {
                Status.SUCCESS -> {
                    val cookies = getCookies().first()
                    cookies.let {
                        if (it.isNotEmpty()) {
                            Timber.d("Cookie: $cookies")
                            setUserIdUseCase.setUserId(_userId.value)
                            setCookieUseCase.setLoginCookie(it.toHashSet())
                            _userId.value = ""
                            _userPass.value = ""
                            _loginUiState.update { uiState ->
                                uiState.copy(
                                    userIdValidState = InputValidState.NONE,
                                    userPassValidState = InputValidState.NONE
                                )
                            }
                            _loginUiEvent.emit(LoginUiEvent.GoToLogin)
                            _joinUiEvent.emit(JoinUiEvent.GoToLogin)
                        }
                    }
                }

                else -> {
                    _loginUiEvent.emit(LoginUiEvent.LoginFail)
                }
            }
        }
    }

    override fun onClickJoin() {
        viewModelScope.launch {
            _loginUiEvent.emit(LoginUiEvent.GoToJoin)
        }
    }

    override fun onClickCheckId() {
        viewModelScope.launch {
            when (_joinId.value.isBlank()) {
                true -> {
                    _joinUiState.update { it.copy(joinIdDuplicateState = DuplicateState.DUPLICATE) }
                    _joinUiEvent.emit(JoinUiEvent.CheckId(true))
                }

                else -> {
                    val response = getUserUseCase.getIsUsedId(_joinId.value)
                    when (response.status) {
                        Status.SUCCESS -> {
                            when (response.data) {
                                false -> {
                                    _joinUiState.update { it.copy(joinIdDuplicateState = DuplicateState.NONE) }
                                    _joinUiEvent.emit(JoinUiEvent.CheckId(response.data))
                                }

                                else -> {
                                    _joinUiState.update { it.copy(joinIdDuplicateState = DuplicateState.DUPLICATE) }
                                    _joinUiEvent.emit(JoinUiEvent.CheckId(true))
                                }
                            }
                        }

                        else -> {
                            _joinUiState.update { it.copy(joinIdDuplicateState = DuplicateState.DUPLICATE) }
                            _joinUiEvent.emit(JoinUiEvent.CheckId(true))
                        }
                    }
                }
            }
        }
    }

    override fun onClickGoToJoin() {
        viewModelScope.launch {
            val response = getUserUseCase.postUser(
                User(
                    id = _joinId.value,
                    name = _joinName.value,
                    pass = _joinPass.value,
                    stamps = "0",
                    stampList = emptyList()
                )
            )

            when (response.status) {
                Status.SUCCESS -> {
                    when (response.data) {
                        true -> {
                            _userId.value = _joinId.value
                            _joinId.value = ""
                            _joinPass.value = ""
                            _joinName.value = ""
                            _joinUiState.update {
                                it.copy(
                                    joinIdValidState = InputValidState.NONE,
                                    joinPassValidState = InputValidState.NONE,
                                    joinNameValidState = InputValidState.NONE,
                                    joinIdDuplicateState = DuplicateState.DUPLICATE
                                )
                            }
                            _joinUiEvent.emit(JoinUiEvent.GoToLogin)
                        }

                        else -> {
                            _joinUiEvent.emit(JoinUiEvent.JoinFail)
                        }
                    }
                }

                else -> {
                    _joinUiEvent.emit(JoinUiEvent.JoinFail)
                }
            }
        }
    }

    private suspend fun getCookies(): Flow<Set<String>> = flow {
        val cookies = getCookieUseCase.getLoginCookie().firstOrNull() ?: emptySet()
        emit(cookies)
    }

    fun validateUserId(id: CharSequence) {
        when (id.isNotBlank()) {
            true -> _loginUiState.update { it.copy(userIdValidState = InputValidState.VALID) }

            else -> _loginUiState.update { it.copy(userIdValidState = InputValidState.NONE) }
        }
    }

    fun validateUserPass(pass: CharSequence) {
        when (pass.isNotBlank()) {
            true -> _loginUiState.update { it.copy(userPassValidState = InputValidState.VALID) }

            else -> _loginUiState.update { it.copy(userPassValidState = InputValidState.NONE) }
        }
    }

    fun validateJoinId(id: CharSequence) {
        when (id.isNotBlank()) {
            true -> _joinUiState.update { it.copy(joinIdValidState = InputValidState.VALID) }

            else -> _joinUiState.update { it.copy(joinIdValidState = InputValidState.NONE) }
        }
    }

    fun validateJoinPass(pass: CharSequence) {
        when (pass.isNotBlank()) {
            true -> _joinUiState.update { it.copy(joinPassValidState = InputValidState.VALID) }

            else -> _joinUiState.update { it.copy(joinPassValidState = InputValidState.NONE) }
        }
    }

    fun validateJoinName(name: CharSequence) {
        when (name.isNotBlank()) {
            true -> _joinUiState.update { it.copy(joinNameValidState = InputValidState.VALID) }

            else -> _joinUiState.update { it.copy(joinNameValidState = InputValidState.NONE) }
        }
    }
}