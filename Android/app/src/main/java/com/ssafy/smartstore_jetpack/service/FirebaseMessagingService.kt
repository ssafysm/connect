package com.ssafy.smartstore_jetpack.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.ssafy.smartstore_jetpack.R
import com.ssafy.smartstore_jetpack.presentation.views.main.MainActivity

class MyFirebaseMessagingService : FirebaseMessagingService() {

	private val TAG = "MyFirebaseMessagingService"

	override fun onNewToken(token: String) {
		super.onNewToken(token)
		Log.d(TAG, "새 FCM 토큰: $token")

		// 필요 시 서버에 토큰 업로드
		uploadTokenToServer(token)
	}

	override fun onMessageReceived(remoteMessage: RemoteMessage) {
		super.onMessageReceived(remoteMessage)
		var messageTitle = ""
		var messageContent = ""

		// 알림 페이로드가 있는 경우
		if (remoteMessage.notification != null) {
			Log.d(TAG, "FCM 메시지 수신: ${remoteMessage.data}")
			val title = remoteMessage.notification?.title ?: "새 알림"
			val message = remoteMessage.notification?.body ?: "알림 내용이 없습니다."
			sendNotification(title, message)

		} else {
			// 데이터 페이로드 처리
			val title = remoteMessage.data["myTitle"] ?: "새 알림"
			val message = remoteMessage.data["myBody"] ?: "알림 내용이 없습니다."
			Log.d(TAG, "FCM 메시지 수신: title=$title, body=$message")
			sendNotification(title, message)
		}
	}

	private fun sendNotification(title: String, message: String) {
		val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
		val channelId = "fcm_channel"

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			val channel = NotificationChannel(
				channelId,
				"FCM 알림",
				NotificationManager.IMPORTANCE_HIGH
			)
			notificationManager.createNotificationChannel(channel)
		}

		val intent = Intent(this, MainActivity::class.java).apply {
			flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
		}
		val pendingIntent = PendingIntent.getActivity(
			this, 0, intent,
			PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
		)

		val notification = NotificationCompat.Builder(this, channelId)
			.setContentTitle(title)
			.setContentText(message)
			.setSmallIcon(R.drawable.cookie) // 알림 아이콘 설정
			.setAutoCancel(true)
			.setContentIntent(pendingIntent)
			.build()

		notificationManager.notify(0, notification)
	}

	private fun uploadTokenToServer(token: String) {
		// 서버로 FCM 토큰을 전송하는 로직을 여기에 구현하세요.
		Log.d(TAG, "FCM 토큰 서버 전송: $token")
	}
}
