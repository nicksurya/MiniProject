package com.testing.miniproject.config.migration

import io.realm.DynamicRealm
import io.realm.RealmMigration


class MigrationVersion : RealmMigration {
    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        val schema = realm.schema

        //handle versions
        if (oldVersion == 0L) {
            val dataSchema = schema["MyData"]
            dataSchema?.addField("activityLocation", String::class.java)
        }
    }
}