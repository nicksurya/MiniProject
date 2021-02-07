package com.testing.miniproject.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob


open abstract class BaseViewModel : ViewModel() {
    protected val viewModelJob = SupervisorJob()

    protected val viewModeScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    protected val _error: MutableLiveData<String> = MutableLiveData()
    protected val _loading: MutableLiveData<Boolean> = MutableLiveData()

    val error: LiveData<String> get() = _error
    val loading: LiveData<Boolean> get() = _loading
}