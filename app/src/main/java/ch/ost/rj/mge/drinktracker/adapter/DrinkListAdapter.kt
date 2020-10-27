package ch.ost.rj.mge.drinktracker.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ch.ost.rj.mge.drinktracker.R
import ch.ost.rj.mge.drinktracker.entity.Drink
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter

class DrinkListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<DrinkListAdapter.DrinkViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var drinks = emptyList<Drink>()

    @SuppressLint("SimpleDateFormat")
    private val dateTimeFormatter = SimpleDateFormat("dd.MM.yyyy HH:mm:ss")

    inner class DrinkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val time: TextView = itemView.findViewById(R.id.adapter_history_datetime)
        val name: TextView = itemView.findViewById(R.id.adapter_history_drink_type)
        val quantity: TextView = itemView.findViewById(R.id.adapter_history_amount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkViewHolder {
        val itemView = inflater.inflate(R.layout.adapter_history_item, parent, false)
        return DrinkViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DrinkViewHolder, position: Int) {
        val current = drinks[position]
        holder.time.text = dateTimeFormatter.format(current.openAt)
        holder.name.text = current.name
        val quantityText = """${current.quantity}${current.quantityUnit.shortName}"""
        holder.quantity.text = quantityText
    }

    internal fun setDrinks(drinks: List<Drink>) {
        this.drinks = drinks
        notifyDataSetChanged()
    }

    override fun getItemCount() = drinks.size
}