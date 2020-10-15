package ch.ost.rj.mge.drinktracker.dao

import android.content.Context
import android.widget.EditText
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ch.ost.rj.mge.drinktracker.converter.Converters
import ch.ost.rj.mge.drinktracker.entity.Person
import kotlinx.coroutines.CoroutineScope

@Database(entities = arrayOf(Person::class), version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class DrinkTrackerDatabase : RoomDatabase() {

    abstract fun personDao(): PersonDao

    companion object {
        @Volatile
        private var INSTANCE: DrinkTrackerDatabase? = null

        fun getDatabase(context: Context): DrinkTrackerDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DrinkTrackerDatabase::class.java,
                    "drinkTrackerDatabase"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}