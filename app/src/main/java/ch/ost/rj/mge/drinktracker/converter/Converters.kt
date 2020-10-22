package ch.ost.rj.mge.drinktracker.converter

import androidx.room.TypeConverter
import ch.ost.rj.mge.drinktracker.entity.Gender
import ch.ost.rj.mge.drinktracker.entity.QuantityUnit
import java.util.*

class Converters {
    companion object {
        @TypeConverter
        @JvmStatic
        fun fromGender(value: Gender): Int {
            return value.ordinal
        }

        @TypeConverter
        @JvmStatic
        fun toGender(value: Int): Gender {
            return Gender.values()[value]
        }

        @TypeConverter
        @JvmStatic
        fun fromQuantityUnit(value: QuantityUnit): Int {
            return value.ordinal
        }

        @TypeConverter
        @JvmStatic
        fun toQuantityUnit(value: Int): QuantityUnit {
            return QuantityUnit.values()[value]
        }

        @TypeConverter
        @JvmStatic
        fun toDate(value: Long): Date {
            return Date(value)
        }

        @TypeConverter
        @JvmStatic
        fun fromDate(date: Date): Long {
            return date.time
        }
    }
}