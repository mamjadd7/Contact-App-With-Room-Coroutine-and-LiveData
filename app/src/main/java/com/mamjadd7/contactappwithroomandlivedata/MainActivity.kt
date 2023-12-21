package com.mamjadd7.contactappwithroomandlivedata

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mamjadd7.contactappwithroomandlivedata.adapters.ContactAdapter
import com.mamjadd7.contactappwithroomandlivedata.databases.ContactDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var btnAddContact : FloatingActionButton
    private lateinit var rvContact : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnAddContact = findViewById(R.id.floatingActionButtonAddContact)
        rvContact = findViewById(R.id.rvMainContact)




        displayContact()
        addContact()
    }
    private fun displayContact() {
        val database = ContactDatabase.getContacts(this)
        database.contactDao().getContact().observe(this, Observer {
            rvContact.setHasFixedSize(true)
            rvContact.setItemViewCacheSize(10)
            rvContact.layoutManager = LinearLayoutManager(this)
            rvContact.adapter = ContactAdapter(this, it)
        })
    }

    private fun addContact() {
        btnAddContact.setOnClickListener {
            startActivity(Intent(this@MainActivity, CreateContactActivity::class.java ))
        }
    }
}