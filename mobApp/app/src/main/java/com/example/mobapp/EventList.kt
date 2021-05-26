package com.example.mobapp

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.add_product.*
import kotlinx.android.synthetic.main.event_list.*

class EventList : AppCompatActivity() {

    companion object{
        lateinit var dbHandler: DbHandler
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.event_list)

        dbHandler = DbHandler(this, null,null,1)

        viewEvents()

        addEvent.setOnClickListener {
            val intent = Intent(this, AddProductActivity::class.java)
            startActivity(intent)
        }
    }

    private fun viewEvents () {
        val eventList = dbHandler.getEvents(this)
        val adapter = Adapter(this, eventList)
        val rv : RecyclerView = findViewById(R.id.rv)

        rv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false) as RecyclerView.LayoutManager
        rv.adapter = adapter

    }

    override fun onResume() {
        viewEvents()
        super.onResume()
    }
}
