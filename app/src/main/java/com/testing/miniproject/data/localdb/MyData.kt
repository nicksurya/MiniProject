package com.testing.miniproject.data.localdb

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class MyData (
    @PrimaryKey var id : Long? = 0,
    var name : String? = null,
    var dob : String? = null,
    var product : String?= null,
    var productCode : String? = null,
    var activityType : String? = null,
    var activityLocation : String? = null,
    var activityDate : String? = null,
    var activityTimeStart : String? = null,
    var activityTimeEnd : String? = null,
    var planStart : String? = null,
    var reason : String? = null,
    var price : Double? = 0.0,
    var period : Int? = 0,
    var notes : String? = null,
    var lastUpdate : String? = null
) : RealmObject()