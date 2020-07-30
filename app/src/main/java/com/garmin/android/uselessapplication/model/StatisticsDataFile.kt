package com.garmin.android.uselessapplication.model

import java.lang.Integer.parseInt

class StatisticsDataFile(var name: String, var text: String) {
    var numbersAverage: Double = getAverage()
    var numbersSum: Int = getSum()

    private fun getAverage() =
        text.split(TEXT_SPLITTER)
            .mapNotNull { it.toIntOrNull() }
            .average()

    private fun getSum() =
        text.split(TEXT_SPLITTER)
            .mapNotNull { it.toIntOrNull() }
            .sum()

    companion object {
        private const val TEXT_SPLITTER = " "

        fun fromDataFile(dataFile: DataFile) =
            StatisticsDataFile(dataFile.name, dataFile.text)
    }
}