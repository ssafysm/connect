package com.ssafy.smartstore_jetpack.presentation.util

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.widget.ImageView
import androidx.appcompat.widget.SwitchCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.widget.NestedScrollView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import coil.load
import coil.transform.RoundedCornersTransformation
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.ssafy.smartstore_jetpack.R
import com.ssafy.smartstore_jetpack.app.ApplicationClass
import timber.log.Timber

@BindingAdapter("app:backgroundCustomTheme")
fun ConstraintLayout.bindBackgroundColor(appThemeName: String) {
    when (appThemeName) {
        "기본" -> setBackgroundColor(resources.getColor(R.color.background_main, context.theme))

        "봄" -> setBackgroundColor(resources.getColor(R.color.spring_primary, context.theme))

        "여름" -> setBackgroundColor(resources.getColor(R.color.summer_primary, context.theme))

        "가을" -> setBackgroundColor(resources.getColor(R.color.autumn_primary, context.theme))

        "겨울" -> setBackgroundColor(resources.getColor(R.color.winter_primary, context.theme))
    }
}

@BindingAdapter("app:nestedScrollViewCustomTheme")
fun NestedScrollView.bindNestedScrollViewBackgroundColor(appThemeName: String) {
    when (appThemeName) {
        "기본" -> setBackgroundColor(resources.getColor(R.color.background_main, context.theme))

        "봄" -> setBackgroundColor(resources.getColor(R.color.spring_primary, context.theme))

        "여름" -> setBackgroundColor(resources.getColor(R.color.summer_primary, context.theme))

        "가을" -> setBackgroundColor(resources.getColor(R.color.autumn_primary, context.theme))

        "겨울" -> setBackgroundColor(resources.getColor(R.color.winter_primary, context.theme))
    }
}

@BindingAdapter("app:backgroundCustomSubTheme")
fun ConstraintLayout.bindSubBackgroundColor(appThemeName: String) {
    when (appThemeName) {
        "기본" -> setBackgroundResource(R.drawable.shape_background_sub)

        "봄" -> setBackgroundResource(R.drawable.shape_background_spring_sub)

        "여름" -> setBackgroundResource(R.drawable.shape_background_summer_sub)

        "가을" -> setBackgroundResource(R.drawable.shape_background_autumn_sub)

        "겨울" -> setBackgroundResource(R.drawable.shape_background_winter_sub)
    }
}

@BindingAdapter("app:noticeCustomTheme")
fun ImageView.bindNoticeBackground(appThemeName: String) {
    when (appThemeName) {
        "기본" -> load(R.drawable.ic_notice)

        "봄" -> load(R.drawable.ic_notice_spring)

        "여름" -> load(R.drawable.ic_notice_summer)

        "가을" -> load(R.drawable.ic_notice_autumn)

        "겨울" -> load(R.drawable.ic_notice_winter)
    }
}

@BindingAdapter("app:settingsCustomTheme")
fun ImageView.bindSettingsBackground(appThemeName: String) {
    when (appThemeName) {
        "기본" -> load(R.drawable.ic_settings)

        "봄" -> load(R.drawable.ic_settings_spring)

        "여름" -> load(R.drawable.ic_settings_summer)

        "가을" -> load(R.drawable.ic_settings_autumn)

        "겨울" -> load(R.drawable.ic_settings_winter)
    }
}

@BindingAdapter("app:coordinatorCustomTheme")
fun CoordinatorLayout.bindCoordinatorBackgroundColor(appThemeName: String) {
    when (appThemeName) {
        "기본" -> setBackgroundColor(resources.getColor(R.color.background_main, context.theme))

        "봄" -> setBackgroundColor(resources.getColor(R.color.spring_primary, context.theme))

        "여름" -> setBackgroundColor(resources.getColor(R.color.summer_primary, context.theme))

        "가을" -> setBackgroundColor(resources.getColor(R.color.autumn_primary, context.theme))

        "겨울" -> setBackgroundColor(resources.getColor(R.color.winter_primary, context.theme))
    }
}

@SuppressLint("UseCompatLoadingForDrawables")
@BindingAdapter("app:switchCustomTheme")
fun SwitchCompat.bindSwitchCustomTheme(appThemeName: String) {
    when (appThemeName) {
        "기본" -> {
            thumbDrawable = resources.getDrawable(R.drawable.switch_thumb, context.theme)
            trackDrawable = resources.getDrawable(R.drawable.switch_track, context.theme)
        }

        "봄" -> {
            thumbDrawable = resources.getDrawable(R.drawable.switch_thumb_spring, context.theme)
            trackDrawable = resources.getDrawable(R.drawable.switch_track_spring, context.theme)
        }

        "여름" -> {
            thumbDrawable = resources.getDrawable(R.drawable.switch_thumb_summer, context.theme)
            trackDrawable = resources.getDrawable(R.drawable.switch_track_summer, context.theme)
        }

        "가을" -> {
            thumbDrawable = resources.getDrawable(R.drawable.switch_thumb_autumn, context.theme)
            trackDrawable = resources.getDrawable(R.drawable.switch_track_autumn, context.theme)
        }

        "겨울" -> {
            thumbDrawable = resources.getDrawable(R.drawable.switch_thumb_winter, context.theme)
            trackDrawable = resources.getDrawable(R.drawable.switch_track_winter, context.theme)
        }
    }
}

@BindingAdapter("app:selectTextColor")
fun TabLayout.bindTextColor(appThemeName: String) {
    val normalTextColor = resources.getColor(R.color.neutral_70, context.theme)
    val selectedTextColor = when (appThemeName) {
        "봄" -> resources.getColor(R.color.spring_secondary, context.theme)

        "여름" -> resources.getColor(R.color.summer_secondary, context.theme)

        "가을" -> resources.getColor(R.color.autumn_secondary, context.theme)

        "겨울" -> resources.getColor(R.color.winter_secondary, context.theme)

        else -> resources.getColor(R.color.background_sub, context.theme)
    }

    setTabTextColors(normalTextColor, selectedTextColor)
}

@BindingAdapter("app:bottomNavigationBarCustomTheme")
fun BottomAppBar.bindBottomBackgroundColor(appThemeName: String) {
    when (appThemeName) {
        "기본" -> backgroundTint =
            ColorStateList.valueOf(resources.getColor(R.color.background_sub, context.theme))

        "봄" -> backgroundTint =
            ColorStateList.valueOf(resources.getColor(R.color.spring_secondary, context.theme))

        "여름" -> backgroundTint =
            ColorStateList.valueOf(resources.getColor(R.color.summer_secondary, context.theme))

        "가을" -> backgroundTint =
            ColorStateList.valueOf(resources.getColor(R.color.autumn_secondary, context.theme))

        "겨울" -> backgroundTint =
            ColorStateList.valueOf(resources.getColor(R.color.winter_secondary, context.theme))
    }
}

@BindingAdapter("app:fabCustomTheme")
fun FloatingActionButton.bindFloatingActionButtonBackgroundColor(appThemeName: String) {
    when (appThemeName) {
        "기본" -> backgroundTintList =
            ColorStateList.valueOf(resources.getColor(R.color.background_sub, context.theme))

        "봄" -> backgroundTintList =
            ColorStateList.valueOf(resources.getColor(R.color.spring_secondary, context.theme))

        "여름" -> backgroundTintList =
            ColorStateList.valueOf(resources.getColor(R.color.summer_secondary, context.theme))

        "가을" -> backgroundTintList =
            ColorStateList.valueOf(resources.getColor(R.color.autumn_secondary, context.theme))

        "겨울" -> backgroundTintList =
            ColorStateList.valueOf(resources.getColor(R.color.winter_secondary, context.theme))
    }
}

@BindingAdapter("app:photoUrl")
fun ImageView.bindImage(src: String?) {
    if (!src.isNullOrEmpty()) {
        load("${ApplicationClass.MENU_IMGS_URL}$src") {
            transformations(RoundedCornersTransformation(10F))
        }
    } else {
        this.setImageResource(R.drawable.logo)
    }
}

@BindingAdapter("app:eventImage")
fun ImageView.bindEventImage(src: String) {
    load(src) {
        transformations(RoundedCornersTransformation(20F))
    }
    scaleType = ImageView.ScaleType.CENTER_INSIDE
}

@SuppressLint("DiscouragedApi")
@BindingAdapter("app:stampImage")
fun ImageView.bindStamp(src: String) {
    val resourceName = src.split(".")[0]
    val packageName = context.packageName
    val resId = resources.getIdentifier(resourceName, "drawable", packageName)

    if (resId != 0) {
        load(resId)
        scaleType = ImageView.ScaleType.CENTER_CROP
    } else {
        load(R.drawable.seeds)
    }
}

@BindingAdapter("adapter")
fun RecyclerView.bindAdapter(adapter: RecyclerView.Adapter<*>?) {
    this.adapter = adapter
}

@BindingAdapter("submitData")
fun <T> RecyclerView.submitData(items: List<T>?) {
    val adapter = this.adapter as? ListAdapter<T, *> ?: return
    adapter.submitList(items ?: emptyList())
}

@BindingAdapter("submitDataViewPager")
fun <T> ViewPager2.submitData(items: List<T>?) {
    Timber.d("Data submitted: ${items?.size ?: 0}")
    val adapter = this.adapter as? ListAdapter<T, *>
    adapter?.submitList(items ?: emptyList())
}