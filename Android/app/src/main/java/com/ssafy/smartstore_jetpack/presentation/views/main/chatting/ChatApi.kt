package com.ssafy.smartstore_jetpack.presentation.views.main.chatting

import android.content.Context
import android.net.Uri
import android.util.Base64
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject

object ChatApi {
	private const val API_URL = "https://api.openai.com/v1/chat/completions"
	private const val API_KEY = "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"

	private val client = OkHttpClient()

	suspend fun sendMessage(
		context: Context,
		text: String,
		imageUri: Uri?,
		senderName: String // 추가된 senderName 매개변수
	): ChatMessage {
		return withContext(Dispatchers.IO) {
			try {
				val messages = if (imageUri != null) {
					JSONArray().apply {
						put(JSONObject().apply {
							put("role", "user")
							put("content", JSONArray().apply {
								put(JSONObject().apply {
									put("type", "text")
									put("text", text.ifEmpty { "Analyze this image" })
								})
								put(JSONObject().apply {
									put("type", "image_url")
									put("image_url", JSONObject().apply {
										put("url", encodeImageToBase64(context, imageUri))
									})
								})
							})
						})
					}
				} else {
					JSONArray().apply {
						put(JSONObject().apply {
							put("role", "user")
							put("content", text)
						})
					}
				}

				val requestData = JSONObject().apply {
					put("model", "gpt-4o-mini")
					put("messages", messages)
					put("max_tokens", 2000)
				}

				val request = Request.Builder()
					.url(API_URL)
					.post(RequestBody.create("application/json".toMediaTypeOrNull(), requestData.toString()))
					.addHeader("Authorization", "Bearer $API_KEY")
					.build()

				val response = client.newCall(request).execute()
				if (!response.isSuccessful) {
					throw Exception("API Error: ${response.code}")
				}

				val responseBody = response.body?.string() ?: throw Exception("Empty response")
				val jsonResponse = JSONObject(responseBody)
				val assistantMessage = jsonResponse.getJSONArray("choices")
					.getJSONObject(0)
					.getJSONObject("message")
					.getString("content")

				ChatMessage(
					text = assistantMessage,
					imageUri = null,
					isSender = false,
					senderName = "GPT-4" // GPT의 이름
				)
			} catch (e: Exception) {
				e.printStackTrace()
				ChatMessage(
					text = "Error: ${e.message}",
					imageUri = null,
					isSender = false,
					senderName = "System"
				)
			}
		}
	}

	private fun encodeImageToBase64(context: Context, imageUri: Uri): String {
		val inputStream = context.contentResolver.openInputStream(imageUri)
		val bytes = inputStream?.readBytes() ?: ByteArray(0)
		inputStream?.close()
		return "data:image/jpeg;base64,${Base64.encodeToString(bytes, Base64.NO_WRAP)}"
	}
}
