package com.uas.phonebook

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_list_contact.view.*

class AdapterContact(private val items: ArrayList<ModelContact>) : RecyclerView.Adapter<AdapterContact.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_contact, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(items[position])
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindData(data: ModelContact) {
            itemView.tv_title_contact.text = data.nameContact
            itemView.tv_number_contact.text = data.numberContact
        }
    }
}