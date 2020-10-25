package ch.ost.rj.mge.drinktracker.services

import android.app.IntentService
import android.content.Intent
import android.util.Log
import ch.ost.rj.mge.drinktracker.activities.HistoryActivity

class AlcoholReducerService : IntentService("AlcoholReducer") {

    // TODO How to get user from database here?

    companion object {
        const val ALCOHOL_REDUCE_PER_HOUR: Double = 0.1
    }

    override fun onHandleIntent(intent: Intent?) {

        Log.i("AlcoholReducer", "Alcohol reducer running");

        var alcoholLevel: Double = 1.05 // TODO get from current user

        if (alcoholLevel > 0.0) {
            alcoholLevel -= ALCOHOL_REDUCE_PER_HOUR / HistoryActivity.AMOUNT_OF_REDUCES_PER_HOUR

            if (alcoholLevel < 0.5) {

                // TODO trigger below 0.5 notification

            } else if (alcoholLevel <= 0.0){
                alcoholLevel = 0.0

                // TODO trigger zero notification
            }

            // TODO save new alcohol level

        } else {
            Log.i("AlcoholReducer", "User is sober");
        }

    }

}