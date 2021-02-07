package com.testing.miniproject.domain

import com.testing.miniproject.data.localdb.DataDAO
import com.testing.miniproject.data.localdb.MyData
import io.realm.Realm

class DataDAOImpl(var realm : Realm) : DataDAO {
    override fun add(data: MyData): Boolean {
        return try {
            realm.executeTransaction{
                it.insertOrUpdate(data)
            }
            true
        } catch (e: Exception) {
            println(e)
            false
        }
    }

    override fun findAll(): List<MyData> {
        return realm.copyFromRealm(realm.where(MyData::class.java).findAll())
    }

    override fun findById(id: Long): MyData? {
        return realm.where(MyData::class.java).equalTo("id", id).findFirst()
    }
}