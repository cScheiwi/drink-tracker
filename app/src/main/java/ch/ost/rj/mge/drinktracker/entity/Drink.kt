package ch.ost.rj.mge.drinktracker.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "drink")
class Drink(
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "quantity") var quantity: Double,
    @ColumnInfo(name = "quantityUnit") var quantityUnit: QuantityUnit,
    @ColumnInfo(name = "percentByVolume") var percentByVolume: Double,
    @ColumnInfo(name = "openAt") var openAt: Date
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}