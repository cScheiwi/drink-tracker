package ch.ost.rj.mge.drinktracker.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity(tableName = "person")
data class Person(
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "weight") var weight: Int,
    @ColumnInfo(name = "gender") var gender: Gender
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}