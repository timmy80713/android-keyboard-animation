package com.timmy.codelab.keyboardanimation.widget.keyboard

import android.os.Build
import android.view.WindowInsets
import android.view.WindowInsetsAnimation
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

internal class KeyboardAnimationCompatImpl30 {

    private var isKeyboardAnimationRunning = false
    private var keyboardInsetsBottom = 0

    @RequiresApi(Build.VERSION_CODES.R)
    fun setupKeyboardAnimations(
        editText: EditText,
        onKeyboardInsetsBottomChanged: (keyboardInsetsBottom: Int) -> Unit
    ) {
        ViewCompat.setOnApplyWindowInsetsListener(editText) { _, insets ->

            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            val imeInsetsType = WindowInsetsCompat.Type.ime()
            val imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime())

            val isKeyboardVisible = editText.rootWindowInsets.isVisible(imeInsetsType)
            if (isKeyboardVisible && isKeyboardAnimationRunning.not()) {
                keyboardInsetsBottom = imeInsets.bottom
            } else if (keyboardInsetsBottom == 0) {
                keyboardInsetsBottom = systemBarsInsets.bottom
            }
            onKeyboardInsetsBottomChanged.invoke(keyboardInsetsBottom)

            insets
        }

        val callback = object : WindowInsetsAnimation.Callback(DISPATCH_MODE_STOP) {
            override fun onPrepare(animation: WindowInsetsAnimation) {
                super.onPrepare(animation)
                isKeyboardAnimationRunning = true
            }

            override fun onEnd(animation: WindowInsetsAnimation) {
                super.onEnd(animation)
                isKeyboardAnimationRunning = false
            }

            override fun onProgress(
                insets: WindowInsets,
                runningAnimations: MutableList<WindowInsetsAnimation>
            ): WindowInsets {

                val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                val systemBarsBottom = systemBarsInsets.bottom

                val imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime())
                val imeBottom = imeInsets.bottom

                keyboardInsetsBottom = if (imeBottom <= systemBarsBottom) {
                    systemBarsBottom
                } else {
                    imeBottom
                }
                onKeyboardInsetsBottomChanged.invoke(keyboardInsetsBottom)

                return insets
            }
        }
        editText.setWindowInsetsAnimationCallback(callback)
    }
}