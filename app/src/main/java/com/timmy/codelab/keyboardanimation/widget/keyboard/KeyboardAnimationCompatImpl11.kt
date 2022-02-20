package com.timmy.codelab.keyboardanimation.widget.keyboard

import android.os.Build
import android.view.WindowInsets
import android.view.WindowInsetsAnimation
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

internal class KeyboardAnimationCompatImpl11(
    private val editText: EditText,
    private val keyboardInsetsBottomUpdated: (keyboardInsetsBottom: Int) -> Unit
) {

    private var isKeyboardAnimationEnd = true
    private var keyboardInsetsBottom = 0

    @RequiresApi(Build.VERSION_CODES.R)
    fun setupKeyboardAnimations() {
        ViewCompat.setOnApplyWindowInsetsListener(editText) { _, windowInsets ->

            val systemInsetsType = WindowInsetsCompat.Type.systemBars()
            val systemInsets = windowInsets.getInsets(systemInsetsType)

            val imeInsetsType = WindowInsetsCompat.Type.ime()
            val imeInsets = windowInsets.getInsets(imeInsetsType)

            val isKeyboardVisible = editText.rootWindowInsets.isVisible(imeInsetsType)
            if (isKeyboardVisible && isKeyboardAnimationEnd) {
                keyboardInsetsBottom = imeInsets.bottom
            } else if (keyboardInsetsBottom == 0) {
                keyboardInsetsBottom = systemInsets.bottom
            }
            keyboardInsetsBottomUpdated.invoke(keyboardInsetsBottom)
            windowInsets
        }

        val callback = object : WindowInsetsAnimation.Callback(DISPATCH_MODE_STOP) {
            override fun onPrepare(animation: WindowInsetsAnimation) {
                super.onPrepare(animation)
                isKeyboardAnimationEnd = false
            }

            override fun onEnd(animation: WindowInsetsAnimation) {
                super.onEnd(animation)
                isKeyboardAnimationEnd = true
            }

            override fun onProgress(
                windowInsets: WindowInsets,
                animations: MutableList<WindowInsetsAnimation>
            ): WindowInsets {

                val systemInsetsType = WindowInsetsCompat.Type.systemBars()
                val systemInsets = windowInsets.getInsets(systemInsetsType)

                val imeInsetsType = WindowInsetsCompat.Type.ime()
                val imeInsets = windowInsets.getInsets(imeInsetsType)

                val imeBottom = imeInsets.bottom
                val systemBarBottom = systemInsets.bottom

                keyboardInsetsBottom = if (imeBottom <= systemBarBottom) {
                    systemBarBottom
                } else {
                    imeBottom
                }
                keyboardInsetsBottomUpdated.invoke(keyboardInsetsBottom)

                return windowInsets
            }
        }
        editText.setWindowInsetsAnimationCallback(callback)
    }
}