package ch.ost.rj.mge.drinktracker.adapter

import android.content.Context
import android.database.Cursor
import android.view.View

import android.widget.TextView
import androidx.cursoradapter.widget.ResourceCursorAdapter
import ch.ost.rj.mge.drinktracker.R

// TODO implement this way?
class ClientCursorAdapter(context: Context?, layout: Int, cursor: Cursor?, flags: Int) :
    ResourceCursorAdapter(context, layout, cursor, flags) {

    override fun bindView(view: View, context: Context?, cursor: Cursor) {
/*        val name = view.findViewById(R.id.XXX) as TextView
        name.text = cursor.getString(cursor.getColumnIndex("name"))

        val phone = view.findViewById(R.id.XXX) as TextView
        phone.text = cursor.getString(cursor.getColumnIndex("phone"))*/
    }
}