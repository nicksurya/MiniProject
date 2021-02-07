package com.testing.miniproject.data.localdb

interface DataDAO {

    fun add(data: MyData): Boolean

    fun findAll(): List<MyData>

    fun findById(id : Long) : MyData?
}