package com.incursio.randomusers

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class StringUtil {
    companion object {
        /**
         * Convert String to 'Title Case'
         */
        @ExperimentalStdlibApi
        @SuppressLint("DefaultLocale")
        @JvmStatic
        fun title(string: String?): String {
            if (string == null) return ""
            return string.split(" ")
                .joinToString(" ") { it.capitalize(Locale.US) }
        }
    }
}

val readFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
val writeFormat = SimpleDateFormat("MM/dd/yyyy", Locale.US)

@ExperimentalStdlibApi
fun String.title() = StringUtil.title(this)

fun String?.isoDateToFormatted(): String {
    if (this == null) return ""
    try {
        val date = readFormat.parse(this) ?: return ""
        return writeFormat.format(date)
    } catch (e: Exception) {
        return ""
    }
}
