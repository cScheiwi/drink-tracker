package ch.ost.rj.mge.drinktracker.services

import android.app.*
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import ch.ost.rj.mge.drinktracker.R
import ch.ost.rj.mge.drinktracker.activities.HistoryActivity
import ch.ost.rj.mge.drinktracker.database.DrinkTrackerDatabase
import ch.ost.rj.mge.drinktracker.entity.Person
import kotlinx.coroutines.GlobalScope

class AlcoholReducerService : IntentService("AlcoholReducer") {

    private val cId: String = "DrinkTracker_Channel"
    private val cName: String = "DrinkTracker Notifications"
    private var notificationId: Int = 1
    private var manager: NotificationManagerCompat? = null
    private var notificationChannel: NotificationChannel? = null

    companion object {
        const val ALCOHOL_REDUCE_PER_HOUR: Double = 0.1
    }

    override fun onHandleIntent(intent: Intent?) {

        val db = DrinkTrackerDatabase.getDatabase(applicationContext, GlobalScope)
        val person: Person = db.personDao().findPerson()!!

        var alcoholLevel: Double = person.perMil
        val alcoholLevelBefore = alcoholLevel

        if (alcoholLevel > 0.0) {
            alcoholLevel -= ALCOHOL_REDUCE_PER_HOUR / HistoryActivity.AMOUNT_OF_REDUCES_PER_HOUR

            if (alcoholLevelBefore > 0.5 && alcoholLevel < 0.5) {
                showNotification("Fahren erlaubt", "Du kannst wieder legal fahren")
            } else if (alcoholLevel <= 0.0) {
                alcoholLevel = 0.0
                showNotification("Komplett nüchtern", "Du bist nun vollständig nüchtern")
            }

            person.perMil = alcoholLevel
            db.personDao().update(person)
        } else {
            // TODO 1 (optional): cancel alcohol reducer properly
            // cancelAlcoholReducer()
        }
    }

/*    private fun cancelAlcoholReducer() {
        val intent = Intent(applicationContext, AlcoholReducerAlarmReceiver::class.java)
        val pIntent = PendingIntent.getBroadcast(
            this, AlcoholReducerAlarmReceiver.REQUEST_CODE,
            intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarm = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarm.cancel(pIntent)
    }*/

    private fun showNotification(title: String, text: String) {
        if (manager == null) {
            manager = NotificationManagerCompat.from(applicationContext)
        }
        if (notificationChannel == null) {
            notificationChannel =
                NotificationChannel(cId, cName, NotificationManager.IMPORTANCE_HIGH)
            manager!!.createNotificationChannel(notificationChannel!!)
        }

        val intent = Intent(this, HistoryActivity::class.java)

        val notification: Notification? = NotificationCompat.Builder(applicationContext, cId)
            // TODO (priority low): add a custom app icon for notifications
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(text)
            .setContentIntent(
                PendingIntent.getActivity(
                    this,
                    0,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            )
            .build()

        manager!!.notify(notificationId++, notification!!)
    }
}