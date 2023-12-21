package com.mamjadd7.contactappwithroomandlivedata.databases

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update



@Dao
interface ContactDao {

    @Insert
    suspend fun insertContact(contact : ContactModel)

    @Update
    suspend fun updateContact(contact : ContactModel)

    @Delete
    suspend fun deleteContact(contact : ContactModel)

    @Query("SELECT * FROM contactDB")
    fun getContact() : LiveData<List<ContactModel>>
}