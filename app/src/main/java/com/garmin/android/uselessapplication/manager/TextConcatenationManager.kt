package com.garmin.android.uselessapplication.manager

class TextConcatenationManager {
    companion object {
        fun concatenateStrings(
            arg1: String,
            arg2: String,
            textSeparator: String = "\n"
        ) =
            arg1 + textSeparator + arg2
    }
}