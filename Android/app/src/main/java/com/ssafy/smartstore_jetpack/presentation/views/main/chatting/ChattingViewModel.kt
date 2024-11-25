package com.ssafy.smartstore_jetpack.presentation.views.main.chatting

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.smartstore_jetpack.domain.model.UserInfo
import com.ssafy.smartstore_jetpack.domain.usecase.GetCookieUseCase
import com.ssafy.smartstore_jetpack.domain.usecase.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChattingViewModel @Inject constructor(
	private val getUserUseCase: GetUserUseCase,
	private val getCookieUseCase: GetCookieUseCase
) : ViewModel() {

	private val _chatList = MutableLiveData<MutableList<ChatMessage>>(mutableListOf())
	val chatList: LiveData<MutableList<ChatMessage>> = _chatList

	private val _imageUri = MutableLiveData<Uri?>(null)
	val imageUri: LiveData<Uri?> = _imageUri

	private val _userInfo = MutableLiveData<UserInfo?>(null)
	val userInfo: LiveData<UserInfo?> = _userInfo

	fun setImageUri(uri: Uri?) {
		_imageUri.value = uri
	}

	fun sendMessage(context: Context, text: String) {
		val image = imageUri.value
		val userName = userInfo.value?.user?.name ?: "User" // 사용자 이름 가져오기
		val newMessage = ChatMessage(
			text = text,
			imageUri = image,
			isSender = true,
			senderName = userName
		)
		addMessage(newMessage)

		viewModelScope.launch {
			try {
				// senderName을 추가로 전달
				val response = ChatApi.sendMessage(context, text, image, senderName = userName)
				addMessage(response)
			} catch (e: Exception) {
				addMessage(
					ChatMessage(
						text = "Error: ${e.message}",
						imageUri = null,
						isSender = false,
						senderName = "System"
					)
				)
			}
		}
	}

	private fun addMessage(message: ChatMessage) {
		_chatList.value = (_chatList.value ?: mutableListOf()).apply {
			add(message)
		}
	}

	fun loadUserInfo() {
		viewModelScope.launch {
			val cookies = getCookieUseCase.getLoginCookie().firstOrNull()
			val userId = cookies?.firstOrNull()
			if (!userId.isNullOrEmpty()) {
				val response = getUserUseCase.getUserInfo(userId)
				if (response.status == com.ssafy.smartstore_jetpack.domain.model.Status.SUCCESS) {
					_userInfo.value = response.data
				}
			}
		}
	}
}
