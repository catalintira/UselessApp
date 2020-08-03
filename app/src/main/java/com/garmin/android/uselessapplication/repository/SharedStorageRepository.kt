package com.garmin.android.uselessapplication.repository

import android.content.Context
import android.net.Uri
import android.os.Environment
import com.garmin.android.uselessapplication.manager.DummyTextManager
import com.garmin.android.uselessapplication.manager.TextConcatenationManager
import com.garmin.android.uselessapplication.model.DataFile
import java.io.*

class SharedStorageRepository {
    private lateinit var mContext: Context

    fun initializeData() {
        val textToWrite = DummyTextManager.getText(mContext) + TEXT_DISTINCTION

        val dir = File(mContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.toString() + RELATIVE_PATH).apply {
            setReadable(true)
            setWritable(true)
            mkdirs()
        }

        val file = File(dir, FILE_NAME).apply {
            setReadable(true)
            setWritable(true)
            createNewFile()
        }

        try {
            writeToFile(file, textToWrite)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    fun getSharedStorageFile(): DataFile {
        val text = getTextFromFile((getFile()))
        return DataFile(FILE_NAME, text)
    }

    fun insertText(textToInsert: String) {
        val existentText = getTextFromFile((getFile()))

        try {
            writeToFile(getFile(),
                TextConcatenationManager.concatenateStrings(existentText, textToInsert))
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun setContext(context: Context) =
        let { mContext = context }

    private fun getFile(): File {
        val dir = File(mContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.toString() + RELATIVE_PATH)
        return File(dir, FILE_NAME)
    }

    private fun getTextFromFile(file: File) =
        mContext.contentResolver.openFileDescriptor(Uri.fromFile(file), "r")
            .use {
                FileInputStream(it?.fileDescriptor).use { fis ->
                    fis.bufferedReader().useLines {lines ->
                        lines.fold("") { some, text ->
                            "$some\n$text"
                        }
                    }
                }
            }

    private fun writeToFile(
        file: File,
        text: String
    ) =
        mContext.contentResolver.openFileDescriptor(Uri.fromFile(file), "w")
            .use {
                FileOutputStream(it?.fileDescriptor).use { fos ->
                    fos.write(text.toByteArray())
                }
            }

    companion object {
        private const val RELATIVE_PATH = "/data_files"
        private const val FILE_NAME = "data_2.txt"
        private const val TEXT_DISTINCTION = " 2000"
    }
}