package com.example.mobapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.add_product.*

class AddProductActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //
        setContentView(R.layout.add_product)

        createBtn.setOnClickListener {
            if(newEventName.text.isEmpty()){
                Toast.makeText(this, "Enter event name", Toast.LENGTH_SHORT).show()
                newEventName.requestFocus()

            }else{
                val event = Event()
                event.eventName = newEventName.text.toString()
                if(newEventCapacity.text.isEmpty()){
                    event.eventCapacity = "0"
                }else{
                    event.eventCapacity = newEventCapacity.text.toString()
                }
                EventList.dbHandler.addEvent(this, event)
                clearEdits()
                newEventName.requestFocus()
            }

        }


        backBtn.setOnClickListener {
            val intent = Intent(this , EventList::class.java)
            startActivity(intent)
        }
    }

    private fun clearEdits(){
        newEventName.text.clear()
        newEventCapacity.text.clear()

    }



}
