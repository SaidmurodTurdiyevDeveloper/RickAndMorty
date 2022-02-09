package com.example.demoforajob.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.demoforajob.domen.UseCaseMain
import com.example.demoforajob.viewModel.impl.ViewModelMainImplament

class MyViewModelFactory(private var usecase: UseCaseMain) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewModelMainImplament::class.java)) {
            return ViewModelMainImplament(usecase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}