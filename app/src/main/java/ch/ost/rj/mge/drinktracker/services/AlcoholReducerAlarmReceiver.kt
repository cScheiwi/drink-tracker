package ch.ost.rj.mge.drinktracker.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlcoholReducerAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        context.startService(Intent(context, AlcoholReducerService::class.java))
    }

    companion object {
        const val REQUEST_CODE = 12345
    }
}
