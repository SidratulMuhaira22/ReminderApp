package com.hera.reminderapp.data

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.hera.reminderapp.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ReminderAdapter(private var reminders: List<Reminder>) :
    RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.todos_item, parent, false)
        return ReminderViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        val reminder = reminders[position]
        holder.bind(reminder)
    }

    override fun getItemCount(): Int {
        return reminders.size
    }

    fun setData(newReminders: List<Reminder>) {
        reminders = newReminders
        notifyDataSetChanged()
    }

    inner class ReminderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val titleTextView: TextView = itemView.findViewById(R.id.title)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.decription)
        private val dateTimeTextView: TextView = itemView.findViewById(R.id.date_time)

        fun bind(reminder: Reminder) {
            titleTextView.text = reminder.title
            descriptionTextView.text = reminder.description

            val formattedDateTime = formatDateTime(reminder.dateTime)
            dateTimeTextView.text = formattedDateTime

            Log.d("ReminderAdapter", "Binding reminder: $reminder")
            Log.d("ReminderAdapter", "Formatted DateTime: $formattedDateTime")
        }

        private fun formatDateTime(dateTime: Long): String {
            val pattern = "yyyy-MM-dd HH:mm"
            val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
            return simpleDateFormat.format(Date(dateTime))
        }

    }
}
