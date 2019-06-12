package br.ufpe.cin.walletshare.db

import android.arch.persistence.room.TypeConverter
import br.ufpe.cin.walletshare.entity.Friend
import br.ufpe.cin.walletshare.entity.Item
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*
import kotlin.collections.ArrayList

class Converter {

    @TypeConverter
    fun toDate(timestamp: Long): Date {
        return Date(timestamp)
    }

    @TypeConverter
    fun toTimestamp(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun fromStringFriend(value: String): MutableList<Friend> {
        val listType = object : TypeToken<MutableList<Friend>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayListFriend(list: MutableList<Friend>): String {
        val gson = Gson()
        val json= gson.toJson(list)
        return json
    }

    @TypeConverter
    fun fromStringItem(value: String): MutableList<Item> {
        val listType = object : TypeToken<MutableList<Item>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayListItem(list: MutableList<Item>): String {
        val gson = Gson()
        val json= gson.toJson(list)
        return json
    }
}