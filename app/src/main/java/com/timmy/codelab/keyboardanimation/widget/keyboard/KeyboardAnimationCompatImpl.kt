package com.timmy.codelab.keyboardanimation.widget.keyboard

import android.os.Build
import android.view.View

class KeyboardAnimationCompatImpl(
    private val root: View,
    private val keyboardInsetsBottomUpdated: (keyboardInsetsBottom: Int) -> Unit
) : KeyboardAnimationCompat {

    private val keyboardAnimationHelper10 by lazy {
        KeyboardAnimationCompatImpl10(root, keyboardInsetsBottomUpdated)
    }
    private val keyboardAnimationHelper11 by lazy {
        KeyboardAnimationCompatImpl11(root, keyboardInsetsBottomUpdated)
    }

    override fun setupKeyboardAnimations() {
        if (isAtLeastAndroid11()) {
            keyboardAnimationHelper11.setupKeyboardAnimations()
        } else {
            keyboardAnimationHelper10.setupKeyboardAnimations()
        }
    }

    private fun isAtLeastAndroid11() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.R
}