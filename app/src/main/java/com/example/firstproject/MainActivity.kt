package com.example.firstproject

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.ContactsContract.PhoneLookup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {

    private lateinit var facebookBtn: Button
    private lateinit var githubbtn: Button
    private lateinit var emailbtn: Button
    private lateinit var mypositon: Button
    private lateinit var call: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        facebookBtn = findViewById(R.id.facebtn)
        githubbtn = findViewById(R.id.gitbtn)
        emailbtn = findViewById(R.id.emailbtn)
        mypositon = findViewById(R.id.mapbtn)
        call = findViewById(R.id.call)

        facebookBtn.setOnClickListener {
            Toast.makeText(this@MainActivity, "Hello", Toast.LENGTH_LONG).show()

            val i = Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse("https://www.facebook.com/profile.php?id=100003494998781")
            }

            if (i.resolveActivity(packageManager) != null) {
                startActivity(i)
            }
        }
        githubbtn.setOnClickListener {
            val t = Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse("https://github.com/AbdulqaderAlhemyari")
            }

            if (t.resolveActivity(packageManager) != null) {
                startActivity(t)
            }
        }
        emailbtn.setOnClickListener {
            val r = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_EMAIL, arrayOf("CodingAcademy@gmail.com"))
                putExtra(Intent.EXTRA_SUBJECT, "Sending challenge")
                putExtra(Intent.EXTRA_TEXT, "The challenge is solved ")
            }
            if (r.resolveActivity(packageManager) != null) {
                val chooser = Intent.createChooser(r, "Send By:")
                startActivity(chooser)
            }
        }
        mypositon.setOnClickListener {
            val v = Intent().apply {
                action = Intent.ACTION_VIEW
                data =
                    Uri.parse("https://www.google.com/maps/search/%D8%B4%D8%A7%D8%B1%D8%B9%20%D8%A7%D9%84%D8%AD%D8%B1%D9%8A%D8%A9%D8%8C%20%D8%B5%D9%86%D8%B9%D8%A7%D8%A1%E2%80%8E%D8%8C%D8%8C%20Sana'a%2C%20Yemen/@15.364584543530366,44.198298776792114,17z?hl=en")
            }

            if (v.resolveActivity(packageManager) != null) {
                startActivity(v)
            }
        }

        call.setOnClickListener {
            val hasWriteContactsPermission = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_CONTACTS
            )
            if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                    arrayOf(Manifest.permission.WRITE_CONTACTS), 1
                )
            } else {
                val contactUri = ContactsContract.Contacts.CONTENT_URI
                val queryFields = arrayOf(ContactsContract.Contacts.DISPLAY_NAME)
                val select: String =
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " = 'Abdulqader'"
                val cursor = contentResolver.query(contactUri, queryFields, select, null, null)

                cursor?.use {
                    if (it.count == 0) {
                        val addContactIntent = Intent().apply {
                            action = Intent.ACTION_INSERT
                            setType(ContactsContract.Contacts.CONTENT_TYPE)
                            putExtra(ContactsContract.Intents.Insert.NAME, "Abdulqader")
                            putExtra(ContactsContract.Intents.Insert.PHONE, "+96773791000")
                        }
                        if (addContactIntent.resolveActivity(packageManager) != null) {
                            startActivity(addContactIntent)
                        }
                    } else {
                        val callIntent = Intent().apply {
                            action = Intent.ACTION_DIAL
                            data = Uri.parse("tel:+967773791000")

                        }
                        if (callIntent.resolveActivity(packageManager) != null) {
                            startActivity(callIntent)
                        }
                    }
                }
            }


        }


    }
}

