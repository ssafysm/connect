package com.ssafy.smartstore_jetpack.presentation.config

import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ssafy.smartstore_jetpack.R

abstract class BaseBottomSheetDialogFragment<T : ViewDataBinding>(private val layoutId: Int) :
    BottomSheetDialogFragment() {

    private var _binding: T? = null
    protected val binding
        get() = requireNotNull(_binding)
    open lateinit var behavior: BottomSheetBehavior<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NORMAL, R.style.TransparentBottomSheetDialogFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.run {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            requestFeature(Window.FEATURE_NO_TITLE)
        }

        _binding = DataBindingUtil.inflate<T>(inflater, layoutId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        isCancelable = false

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rootView = requireView()
        rootView.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            rootView.getWindowVisibleDisplayFrame(rect)
            val screenHeight = rootView.rootView.height
            val keypadHeight = screenHeight - rect.bottom

            if (keypadHeight > screenHeight * 0.15) { // 키보드가 화면의 15% 이상 차지할 때
                // 키보드가 올라왔을 때
                (dialog as? BottomSheetDialog)?.behavior?.state = BottomSheetBehavior.STATE_EXPANDED
            } else {
                // 키보드가 내려갔을 때
                (dialog as? BottomSheetDialog)?.behavior?.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
    }

    override fun onStart() {
        super.onStart()

        dialog?.let { dialog ->
            val bottomSheet = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            behavior = BottomSheetBehavior.from(bottomSheet!!)
            behavior.isDraggable = true

            dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}