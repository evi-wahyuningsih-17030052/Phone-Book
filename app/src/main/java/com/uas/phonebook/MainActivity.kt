package com.uas.phonebook

import android.Manifest
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: AdapterContact
    var contactsList: ArrayList<ModelContact>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        contactsList = ArrayList()
        checkPermissions()
    }

    private fun checkPermissions() {
        Dexter.withActivity(this).withPermission(Manifest.permission.READ_CONTACTS)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) {
                    if (response.permissionName
                            .equals(Manifest.permission.READ_CONTACTS)
                    ) {
                        loadContacts()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: com.karumi.dexter.listener.PermissionRequest?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                    Toast.makeText(this@MainActivity, "Permission Required", Toast.LENGTH_SHORT)
                        .show()
                }
            }).check()
    }

    private fun loadContacts() {
        val phones: Cursor? = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        )
        while (phones!!.moveToNext()) {
            val name: String = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val number: String = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            val contacts = ModelContact(name, number)
            contactsList?.add(contacts)
            adapter = AdapterContact(contactsList!!)
            rv_contact.layoutManager = LinearLayoutManager(this)
            rv_contact?.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
            rv_contact.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }
}