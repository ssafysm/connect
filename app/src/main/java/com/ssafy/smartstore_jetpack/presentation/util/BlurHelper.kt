package com.ssafy.smartstore_jetpack.presentation.util

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.view.View

object BlurHelper {

    fun applyBlur(view: View, radius: Float) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            view.setRenderEffect(
                RenderEffect.createBlurEffect(radius, radius, Shader.TileMode.CLAMP)
            )
        }
    }

    fun clearBlur(view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            view.setRenderEffect(null)
        }
    }
}
