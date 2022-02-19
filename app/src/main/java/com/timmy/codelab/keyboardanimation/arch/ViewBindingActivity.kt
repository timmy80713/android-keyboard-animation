package com.timmy.codelab.keyboardanimation.arch

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class ViewBindingActivity<T : ViewBinding> : AppCompatActivity() {

    private var internalViewBinding: T? = null

    protected val viewBinding: T
        get() = internalViewBinding
            ?: throw ViewBindingUnavailableException("Cannot use ViewBinding before onCreate().")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        internalViewBinding = createViewBinding(layoutInflater).apply { setContentView(root) }
    }

    abstract fun createViewBinding(inflater: LayoutInflater): T

}