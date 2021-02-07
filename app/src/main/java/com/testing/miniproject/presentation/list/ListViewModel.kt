package com.testing.miniproject.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.testing.miniproject.base.BaseViewModel
import com.testing.miniproject.data.DataRepository
import com.testing.miniproject.data.localdb.MyData
import kotlinx.coroutines.launch

class ListViewModel(val repository: DataRepository) : BaseViewModel() {

    private val _list : MutableLiveData<List<MyData>> = MutableLiveData()

    val currentData: LiveData<List<MyData>> get() = _list

    fun fetchData() {
        viewModeScope.launch {
            _loading.value = true
            _list.value = repository.getAllData()
            _loading.value= false

        }
    }

}