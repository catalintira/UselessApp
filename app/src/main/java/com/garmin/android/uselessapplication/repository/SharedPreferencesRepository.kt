package com.garmin.android.uselessapplication.repository

import android.content.Context
import com.garmin.android.uselessapplication.manager.DummyTextManager
import com.garmin.android.uselessapplication.manager.TextConcatenationManager
import com.garmin.android.uselessapplication.model.DataFile

class SharedPreferencesRepository {
    private lateinit var mContext: Context

    fun initializeData() =
        setText(DummyTextManager.getText(mContext) + TEXT_DISTINCTION)


    fun getSharedPrefFile(): DataFile =
        DataFile(FILE_KEY, getText())

    fun insertText(textToInsert: String) {
        val existentText = getText()
        setText(TextConcatenationManager.concatenateStrings(existentText, textToInsert))
    }

    fun setContext(context: Context) =
        let { mContext = context }

    private fun setText(textToWrite: String) {
        val sharedPref = mContext.getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString(FILE_KEY, textToWrite)
            apply()
        }
    }

    private fun getText() =
        mContext.getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE)
            .getString(FILE_KEY, DEFAULT_FILE_TEXT) ?: DEFAULT_FILE_TEXT

    companion object {
        private const val SHARED_PREF_KEY = "DATA_FILES"
        private const val FILE_KEY = "DATA_KEY"
        private const val DEFAULT_FILE_TEXT = "FILE COULD NOT BE FETCHED"
        private const val TEXT_DISTINCTION = " 1000"
    }
}