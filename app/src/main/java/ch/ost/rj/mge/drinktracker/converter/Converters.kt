package ch.ost.rj.mge.drinktracker.converter

import androidx.room.TypeConverter
import ch.ost.rj.mge.drinktracker.entity.Gender

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
    }
}