package com.garmin.android.uselessapplication.repository

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.core.net.toUri
import com.garmin.android.uselessapplication.repository.InternalStorageRepository.Companion.SOURCE_FILE_NAME
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class SharedStorageRepository {

    private lateinit var mContext: Context

    fun initializeData() {
        val textToWrite = mContext
                .assets
                .open(SOURCE_FILE_NAME)
                .bufferedReader()
                .use { it.readText() } + TEXT_DISTINCTION

        val dir = File(ROOT.absolutePath + RELATIVE_PATH).apply {
            mkdirs()
        }
        val file = File(dir, FILE_NAME).apply {
            if (exists()) {
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


    fun setContext(context: Context) = let { mContext = context }

    companion object {
        private val ROOT = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        private const val RELATIVE_PATH = "/data_files"
        private const val FILE_NAME = "data_2.txt"
        private const val TEXT_DISTINCTION = "2000"
    }
}