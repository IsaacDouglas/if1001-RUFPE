package br.ufpe.cin.walletshare.db

import android.arch.persistence.room.TypeConverter
import java.util.*

class Converter {

    @TypeConverter
    fun toDate(timestamp: Long): Date {
        return Date(timestamp)
    }

    @TypeConverter
    fun toTimestamp(date: Date): Long {
        return date.time
    }
}