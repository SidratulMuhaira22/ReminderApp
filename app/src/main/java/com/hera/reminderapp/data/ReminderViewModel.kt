package com.hera.reminderapp.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.hera.reminderapp.data.network.RetrofitClient
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ReminderViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ReminderRepository
    val allReminders: LiveData<List<Reminder>>

    init {
        val reminderDao = ReminderDatabase.getDatabase(application).reminderDao()
        val reminderService = RetrofitClient.create()

        repository = ReminderRepository(reminderDao, reminderService)
        allReminders = repository.allReminders
    }

    fun refreshReminders() = viewModelScope.launch {
        repository.refreshReminders()
    }

    fun insertReminder(title: String, description: String, dateTime: Long) {
        viewModelScope.launch {
            repository.insertReminder(title, description, dateTime)
        }
    }

    fun insertReminder(reminder: Reminder) = viewModelScope.launch {
        repository.insertReminder(reminder)
    }
}
