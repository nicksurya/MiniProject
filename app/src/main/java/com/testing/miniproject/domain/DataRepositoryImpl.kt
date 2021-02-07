package com.testing.miniproject.domain

import com.testing.miniproject.data.DataRepository
import com.testing.miniproject.data.localdb.DataDAO
import com.testing.miniproject.data.localdb.MyData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DataRepositoryImpl(private val dao : DataDAO) : DataRepository {

    override fun getAllData(): List<MyData> = dao.findAll()

    override fun insertData(data: MyData) : Boolean {
        return dao.add(data)
    }

    override fun getDataById(id: Long): MyData? {
        return dao.findById(id)
    }
}