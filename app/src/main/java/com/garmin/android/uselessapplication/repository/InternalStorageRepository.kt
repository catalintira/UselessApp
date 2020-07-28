package com.garmin.android.uselessapplication.repository

import android.content.Context
import android.util.Log
import com.garmin.android.uselessapplication.model.DataFile
import java.io.File

class InternalStorageRepository {

    private lateinit var mContext: Context

    fun initializeData() {
        if( !File(FILE_NAME).exists() ) {
            Log.i(LOG_TAG, LOG_NOT_EXISTENT_MESSAGE)
            val textToWrite =
                mContext
                    .assets
                    .open(SOURCE_FILE_NAME)
                    .bufferedReader()
                    .use { it.readText() }
            mContext.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).use {
                it.write(textToWrite.toByteArray())
            }
        }
    }

    fun genInternalFile(): DataFile {
        val content = mContext.openFileInput(FILE_NAME).bufferedReader().useLines { lines ->
            lines.fold("") { some, text ->
                "$some\n$text"
            }
        }
        return DataFile(FILE_NAME, content)
    }

    fun setContext(context: Context) = let {this.mContext = context }

    companion object {
        const val SOURCE_FILE_NAME = "data_source.txt"
        private const val FILE_NAME = "data.txt"
        private const val LOG_TAG = "InternalStorageRepository"
        private const val LOG_NOT_EXISTENT_MESSAGE = "FILE NOT EXISTENT"
    }
}