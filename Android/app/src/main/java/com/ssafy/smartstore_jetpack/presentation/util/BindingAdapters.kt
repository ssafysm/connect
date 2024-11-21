package com.ssafy.smartstore_jetpack.presentation.util

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
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
import com.google.android.material.textfield.TextInputLayout
import com.ssafy.smartstore_jetpack.R
import com.ssafy.smartstore_jetpack.app.ApplicationClass
import com.ssafy.smartstore_jetpack.presentation.views.main.join.JoinUiState
import com.ssafy.smartstore_jetpack.presentation.views.main.login.LoginUiState
import com.ssafy.smartstore_jetpack.presentation.views.main.password.PasswordUiState
import timber.log.Timber
import kotlin.math.log

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
fun ConstraintLayout.bindSubBackgroundColor(appThemeName: String?) {
    when (appThemeName) {
        "봄" -> setBackgroundResource(R.drawable.shape_background_spring_sub)

        "여름" -> setBackgroundResource(R.drawable.shape_background_summer_sub)

        "가을" -> setBackgroundResource(R.drawable.shape_background_autumn_sub)

        "겨울" -> setBackgroundResource(R.drawable.shape_background_winter_sub)

        else -> setBackgroundResource(R.drawable.shape_background_sub)
    }
}

@BindingAdapter("app:bottomSheetCustomTheme")
fun ConstraintLayout.bindBottomSheetCustomTheme(appThemeName: String) {
    when (appThemeName) {
        "봄" -> setBackgroundResource(R.drawable.shape_bottom_sheet_spring)

        "여름" -> setBackgroundResource(R.drawable.shape_bottom_sheet_summer)

        "가을" -> setBackgroundResource(R.drawable.shape_bottom_sheet_autumn)

        "겨울" -> setBackgroundResource(R.drawable.shape_bottom_sheet_winter)

        else -> setBackgroundResource(R.drawable.shape_bottom_sheet_main)
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
fun ImageView.bindEventImage(src: String?) {
    if (!src.isNullOrEmpty()) {
        load(src) {
            transformations(RoundedCornersTransformation(20F))
        }
    } else {
        this.setImageResource(R.drawable.logo)
    }
    scaleType = ImageView.ScaleType.MATRIX
}

@BindingAdapter("app:homeBanner")
fun ImageView.bindHomeBanner(appThemeName: String) {
    when (appThemeName) {
        "봄" -> load(R.drawable.home_banner_spring) {
            transformations(RoundedCornersTransformation(0F, 0F, 20F, 20F))
        }

        "여름" -> load(R.drawable.home_banner_summer) {
            transformations(RoundedCornersTransformation(0F, 0F, 20F, 20F))
        }

        "가을" -> load(R.drawable.home_banner_autumn) {
            transformations(RoundedCornersTransformation(0F, 0F, 20F, 20F))
        }

        "겨울" -> load(R.drawable.home_banner_winter) {
            transformations(RoundedCornersTransformation(0F, 0F, 20F, 20F))
        }

        else -> load(R.drawable.home_banner_main) {
            transformations(RoundedCornersTransformation(0F, 0F, 20F, 20F))
        }
    }
}

@BindingAdapter("app:homeBannerBackground")
fun CardView.bindHomeBannerBackground(appThemeName: String) {
    when (appThemeName) {
        "봄" -> setBackgroundResource(R.drawable.background_banner_spring)

        "여름" -> setBackgroundResource(R.drawable.background_banner_summer)

        "가을" -> setBackgroundResource(R.drawable.background_banner_autumn)

        "겨울" -> setBackgroundResource(R.drawable.background_banner_winter)

        else -> setBackgroundResource(R.drawable.background_banner)
    }
}

@BindingAdapter("app:countBackground")
fun CardView.bindCountBackground(appThemeName: String) {
    when (appThemeName) {
        "봄" -> setBackgroundResource(R.drawable.shape_background_spring_sub)

        "여름" -> setBackgroundResource(R.drawable.shape_background_summer_sub)

        "가을" -> setBackgroundResource(R.drawable.shape_background_autumn_sub)

        "겨울" -> setBackgroundResource(R.drawable.shape_background_winter_sub)

        else -> setBackgroundResource(R.drawable.shape_background_sub)
    }
}

@BindingAdapter("app:homeBannerText")
fun TextView.bindHomeBannerText(appThemeName: String) {
    when (appThemeName) {
        "봄" -> setTextColor(resources.getColor(R.color.neutral_white, context.theme))

        "여름" -> setTextColor(resources.getColor(R.color.neutral_100, context.theme))

        "가을" -> setTextColor(resources.getColor(R.color.neutral_white, context.theme))

        "겨울" -> setTextColor(resources.getColor(R.color.neutral_100, context.theme))

        else -> setTextColor(resources.getColor(R.color.neutral_white, context.theme))
    }
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

@BindingAdapter("app:buttonCustomTheme")
fun Button.bindButtonBackground(appThemeName: String) {
    when (appThemeName) {
        "봄" -> setBackgroundResource(R.drawable.shape_background_spring_sub)

        "여름" -> setBackgroundResource(R.drawable.shape_background_summer_sub)

        "가을" -> setBackgroundResource(R.drawable.shape_background_autumn_sub)

        "겨울" -> setBackgroundResource(R.drawable.shape_background_winter_sub)

        else -> setBackgroundResource(R.drawable.shape_background_sub)
    }
}

@BindingAdapter("app:enableLogin", "app:enableLoginCustomTheme")
fun Button.bindEnableCustomTheme(loginUiState: LoginUiState, appThemeName: String) {
    when (loginUiState.isLoginBtnEnable) {
        true -> {
            when (appThemeName) {
                "봄" -> setBackgroundResource(R.drawable.shape_background_spring_sub)

                "여름" -> setBackgroundResource(R.drawable.shape_background_summer_sub)

                "가을" -> setBackgroundResource(R.drawable.shape_background_autumn_sub)

                "겨울" -> setBackgroundResource(R.drawable.shape_background_winter_sub)

                else -> setBackgroundResource(R.drawable.shape_background_sub)
            }
        }

        else -> setBackgroundResource(R.drawable.button_disabled)
    }
}

@BindingAdapter("app:enableJoin", "app:enableJoinCustomTheme")
fun Button.bindEnableJoinCustomTheme(joinUiState: JoinUiState, appThemeName: String) {
    when (joinUiState.isJoinBtnEnable) {
        true -> {
            when (appThemeName) {
                "봄" -> setBackgroundResource(R.drawable.shape_background_spring_sub)

                "여름" -> setBackgroundResource(R.drawable.shape_background_summer_sub)

                "가을" -> setBackgroundResource(R.drawable.shape_background_autumn_sub)

                "겨울" -> setBackgroundResource(R.drawable.shape_background_winter_sub)

                else -> setBackgroundResource(R.drawable.shape_background_sub)
            }
        }

        else -> setBackgroundResource(R.drawable.button_disabled)
    }
}

@SuppressLint("ResourceType")
@BindingAdapter("app:validateNewPassword", "app:passwordCustomTheme")
fun TextInputLayout.bindNewPassword(joinUiState: JoinUiState, appThemeName: String) {
    val hintColor = when (appThemeName) {
        "봄" -> ContextCompat.getColor(context, R.color.spring_secondary)

        "여름" -> ContextCompat.getColor(context, R.color.summer_secondary)

        "가을" -> ContextCompat.getColor(context, R.color.autumn_secondary)

        "겨울" -> ContextCompat.getColor(context, R.color.winter_secondary)

        else -> ContextCompat.getColor(context, R.color.background_sub)
    }

    when (joinUiState.joinPassValidState) {
        PasswordState.VALID -> {
            helperText = "사용해도 좋은 비밀번호에요."
            defaultHintTextColor = ColorStateList.valueOf(hintColor)
        }

        PasswordState.INIT -> {
            helperText = ""
            error = ""
            defaultHintTextColor = ColorStateList.valueOf(
                ContextCompat.getColor(context, R.color.neutral_70)
            )
        }

        else -> {
            error = "유효한 비밀번호가 아니에요."
            defaultHintTextColor = ColorStateList.valueOf(
                ContextCompat.getColor(context, R.color.sub_alert)
            )
        }
    }
}

@SuppressLint("ResourceType")
@BindingAdapter("app:validateNewPasswordConfirm", "app:passwordConfirmCustomTheme")
fun TextInputLayout.bindNewPasswordConfirm(joinUiState: JoinUiState, appThemeName: String) {
    val hintColor = when (appThemeName) {
        "봄" -> ContextCompat.getColor(context, R.color.spring_secondary)

        "여름" -> ContextCompat.getColor(context, R.color.summer_secondary)

        "가을" -> ContextCompat.getColor(context, R.color.autumn_secondary)

        "겨울" -> ContextCompat.getColor(context, R.color.winter_secondary)

        else -> ContextCompat.getColor(context, R.color.background_sub)
    }

    when (joinUiState.joinPassConfirmValidState) {
        PasswordState.VALID -> {
            helperText = "사용해도 좋은 비밀번호에요."
            defaultHintTextColor = ColorStateList.valueOf(hintColor)
        }

        PasswordState.INIT -> {
            helperText = ""
            error = ""
            defaultHintTextColor = ColorStateList.valueOf(
                ContextCompat.getColor(context, R.color.neutral_70)
            )
        }

        else -> {
            error = "유효한 비밀번호가 아니에요."
            defaultHintTextColor = ColorStateList.valueOf(
                ContextCompat.getColor(context, R.color.sub_alert)
            )
        }
    }
}

@SuppressLint("ResourceType")
@BindingAdapter("app:validatePassword", "app:newPasswordCustomTheme")
fun TextInputLayout.bindPassword(passwordUiState: PasswordUiState, appThemeName: String) {
    val hintColor = when (appThemeName) {
        "봄" -> ContextCompat.getColor(context, R.color.spring_secondary)

        "여름" -> ContextCompat.getColor(context, R.color.summer_secondary)

        "가을" -> ContextCompat.getColor(context, R.color.autumn_secondary)

        "겨울" -> ContextCompat.getColor(context, R.color.winter_secondary)

        else -> ContextCompat.getColor(context, R.color.background_sub)
    }

    when (passwordUiState.newPasswordValidState) {
        PasswordState.VALID -> {
            helperText = "사용해도 좋은 비밀번호에요."
            defaultHintTextColor = ColorStateList.valueOf(hintColor)
        }

        PasswordState.INIT -> {
            helperText = ""
            error = ""
            defaultHintTextColor = ColorStateList.valueOf(
                ContextCompat.getColor(context, R.color.neutral_70)
            )
        }

        else -> {
            error = "유효한 비밀번호가 아니에요."
            defaultHintTextColor = ColorStateList.valueOf(
                ContextCompat.getColor(context, R.color.sub_alert)
            )
        }
    }
}

@SuppressLint("ResourceType")
@BindingAdapter("app:validatePasswordConfirm", "app:newPasswordConfirmCustomTheme")
fun TextInputLayout.bindPasswordConfirm(passwordUiState: PasswordUiState, appThemeName: String) {
    val hintColor = when (appThemeName) {
        "봄" -> ContextCompat.getColor(context, R.color.spring_secondary)

        "여름" -> ContextCompat.getColor(context, R.color.summer_secondary)

        "가을" -> ContextCompat.getColor(context, R.color.autumn_secondary)

        "겨울" -> ContextCompat.getColor(context, R.color.winter_secondary)

        else -> ContextCompat.getColor(context, R.color.background_sub)
    }

    when (passwordUiState.newPasswordConfirmValidState) {
        PasswordState.VALID -> {
            helperText = "비밀번호가 일치해요."
            defaultHintTextColor = ColorStateList.valueOf(hintColor)
        }

        PasswordState.INIT -> {
            helperText = ""
            error = ""
            defaultHintTextColor = ColorStateList.valueOf(
                ContextCompat.getColor(context, R.color.neutral_70)
            )
        }

        else -> {
            error = "비밀번호가 일치하지 않아요."
            defaultHintTextColor = ColorStateList.valueOf(
                ContextCompat.getColor(context, R.color.sub_alert)
            )
        }
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