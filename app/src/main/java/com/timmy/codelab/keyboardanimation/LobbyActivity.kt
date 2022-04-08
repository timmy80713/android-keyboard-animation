package com.timmy.codelab.keyboardanimation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.*
import com.timmy.codelab.keyboardanimation.arch.ViewBindingActivity
import com.timmy.codelab.keyboardanimation.databinding.ActivityLobbyBinding
import com.timmy.codelab.keyboardanimation.widget.keyboard.KeyboardAnimationCompat

class LobbyActivity : ViewBindingActivity<ActivityLobbyBinding>() {

    private val keyboardAnimationCompat = KeyboardAnimationCompat()

    override fun createViewBinding(inflater: LayoutInflater) =
        ActivityLobbyBinding.inflate(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupWindow()
    }

    private fun setupWindow() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        ViewCompat.setOnApplyWindowInsetsListener(viewBinding.root) { _, insets ->

            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            viewBinding.root.updatePadding(
                left = systemBarsInsets.left,
                right = systemBarsInsets.right
            )

            insets
        }
        keyboardAnimationCompat.registerKeyboardAnimations(
            editText = viewBinding.editorEditText,
            onKeyboardInsetsBottomChanged = ::onKeyboardInsetsBottomChanged
        )
    }

    private fun onKeyboardInsetsBottomChanged(keyboardInsetsBottom: Int) {
        viewBinding.bottomSpace.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            updateMargins(bottom = keyboardInsetsBottom)
        }
    }
}