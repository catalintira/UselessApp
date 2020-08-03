package com.garmin.android.uselessapplication.repository

import android.content.Context
import android.util.Log
import com.garmin.android.uselessapplication.manager.DummyTextManager
import com.garmin.android.uselessapplication.manager.TextConcatenationManager
import com.garmin.android.uselessapplication.model.DataFile
import java.io.File

class InternalStorageRepository {
    private lateinit var mContext: Context

    fun initializeData() {
        if (!File(FILE_NAME).exists()) {
            val textToWrite = DummyTextManager.getText(mContext)
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

    fun insertText(textToInsert: String) {
        val existentText = mContext.openFileInput(FILE_NAME).bufferedReader().useLines { lines ->
            lines.fold("") { some, text ->
                "$some\n$text"
            }
        }

        mContext.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).use {
            it.write(
                TextConcatenationManager.concatenateStrings(existentText, textToInsert)
                    .toByteArray()
            )
        }
    }

    fun setContext(context: Context) =
        let { this.mContext = context }

    companion object {
        private const val FILE_NAME = "data.txt"
    }
}