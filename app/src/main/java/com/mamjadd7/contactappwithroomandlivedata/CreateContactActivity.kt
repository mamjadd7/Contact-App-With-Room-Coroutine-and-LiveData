package com.mamjadd7.contactappwithroomandlivedata

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.mamjadd7.contactappwithroomandlivedata.databases.ContactDatabase
import com.mamjadd7.contactappwithroomandlivedata.databases.ContactModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CreateContactActivity : AppCompatActivity() {
    lateinit var edtFirstName : TextInputEditText
    lateinit var edtLastName : TextInputEditText
    lateinit var edtPhoneNo : TextInputEditText
    lateinit var btnSave : Button
    lateinit var btnCancel : Button

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_contact)
        edtFirstName = findViewById(R.id.editTextFirstName)
        edtLastName = findViewById(R.id.editTextLastName)
        edtPhoneNo = findViewById(R.id.editTextContactNo)
        btnSave = findViewById(R.id.buttonSave)
        btnCancel = findViewById(R.id.buttonCancel)


        closeCreateContactActivity()
        saveAndUpdateContact()
    }

    @SuppressLint("SuspiciousIndentation")
    @OptIn(DelicateCoroutinesApi::class)
    fun saveAndUpdateContact() {
        val flag = intent.getBooleanExtra("FLAG", true)
        if (flag) {
            btnSave.text = "Save"
//            val dbHelper = DbHelper(this@CreateContactActivity)
//            val database = ContactDatabase.getContact(this@CreateContactActivity)
            btnSave.setOnClickListener {
                val fName = edtFirstName.text.toString()
                val lName = edtLastName.text.toString()
                val pNo = edtPhoneNo.text.toString().trim()

                if (TextUtils.isEmpty(fName)) {
                    edtFirstName.error = "Please Enter first name"
                    return@setOnClickListener
                } else if (TextUtils.isEmpty(lName)) {
                    edtLastName.error = "Please Enter last name"
                    return@setOnClickListener
                } else if (TextUtils.isEmpty(pNo)) {
                    edtPhoneNo.error = "Please Enter your contact no"
                    return@setOnClickListener
                }

                val database = ContactDatabase.getContacts(this@CreateContactActivity)
                GlobalScope.launch {
                    database.contactDao().insertContact(ContactModel(0,fName, lName, pNo))
                }
                Toast.makeText(this@CreateContactActivity, "Contact Saved", Toast.LENGTH_SHORT).show()
                    edtFirstName.setText("")
                    edtLastName.setText("")
                    edtPhoneNo.setText("")



                finish()

            }
        } else {
            btnSave.text = "Update"



            edtFirstName.setText(intent.getStringExtra("CONTACT_FIRST_NAME"))
            edtLastName.setText(intent.getStringExtra("CONTACT_LAST_NAME"))
            edtPhoneNo.setText(intent.getStringExtra("CONTACT_PHONE_NO"))
            btnSave.setOnClickListener {


                val fName = edtFirstName.text.toString()
                val lName = edtLastName.text.toString()
                val pNo = edtPhoneNo.text.toString().trim()

                if (TextUtils.isEmpty(fName)) {
                    edtFirstName.error = "Please Enter first name"
                    return@setOnClickListener
                } else if (TextUtils.isEmpty(lName)) {
                    edtLastName.error = "Please Enter last name"
                    return@setOnClickListener
                } else if (TextUtils.isEmpty(pNo)) {
                    edtPhoneNo.error = "Please Enter your contact no"
                    return@setOnClickListener
                }
                val position = intent.getLongExtra("ID", 0)
//                val dbHelper = DbHelper(this@CreateContactActivity)

                val database = ContactDatabase.getContacts(this@CreateContactActivity)
                GlobalScope.launch {
                    database.contactDao().updateContact(ContactModel(position, fName, lName, pNo))
                }



                    finish()



            }

        }
    }


    fun closeCreateContactActivity() {
        btnCancel.setOnClickListener {
            finish()
        }
    }
}