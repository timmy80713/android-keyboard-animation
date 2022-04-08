package com.timmy.codelab.keyboardanimation.widget.keyboard

import android.os.Build
import android.widget.EditText

class KeyboardAnimationCompat {

    private val keyboardAnimationCompatImpl29 by lazy { KeyboardAnimationCompatImpl29() }
    private val keyboardAnimationCompatImpl30 by lazy { KeyboardAnimationCompatImpl30() }

    fun registerKeyboardAnimations(
        editText: EditText,
        onKeyboardInsetsBottomChanged: (keyboardInsetsBottom: Int) -> Unit
    ) {
        if (isAtLeastR) {
            keyboardAnimationCompatImpl30.setupKeyboardAnimations(
                editText,
                onKeyboardInsetsBottomChanged
            )
        } else {
            keyboardAnimationCompatImpl29.setupKeyboardAnimations(
                editText,
                onKeyboardInsetsBottomChanged
            )
        }
    }

    private val isAtLeastR: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.R
}