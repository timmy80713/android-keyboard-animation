package com.timmy.codelab.keyboardanimation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.*
import com.timmy.codelab.keyboardanimation.arch.ViewBindingActivity
import com.timmy.codelab.keyboardanimation.databinding.ActivityLobbyBinding
import com.timmy.codelab.keyboardanimation.widget.keyboard.KeyboardAnimationCompat
import com.timmy.codelab.keyboardanimation.widget.keyboard.KeyboardAnimationCompatImpl

class LobbyActivity : ViewBindingActivity<ActivityLobbyBinding>() {

    private val keyboardAnimationHelper by lazy<KeyboardAnimationCompat> {
        KeyboardAnimationCompatImpl(viewBinding.root, ::keyboardInsetsBottomUpdated)
    }

    override fun createViewBinding(inflater: LayoutInflater) =
        ActivityLobbyBinding.inflate(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupWindow()
    }

    private fun setupWindow() {
        // Edge to edge
        WindowCompat.setDecorFitsSystemWindows(window, false)
        ViewCompat.setOnApplyWindowInsetsListener(viewBinding.root) { _, windowInsets ->
            val insetsType = WindowInsetsCompat.Type.systemBars()
            val insets = windowInsets.getInsets(insetsType)
            viewBinding.root.updatePadding(
                left = insets.left,
                right = insets.right
            )
            windowInsets
        }
        keyboardAnimationHelper.setupKeyboardAnimations()
    }

    private fun keyboardInsetsBottomUpdated(keyboardInsetsBottom: Int) {
        viewBinding.bottomSpace.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            updateMargins(bottom = keyboardInsetsBottom)
        }
    }
}