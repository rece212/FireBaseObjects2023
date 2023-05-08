package com.example.firebaseobjects2023

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.firebase.database.*

class MainActivity : AppCompatActivity()
{
    private lateinit var rootNode:FirebaseDatabase
    private lateinit var userReference: DatabaseReference
    private lateinit var listView: ListView
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView= findViewById(R.id.lsOutput)
        rootNode = FirebaseDatabase.getInstance()
        userReference= rootNode.getReference("users")

        val dc = DataClass("b Tapes","Black Tape",83,800)
        userReference.child(dc.id.toString()).setValue(dc)

        val list = ArrayList<String>()
        val adapter = ArrayAdapter<String>(this,R.layout.list_items,list)
        listView.adapter = adapter

        userReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                for (snapshot1 in snapshot.children) {
                    val dc2 = snapshot1.getValue(DataClass::class.java)
                    val txt = "Name is ${dc2?.name},Des: ${dc2?.description}"
                    txt?.let { list.add(it) }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle cancelled event
            }
        })



    }
}