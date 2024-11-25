package com.ssafy.smartstore_jetpack.app

import android.Manifest
import android.app.Application
import android.content.Context
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class ApplicationClass : Application() {

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()

        // Firebase 초기화
        FirebaseApp.initializeApp(this)

        sharedPreferencesUtil = SharedPreferencesUtil(myContext())

        Timber.plant(Timber.DebugTree())
    }

    companion object {

//        const val SERVER_URL = "http://mobile-pjt.sample.ssafy.io/"

        // JW 서버 주소
        const val SERVER_URL = "http://192.168.33.129:9987/"

        // SM 서버 주소
        // const val SERVER_URL = "http://192.168.33.130:9987/"

        // JW 핫스팟 서버 주소
        // const val SERVER_URL = "http://192.168.43.161:9987/"

        // JW 자취방 와이파이 주소
        // const val SERVER_URL = "http://192.168.0.200:9987/"

        const val MENU_IMGS_URL = "${SERVER_URL}imgs/menu/"
        const val IMGS_URL = "${SERVER_URL}imgs/"
        const val GRADE_URL = "${SERVER_URL}imgs/grade/"

        // 모든 퍼미션 관련 배열
        val requiredPermissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_ADVERTISE,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.POST_NOTIFICATIONS
        )

        lateinit var sharedPreferencesUtil: SharedPreferencesUtil
        var instance: ApplicationClass? = null

        // 주문 준비 완료 확인 시간 1분
        const val ORDER_COMPLETED_TIME = 60 * 1000

        fun myContext(): Context {
            return instance!!.applicationContext
        }
    }
}