package com.testing.miniproject.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.testing.miniproject.base.BaseViewModel
import com.testing.miniproject.data.DataRepository
import com.testing.miniproject.data.localdb.MyData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class DetailViewModel(var repository : DataRepository) : BaseViewModel() {
    private val _data: MutableLiveData<MyData> = MutableLiveData()

    val currentData: LiveData<MyData> get() = _data

    fun setPrefilledData(data : MyData) {
        _data.value = data
    }

    fun findDataById(id : Long) {
        viewModeScope.launch {
            _loading.value = true
            _data.value = repository.getDataById(id)
            _loading.value= false
        }
    }

    fun updatedData(data : MyData) {
        viewModeScope.launch {
            _loading.value = true
            if (repository.insertData(data)) {
                _data.value = data
            } else {
                _error.value = "Gagal menginput data"
            }
            _loading.value= false

        }
    }
}