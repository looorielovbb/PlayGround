package xyz.looorielovbb.playground.data.local

import androidx.room.TypeConverter
import xyz.looorielovbb.playground.ext.MoshiEx
import xyz.looorielovbb.playground.pojo.Tag

class Converters {

    @TypeConverter
    fun tagListToString(values: List<Tag>): String {
        return MoshiEx.toJson(values)
    }

    @TypeConverter
    fun stringToTagList(value: String): List<Tag>? {
        return MoshiEx.fromJson<List<Tag>>(value)
    }
}