package com.example.coba1submission.ui.liked

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LikedViewModelFactory(private var id: Int): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LikedViewModel::class.java)){
            return LikedViewModel(id) as T
        }
        throw IllegalArgumentException("Viewmodel class not found!!!")
    }
}