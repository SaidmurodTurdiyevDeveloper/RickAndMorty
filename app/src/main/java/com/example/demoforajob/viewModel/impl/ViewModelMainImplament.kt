package com.example.demoforajob.viewModel.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demoforajob.data.model.Event
import com.example.demoforajob.data.model.ItemData
import com.example.demoforajob.domen.UseCaseMain
import com.example.demoforajob.viewModel.ViewModelMain
import com.example.demoforajob.zzz_others.MyResponce
import com.example.demoforajob.zzz_utills.emptyBlock
import com.example.demoforajob.zzz_utills.sendOneParametreBlock
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ViewModelMainImplament constructor(
    private var useCaseMain: UseCaseMain
) : ViewModel(), ViewModelMain {
    private val _loadItemsLiveData = MutableLiveData<List<ItemData>>(emptyList())
    override val loadItemsLiveData: LiveData<List<ItemData>> = _loadItemsLiveData

    private val _backLiveData = MutableLiveData<Unit>()
    override val backLiveData: LiveData<Unit> get() = _backLiveData

    private val _showToastLiveData = MutableLiveData<String>()
    override val showToastLiveData: LiveData<String> get() = _showToastLiveData

    private val _messageLiveData = MutableLiveData<String>()
    override val messageLiveData: LiveData<String> get() = _messageLiveData

    private val _errorLiveData = MutableLiveData<String>()
    override val errorLiveData: LiveData<String> get() = _errorLiveData

    private val _snackBarLiveData = MutableLiveData<Event<String>>()
    override val snackBarLiveData: LiveData<Event<String>> = _snackBarLiveData

    private val _loadingLiveData = MutableLiveData<Boolean>()
    override val loadingLiveData: LiveData<Boolean> = _loadingLiveData

    private val _openGoogle = MutableLiveData<Event<String>>()
    override val openGoogle: LiveData<Event<String>> = _openGoogle

    private suspend fun <T> loadFlow(flow: Flow<MyResponce<T>>, succeslistener: sendOneParametreBlock<T>, errorSnackbarListener: emptyBlock) {
        flow.collectLatest {
            when (it) {
                is MyResponce.Error -> {
                    _loadingLiveData.postValue(false)
                    _snackBarLiveData.postValue(Event(it.error) {
                        errorSnackbarListener.invoke()
                    })
                }
                is MyResponce.Loading -> {
                    _loadingLiveData.postValue(it.cond)
                }
                is MyResponce.Message -> {
                    _loadingLiveData.postValue(false)
                    _messageLiveData.postValue(it.message)
                }
                is MyResponce.Success -> {
                    _loadingLiveData.postValue(false)
                    succeslistener.invoke(it.data)
                }
            }
        }
    }

    override fun loadItems() {
        viewModelScope.launch {
            loadFlow(useCaseMain.loadItems(), {
                _loadItemsLiveData.postValue(it)
            }, {
                loadItems()
            })
        }
    }


    override fun back() {
        viewModelScope.launch {
            _backLiveData.postValue(Unit)
        }
    }

    override fun itemClick(data: ItemData) {
        if (data.dataUri.isNotEmpty())
            _openGoogle.postValue(Event(data.dataUri))
    }
}