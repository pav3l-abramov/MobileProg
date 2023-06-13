package com.example.assignment6a.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.io.Serializable


@Entity(tableName = "todo_table")
class TaskModel (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo
    @TypeConverters(FeedConverter::class)
    var feed: MutableList<Float> = mutableListOf(),

    @ColumnInfo
    @TypeConverters(TimeConverter::class)
    var time: MutableList<String> = mutableListOf(),

    @ColumnInfo
    @TypeConverters(TimeConverter::class)
    var timeFormat: List<String> = listOf(),

    @ColumnInfo
    var title: String = "",

    @ColumnInfo
    var status: Boolean = false,

    @ColumnInfo
    var description: String = ""
) : Serializable


class FeedConverter {
    @TypeConverter
    fun fromArrayListOfFloats(feed: MutableList<Float>?): String {
        return feed?.joinToString(separator = ";") { it.toString() } ?: ""
    }

    @TypeConverter
    fun toArrayListOfFloats(string: String?): MutableList<Float> {
        return ArrayList(string?.split(";")?.mapNotNull { it.toFloatOrNull() } ?: emptyList())
    }
}

class TimeConverter {
    @TypeConverter
    fun fromArrayListOfStrings(time: MutableList<String>?): String {
        return time?.joinToString(separator = ";") { it.toString() } ?: ""
    }

    @TypeConverter
    fun toArrayListOfStrings(string: String?): MutableList<String> {
        return ArrayList(string?.split(";")?.mapNotNull { it.toString() } ?: emptyList())
    }


}