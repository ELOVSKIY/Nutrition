package com.helicoptera.nutrition.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainViewModelFactory(private val appContext: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val mainViewModel = MainViewModel(appContext)
        return if (modelClass.isInstance(mainViewModel)) {
            modelClass.cast(mainViewModel) ?: throw IllegalStateException()
        } else {
            throw IllegalStateException()
        }
    }
}