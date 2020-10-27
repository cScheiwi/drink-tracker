package ch.ost.rj.mge.drinktracker.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import ch.ost.rj.mge.drinktracker.converter.Converters
import ch.ost.rj.mge.drinktracker.dao.DrinkDao
import ch.ost.rj.mge.drinktracker.dao.DrinkTemplateDao
import ch.ost.rj.mge.drinktracker.dao.PersonDao
import ch.ost.rj.mge.drinktracker.entity.Drink
import ch.ost.rj.mge.drinktracker.entity.DrinkTemplate
import ch.ost.rj.mge.drinktracker.entity.Person
import ch.ost.rj.mge.drinktracker.entity.QuantityUnit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

@Database(
    entities = [Person::class, DrinkTemplate::class, Drink::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class DrinkTrackerDatabase : RoomDatabase() {

    abstract fun personDao(): PersonDao
    abstract fun drinkTemplateDao(): DrinkTemplateDao
    abstract fun drinkDao(): DrinkDao

    private class DrinkTrackerDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            INSTANCE?.let { database ->
                scope.launch {

                    Executors.newSingleThreadExecutor().execute()
                    {
                        val drinkTemplateDao = database.drinkTemplateDao()

                        drinkTemplateDao.insert(
                            DrinkTemplate(
                                "Bier",
                                3.3,
                                QuantityUnit.DECILITER,
                                5.0
                            )
                        )
                        drinkTemplateDao.insert(
                            DrinkTemplate(
                                "Wein",
                                1.0,
                                QuantityUnit.DECILITER,
                                12.0
                            )
                        )
                        drinkTemplateDao.insert(
                            DrinkTemplate(
                                "Martini",
                                4.0,
                                QuantityUnit.CENTILITER,
                                15.0
                            )
                        )
                        drinkTemplateDao.insert(
                            DrinkTemplate(
                                "Campari",
                                4.0,
                                QuantityUnit.CENTILITER,
                                21.0
                            )
                        )
                        drinkTemplateDao.insert(
                            DrinkTemplate(
                                "Cognac",
                                2.5,
                                QuantityUnit.CENTILITER,
                                40.0
                            )
                        )
                        drinkTemplateDao.insert(
                            DrinkTemplate(
                                "Whisky",
                                4.0,
                                QuantityUnit.CENTILITER,
                                40.0
                            )
                        )
                        drinkTemplateDao.insert(
                            DrinkTemplate(
                                "Kirsch",
                                2.5,
                                QuantityUnit.CENTILITER,
                                40.0
                            )
                        )
                        drinkTemplateDao.insert(
                            DrinkTemplate(
                                "Cocktails",
                                4.0,
                                QuantityUnit.CENTILITER,
                                25.0
                            )
                        )
                        drinkTemplateDao.insert(
                            DrinkTemplate(
                                "Wasser",
                                1.0,
                                QuantityUnit.LITER,
                                0.0
                            )
                        )
                    }
                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: DrinkTrackerDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): DrinkTrackerDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DrinkTrackerDatabase::class.java,
                    "drinkTrackerDatabase"
                ).fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .addCallback(DrinkTrackerDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}