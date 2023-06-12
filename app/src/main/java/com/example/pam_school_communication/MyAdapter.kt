package com.example.pam_school_communication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class MyAdapter(private val notificationList: ArrayList<Notification>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val showName: TextView = itemView.findViewById(R.id.tvName)
        val showSurname: TextView = itemView.findViewById(R.id.tvSurname)
        val showEmail: TextView = itemView.findViewById(R.id.tvEmail)
        val showNotification: TextView = itemView.findViewById(R.id.tvNotification)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.showName.text = notificationList[position].name
        holder.showSurname.text = notificationList[position].surname
        holder.showEmail.text = notificationList[position].email
        holder.showNotification.text = notificationList[position].note
    }

    override fun getItemCount(): Int {
        return notificationList.size
    }

}