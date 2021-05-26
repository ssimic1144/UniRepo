package com.example.mobapp

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.lo_event.view.*
import kotlinx.android.synthetic.main.lo_event_update.view.*
import kotlin.time.measureTime

class Adapter(mCtx : Context, val events : ArrayList<Event>) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {

    val mCtx = mCtx

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val txtEventName = itemView.nameView
        val txtEventDate = itemView.dateView
        val btnUpdate = itemView.btnUpdate
        val btnDel = itemView.btnDelete
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.lo_event, parent, false)

        return ViewHolder(v)

    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun onBindViewHolder(holder: Adapter.ViewHolder, position: Int) {
        val event : Event = events[position]

        holder.txtEventName.text = event.eventName
        holder.txtEventDate.text = event.eventCapacity.toString()

        holder.btnDel.setOnClickListener {
            val eventName = event.eventName

            val alertDialog = AlertDialog.Builder(mCtx)
                            .setTitle("Warning")
                            .setMessage("Are you sure to delete : $eventName ?")
                            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                                if (EventList.dbHandler.deleteEvent(event.eventId)){
                                    events.removeAt(position)
                                    notifyItemRemoved(position)
                                    notifyItemRangeChanged(position, events.size)
                                    Toast.makeText(mCtx, "Event $eventName deleted!", Toast.LENGTH_SHORT).show()
                                }else{
                                    Toast.makeText(mCtx, "Error!", Toast.LENGTH_SHORT).show()
                                }
                            })
                            .setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->  }).setIcon(R.drawable.ic_baseline_warning_24).show()

        }

        holder.btnUpdate.setOnClickListener {
            val inflater = LayoutInflater.from(mCtx)

            val view = inflater.inflate(R.layout.lo_event_update, null)

            val txtEvName : TextView = view.findViewById(R.id.upName)
            val txtEvCap : TextView = view.findViewById(R.id.upCapacity)

            txtEvName.text = event.eventName
            txtEvCap.text = event.eventCapacity

            val builder = AlertDialog.Builder(mCtx)
                .setTitle("Update event")
                .setView(view)
                .setPositiveButton("Update", DialogInterface.OnClickListener { dialog, which ->
                    val isUpdate = EventList.dbHandler.updateEvent(
                        event.eventId.toString(),
                        view.upName.toString(),
                        view.upCapacity.toString()
                    )
                    if (isUpdate==true){
                        events[position].eventName = view.upName.text.toString()
                        events[position].eventCapacity = view.upCapacity.text.toString()
                        notifyDataSetChanged()
                        Toast.makeText(mCtx, "Updated successful!", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(mCtx, "Update Error!", Toast.LENGTH_SHORT).show()
                    }
                })
                .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->  })
            val alert = builder.create()
            alert.show()
        }
    }
}

