package ru.gb.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = arrayOf("word"), unique = true)])
class HistoryEntity(
    @field:PrimaryKey
    @field:ColumnInfo(name = "word")
    var word: String,
    @field:ColumnInfo(name = "description")
    var description: String,
    @field:ColumnInfo(name = "image_url")
    var imageUrl: String
)

//@Entity(indices = [Index(value = arrayOf("word"), unique = true)])
//class DataModelEntity(
//    @field:PrimaryKey
//    @field:ColumnInfo(name = "word")
//    var word: String,
//    @field:ColumnInfo(name = "description")
//    var description: String?
//)

//@Entity(indices = [Index(value = arrayOf("word"), unique = true)])
//data class DataModelEntity(
//    @field:PrimaryKey
//    @field:ColumnInfo(name = "word")
//    val text: String = "",
//    val meanings: List<Meaning> = listOf()
//)