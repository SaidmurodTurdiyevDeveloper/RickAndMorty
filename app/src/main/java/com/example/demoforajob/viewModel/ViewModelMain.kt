package com.example.demoforajob.viewModel

import androidx.lifecycle.LiveData
import com.example.demoforajob.data.model.Event
import com.example.demoforajob.data.model.ItemData
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface ViewModelMain {
    val loadItemsLiveData: LiveData<List<ItemData>>
    val backLiveData: LiveData<Unit>
    val showToastLiveData: LiveData<String>
    val messageLiveData: LiveData<String>
    val errorLiveData: LiveData<String>
    val openGoogle:LiveData<Event<String>>
    val snackBarLiveData: LiveData<Event<String>>
    val loadingLiveData: LiveData<Boolean>

    fun loadItems()
    fun back()
    fun itemClick(data: ItemData)
}