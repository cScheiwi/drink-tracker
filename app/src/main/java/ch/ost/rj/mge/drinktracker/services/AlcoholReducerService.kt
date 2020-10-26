package ch.ost.rj.mge.drinktracker.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import ch.ost.rj.mge.drinktracker.R
import ch.ost.rj.mge.drinktracker.activities.HistoryActivity
import ch.ost.rj.mge.drinktracker.database.DrinkTrackerDatabase
import ch.ost.rj.mge.drinktracker.entity.Person
import kotlinx.coroutines.GlobalScope

class AlcoholReducerService : IntentService("AlcoholReducer") {

    private val CID: String = "DrinkTracker_Channel"
    private val CNAME: String = "DrinkTracker Notifications"
    private var notificationId: Int = 1
    private var manager: NotificationManagerCompat? = null
    private var notificationChannel: NotificationChannel? = null

    companion object {
        const val ALCOHOL_REDUCE_PER_HOUR: Double = 0.1
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onHandleIntent(intent: Intent?) {

        Log.i("AlcoholReducer", "Alcohol reducer running")

        val db = DrinkTrackerDatabase.getDatabase(applicationContext, GlobalScope)
        val person: Person = db.personDao().findPerson()!!

        var alcoholLevel: Double = person.perMil
        val alcoholLevelOld = alcoholLevel

        if (alcoholLevel > 0.0) {
            alcoholLevel -= ALCOHOL_REDUCE_PER_HOUR / HistoryActivity.AMOUNT_OF_REDUCES_PER_HOUR

            if (alcoholLevelOld > 0.5 && alcoholLevel < 0.5) {
                showNotification("DrinkTracker", "Sie können nun wieder fahren.")
            } else if (alcoholLevel <= 0.0) {
                alcoholLevel = 0.0
                showNotification("DrinkTracker", "Sie sind nun wieder nüchtern.")
            } else {
                // showNotification("DrinkTracker", """Sie haben aktuell ${alcoholLevel} Promille""")
                Log.i("AlcoholReducer", """Sie haben aktuell ${alcoholLevel} Promille""")
            }

            person.perMil = alcoholLevel
            db.personDao().update(person)
        } else {
            // TODO: 26.10.2020 remove if reactivation in HistoryActivity.scheduleAlcoholReducer() can not be correctly implemented
            cancelAlcoholReducer()
        }
    }

    private fun cancelAlcoholReducer() {
        val intent = Intent(applicationContext, AlcoholReducerAlarmReceiver::class.java)
        val pIntent = PendingIntent.getBroadcast(
            this, AlcoholReducerAlarmReceiver.REQUEST_CODE,
            intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarm =
            this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarm.cancel(pIntent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showNotification(title: String, text: String) {
        if (manager == null) {
            manager = NotificationManagerCompat.from(applicationContext)
        }
        if (notificationChannel == null) {
            notificationChannel =
                NotificationChannel(CID, CNAME, NotificationManager.IMPORTANCE_HIGH)
            manager!!.createNotificationChannel(notificationChannel!!)
        }

        val intent = Intent(this, HistoryActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notification: Notification? = NotificationCompat.Builder(applicationContext, CID)
            // TODO: 26.10.2020 evt. replace Icon
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(text)
            .setContentIntent(pendingIntent)
            .build()

        manager!!.notify(notificationId++, notification!!)
    }
}