package com.ssafy.smartstore_jetpack.app

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesUtil(context: Context) {

	val SHARED_PREFERENCES_NAME = "smartstore_preference"

	var preferences: SharedPreferences =
		context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

	//    fun addNotice(info: String) {
//        val list = getNotice()
//
//        list.add(info)
//        val json = Gson().toJson(list)
//
//        preferences.edit().let {
//            it.putString("notice", json)
//            it.apply()
//        }
//    }
//
//    fun setNotice(list: MutableList<String>) {
//        preferences.edit().let {
//            it.putString("notice", Gson().toJson(list))
//            it.apply()
//        }
//    }
//
//    fun getNotice(): MutableList<String> {
//        val str = preferences.getString("notice", "")!!
//        val list = if (str.isEmpty()) mutableListOf<String>() else Gson().fromJson(
//            str,
//            MutableList::class.java
//        ) as MutableList<String>
//
//        return list0
//    }
// 추가: 마지막 팝업 표시 시간 저장 및 불러오기
	fun setLastPopupShownTime(time: Long) {
		val editor = preferences.edit()
		editor.putLong("lastPopupShownTime", time)
		editor.apply()
	}

	fun getLastPopupShownTime(): Long {
		return preferences.getLong("lastPopupShownTime", 0)
	}

}