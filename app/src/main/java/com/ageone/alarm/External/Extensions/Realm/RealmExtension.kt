package com.ageone.alarm.External.Extensions.Realm

import io.realm.Realm
import io.realm.RealmObject
import timber.log.Timber

fun add(some: RealmObject): Boolean {
    return try {
        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        realm.copyToRealmOrUpdate(some)
        realm.commitTransaction()
        true
    } catch (e: Exception) {
        Timber.e("$e")
        false
    }
}