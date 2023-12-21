package com.mamjadd7.contactappwithroomandlivedata.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.mamjadd7.contactappwithroomandlivedata.CreateContactActivity
import com.mamjadd7.contactappwithroomandlivedata.R
import com.mamjadd7.contactappwithroomandlivedata.databases.ContactDatabase
import com.mamjadd7.contactappwithroomandlivedata.databases.ContactModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ContactAdapter(private val context : Context, private val contactLists : List<ContactModel>): RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    inner class ContactViewHolder(private val view : View): RecyclerView.ViewHolder(view){
        val firstName: TextView = view.findViewById<TextView>(R.id.textViewContactName)
        val btnDelete = view.findViewById<ImageView>(R.id.icDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.main_contact_layout, parent, false)
        return ContactViewHolder(view)
    }

    override fun getItemCount(): Int {
        return contactLists.size
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contactList = contactLists[position]
        holder.firstName.text = contactList.contactFirstName

        holder.btnDelete.setOnClickListener {
            val database = ContactDatabase.getContacts(context)
            GlobalScope.launch {
                database.contactDao().deleteContact(contactLists[position])
            }
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, CreateContactActivity::class.java)
            intent.putExtra("FLAG", false)
            intent.putExtra("ID", contactList.uid)
            intent.putExtra("CONTACT_FIRST_NAME", contactList.contactFirstName)
            intent.putExtra("CONTACT_LAST_NAME", contactList.contactLastName)
            intent.putExtra("CONTACT_PHONE_NO", contactList.contactPhoneNo)
            context.startActivity(intent)
        }
    }
}