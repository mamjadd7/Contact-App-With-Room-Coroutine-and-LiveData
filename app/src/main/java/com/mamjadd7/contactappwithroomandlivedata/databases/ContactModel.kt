package com.mamjadd7.contactappwithroomandlivedata.databases

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contactDB")
data class ContactModel(

    @PrimaryKey(autoGenerate = true) val uid: Long,
    val contactFirstName: String,
    val contactLastName: String,
    val contactPhoneNo: String
)
