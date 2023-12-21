package com.mamjadd7.contactappwithroomandlivedata.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi

@Database(entities = [ContactModel::class], version = 1)
abstract class ContactDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao

    companion object {
        @Volatile
        private var INSTANCE : ContactDatabase? = null

        @OptIn(InternalCoroutinesApi::class)
        fun getContacts(context: Context) : ContactDatabase {
            if (INSTANCE == null) {
                kotlinx.coroutines.internal.synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context, ContactDatabase::class.java, "contactDB"
                    )
                        .build()
                }
            }
            return INSTANCE!!
        }

    }

}