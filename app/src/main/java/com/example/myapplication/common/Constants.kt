package com.example.myapplication.common

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

class Constants {
    companion object {
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore("save")
        const val userToken = "userToken"
        const val userId = "userId"
        const val REQUEST_PICK_PDF = "pick_pdf"
        const val REQUEST_PICK_IMAGE = "pick_image"
    }

}