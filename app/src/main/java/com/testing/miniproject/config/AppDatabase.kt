package com.testing.miniproject.config

import io.realm.Realm

internal fun provideRealm() = Realm.getDefaultInstance()