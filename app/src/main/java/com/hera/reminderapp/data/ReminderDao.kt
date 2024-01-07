package com.hera.reminderapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface ReminderDao {
    @Query("SELECT * FROM reminder ORDER BY datetime ASC")
    fun getAllReminders(): LiveData<List<Reminder>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(reminder: Reminder)
}
