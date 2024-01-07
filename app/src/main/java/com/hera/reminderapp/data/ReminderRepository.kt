package com.hera.reminderapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.hera.reminderapp.data.network.ApiService
import retrofit2.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReminderRepository(private val reminderDao: ReminderDao, private val reminderService: ApiService) {

    val allReminders: LiveData<List<Reminder>> = reminderDao.getAllReminders()

    suspend fun refreshReminders() {
        withContext(Dispatchers.IO) {
            try {
                val response: Response<Reminder> = reminderService.getReminders()

                if (response.isSuccessful) {
                    val reminder = response.body()
                    reminder?.let {
                        // Check if title is not null or empty before inserting
                        if (it.title != null && it.title.isNotBlank()) {
                            insertReminder(it)
                        } else {
                            Log.e("ReminderRepository", "Title is null or empty. Ignoring the reminder.")
                        }
                    }
                } else {
                    Log.e("ReminderRepository", "Unsuccessful response: ${response.code()}")
                    // Handle unsuccessful response
                }
            } catch (e: Exception) {
                Log.e("ReminderRepository", "Exception during network request: ${e.message}")
                // Handle network or other exceptions
            }
        }
    }

    suspend fun insertReminder(reminder: Reminder) {
        reminderDao.insertReminder(reminder)
    }

    suspend fun insertReminder(title: String, description: String, dateTime: Long) {
        val reminder = Reminder(title = title, description = description, dateTime = dateTime)
        reminderDao.insertReminder(reminder)
    }
}
