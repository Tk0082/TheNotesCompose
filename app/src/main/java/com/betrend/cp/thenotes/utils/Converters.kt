package com.betrend.cp.thenotes.utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class Converters {

    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    @TypeConverter
    fun fromLocalDateTime(value: LocalDateTime): String {
        return value.format(formatter)
    }

    @TypeConverter
    fun toLocalDateTime(value: String): LocalDateTime {
        return LocalDateTime.parse(value, formatter)
    }
}
