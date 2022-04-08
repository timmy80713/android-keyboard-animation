package com.timmy.codelab.keyboardanimation.widget.keyboard

import android.widget.EditText
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

internal class KeyboardAnimationCompatImpl29 {

    private var keyboardInsetsBottom = 0

    fun setupKeyboardAnimations(
        editText: EditText,
        onKeyboardInsetsBottomChanged: (keyboardInsetsBottom: Int) -> Unit
    ) {
        ViewCompat.setOnApplyWindowInsetsListener(editText) { _, insets ->

            val systemBarsOrImeInsets = insets.getInsets(
                WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.ime()
            )
            keyboardInsetsBottom = systemBarsOrImeInsets.bottom
            onKeyboardInsetsBottomChanged.invoke(keyboardInsetsBottom)

            insets
        }
    }
}