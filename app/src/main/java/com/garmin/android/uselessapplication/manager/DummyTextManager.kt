package com.garmin.android.uselessapplication.manager

import android.content.Context

class DummyTextManager {
    companion object {
        private const val SOURCE_FILE_NAME = "data_source.txt"

        fun getText(context: Context) =
            context
                .assets
                .open(SOURCE_FILE_NAME)
                .bufferedReader()
                .use { it.readText() }
    }
}