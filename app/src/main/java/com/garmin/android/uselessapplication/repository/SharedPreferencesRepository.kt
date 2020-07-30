package com.garmin.android.uselessapplication.repository

import android.content.Context
import com.garmin.android.uselessapplication.model.DataFile
import com.garmin.android.uselessapplication.repository.InternalStorageRepository.Companion.SOURCE_FILE_NAME

class SharedPreferencesRepository {
    private lateinit var mContext: Context

    fun initializeData() {
        val textToWrite =
            mContext
                .assets
                .open(SOURCE_FILE_NAME)
                .bufferedReader()
                .use { it.readText() } + TEXT_DISTINCTION
        val sharedPref = mContext.getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString(FILE_KEY, textToWrite)
            apply()
        }
    }

    fun getSharedPrefFile(): DataFile {
        val text = mContext.getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE)
            .getString(FILE_KEY, DEFAULT_FILE_TEXT) ?: DEFAULT_FILE_TEXT
        return DataFile(FILE_KEY, text)
    }

    fun setContext(context: Context) =
        let { mContext = context }

    companion object {
        private const val SHARED_PREF_KEY = "DATA_FILES"
        private const val FILE_KEY = "DATA_KEY"
        private const val DEFAULT_FILE_TEXT = "FILE COULD NOT BE FETCHED"
        private const val TEXT_DISTINCTION = " 1000"
    }
}