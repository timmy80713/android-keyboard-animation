package com.timmy.codelab.keyboardanimation.widget.keyboard

import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

internal class KeyboardAnimationCompatImpl10(
    private val root: View,
    private val keyboardInsetsBottomUpdated: (keyboardInsetsBottom: Int) -> Unit
) {

    private var keyboardInsetsBottom = 0

    fun setupKeyboardAnimations() {
        ViewCompat.setOnApplyWindowInsetsListener(root) { _, windowInsets ->
            val systemBarOrImeInsets = windowInsets.getInsets(
                WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.ime()
            )
            keyboardInsetsBottom = systemBarOrImeInsets.bottom
            keyboardInsetsBottomUpdated.invoke(keyboardInsetsBottom)
            windowInsets
        }
    }
}