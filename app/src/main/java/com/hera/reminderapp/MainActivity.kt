package com.hera.reminderapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hera.reminderapp.data.ReminderAdapter
import com.hera.reminderapp.data.ReminderViewModel
import com.hera.reminderapp.databinding.ActivityMainBinding
import com.hera.reminderapp.ui.AddReminder


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ReminderViewModel
    private lateinit var adapter: ReminderAdapter

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ReminderAdapter(emptyList())

        val recyclerView: RecyclerView = binding.recyclerViewTodos
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this).get(ReminderViewModel::class.java)

        viewModel.allReminders.observe(this, { reminders ->
            reminders?.let { adapter.setData(it) }
        })

        viewModel.refreshReminders()

        binding.imgAdd.setOnClickListener {
            val intent = Intent(this, AddReminder::class.java)
            startActivityForResult(intent, ADD_REMINDER_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_REMINDER_REQUEST && resultCode == Activity.RESULT_OK) {
            // Handle the result from AddReminder activity here
            // You can retrieve data from the intent if needed
            // For example, refresh reminders after adding a new one
            viewModel.refreshReminders()
        }
    }

    companion object {
        const val ADD_REMINDER_REQUEST = 1
    }
}

