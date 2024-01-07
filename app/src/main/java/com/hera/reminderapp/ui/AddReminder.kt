package com.hera.reminderapp.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hera.reminderapp.databinding.ActivityAddReminderBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddReminder : AppCompatActivity() {

    private lateinit var binding: ActivityAddReminderBinding
    private val calendar: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddReminderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSubmit.setOnClickListener {
            val title = binding.edTitle.text.toString()
            val description = binding.edDescription.text.toString()
            val dateTime = binding.edDateTime.text.toString()

            if (title.isNotBlank() && description.isNotBlank() && dateTime.isNotBlank()) {
                // Semua field diisi, kirim hasil kembali ke MainActivity
                val resultIntent = Intent().apply {
                    putExtra("title", title)
                    putExtra("description", description)
                    putExtra("dateTime", dateTime)
                }
                setResult(RESULT_OK, resultIntent)
                finish()
            } else {
                // Salah satu atau lebih field kosong, tampilkan pesan kesalahan
                if (title.isBlank()) {
                    binding.edTitle.error = "Title is required"
                }
                if (description.isBlank()) {
                    binding.edDescription.error = "Description is required"
                }
                if (dateTime.isBlank()) {
                    binding.edDateTime.error = "Date and time are required"
                }
            }
        }

        // Tambahkan logika untuk menampilkan dialog pemilihan tanggal dan jam saat ed_date_time diklik
        binding.edDateTime.setOnClickListener {
            showDateTimePickerDialog(binding)
        }
    }

    private fun showDateTimePickerDialog(binding: ActivityAddReminderBinding) {
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                // Setelah memilih tanggal, tampilkan TimePickerDialog
                showTimePickerDialog(binding)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.show()
    }

    private fun showTimePickerDialog(binding: ActivityAddReminderBinding) {
        val timePickerDialog = TimePickerDialog(
            this,
            { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)

                // Format tanggal dan waktu yang dipilih
                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                val formattedDateTime = sdf.format(calendar.time)

                // Set text pada edDateTime
                binding.edDateTime.setText(formattedDateTime)
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        )

        timePickerDialog.show()
    }
}
