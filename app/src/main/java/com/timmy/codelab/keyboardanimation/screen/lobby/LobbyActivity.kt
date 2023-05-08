package com.timmy.codelab.keyboardanimation.screen.lobby

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.timmy.codelab.keyboardanimation.arch.ViewBindingActivity
import com.timmy.codelab.keyboardanimation.databinding.ActivityLobbyBinding
import com.timmy.codelab.keyboardanimation.screen.chat.ChatActivity
import com.timmy.codelab.keyboardanimation.widget.doOnWindowInsetsChanged

class LobbyActivity : ViewBindingActivity<ActivityLobbyBinding>() {

    override fun createViewBinding(inflater: LayoutInflater) =
        ActivityLobbyBinding.inflate(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupWindow()
        setupViews()
    }

    private fun setupWindow() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        viewBinding.root.doOnWindowInsetsChanged { _, insets ->
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            viewBinding.root.updatePadding(
                left = systemBarsInsets.left,
                right = systemBarsInsets.right
            )
            insets
        }
    }

    private fun setupViews() {
        viewBinding.chat.setOnClickListener {
            startActivity(ChatActivity.createIntent(this))
        }
    }
}