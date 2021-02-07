package com.testing.miniproject.data

import com.testing.miniproject.data.localdb.MyData

interface DataRepository {

    fun getAllData() : List<MyData>

    fun insertData(data : MyData) : Boolean

    fun getDataById(id : Long) : MyData?
}