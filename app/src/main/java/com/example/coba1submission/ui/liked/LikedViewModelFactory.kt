package com.example.coba1submission.ui.liked

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LikedViewModelFactory(private val ids: MutableList<Int>) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LikedViewModel::class.java)) {
            return LikedViewModel(ids) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}