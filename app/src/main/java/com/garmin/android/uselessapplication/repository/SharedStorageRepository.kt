package com.garmin.android.uselessapplication.repository

import android.content.Context
import android.net.Uri
import android.os.Environment
import com.garmin.android.uselessapplication.model.DataFile
import com.garmin.android.uselessapplication.repository.InternalStorageRepository.Companion.SOURCE_FILE_NAME
import java.io.*

class SharedStorageRepository {
    private lateinit var mContext: Context

    fun initializeData() {
        val textToWrite = mContext
                .assets
                .open(SOURCE_FILE_NAME)
                .bufferedReader()
                .use { it.readText() } + TEXT_DISTINCTION

        val dir = File(mContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.toString() + RELATIVE_PATH).apply {
            setReadable(true)
            setWritable(true)
            mkdirs()
        }

        val file = File(dir, FILE_NAME).apply {
            setReadable(true)
            setWritable(true)
            if (!exists()) {
                createNewFile()
            }
        }

        try {
            mContext.contentResolver.openFileDescriptor(Uri.fromFile(file), "w")
                .use {
                    FileOutputStream(it?.fileDescriptor).use { fos ->
                        fos.write(textToWrite.toByteArray())
                    }
                }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    fun getSharedStorageFile(): DataFile {
        val dir = File(mContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.toString() + RELATIVE_PATH)
        val file = File(dir, FILE_NAME)
        val text = mContext.contentResolver.openFileDescriptor(Uri.fromFile(file), "r")
             .use {
                 FileInputStream(it?.fileDescriptor).use { fis ->
                     fis.bufferedReader().useLines {lines ->
                         lines.fold("") { some, text ->
                             "$some\n$text"
                         }
                     }
                 }
             }
        return DataFile(FILE_NAME, text)
    }

    fun setContext(context: Context) =
        let { mContext = context }

    companion object {
        private const val RELATIVE_PATH = "/data_files"
        private const val FILE_NAME = "data_2.txt"
        private const val TEXT_DISTINCTION = " 2000"
    }
}