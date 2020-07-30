package com.garmin.android.uselessapplication.model

data class DataFile(
    var name: String,
    var text: String
) {
    fun mapToCount(stringToCount: String): DataFile {
        val text = "$COUNT_PREFIX${
            this.text
                .windowed(stringToCount.length)
                .filter{ it.equals(stringToCount, ignoreCase = true) }
                .count()
            }"
        return DataFile(this.name, text)
    }

    companion object {
        private const val COUNT_PREFIX = "Count: "
    }
}