package com.timmy.codelab.keyboardanimation.screen.chat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updateMargins
import androidx.core.view.updatePadding
import com.timmy.codelab.keyboardanimation.arch.ViewBindingActivity
import com.timmy.codelab.keyboardanimation.databinding.ActivityChatBinding
import com.timmy.codelab.keyboardanimation.widget.doOnWindowInsetsChanged
import com.timmy.codelab.keyboardanimation.widget.keyboard.KeyboardAnimationCompat

class ChatActivity : ViewBindingActivity<ActivityChatBinding>() {

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, ChatActivity::class.java)
        }
    }

    override fun createViewBinding(inflater: LayoutInflater) =
        ActivityChatBinding.inflate(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupWindow()
    }

    private fun setupWindow() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
//        setupWindow1()
        setupWindow2()
    }

    private fun setupWindow1() {
        ViewCompat.setOnApplyWindowInsetsListener(viewBinding.root) { _, insets ->
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            viewBinding.root.updatePadding(
                left = systemBarsInsets.left,
                right = systemBarsInsets.right
            )
            insets
        }

        KeyboardAnimationCompat().registerKeyboardAnimations(
            editText = viewBinding.editorEditText,
            onKeyboardInsetsBottomChanged = {
                viewBinding.bottomSpace.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                    updateMargins(bottom = it)
                }
            }
        )
    }

    private fun setupWindow2() {
        viewBinding.root.doOnWindowInsetsChanged { _, insets ->
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            viewBinding.root.updatePadding(
                left = systemBarsInsets.left,
                right = systemBarsInsets.right
            )
            insets
        }
        viewBinding.bottomSpace.doOnWindowInsetsChanged(true) { _, insets ->
            val systemInsets =
                insets.getInsets(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.ime())
            viewBinding.bottomSpace.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                updateMargins(bottom = systemInsets.bottom)
            }
            insets
        }
    }
}