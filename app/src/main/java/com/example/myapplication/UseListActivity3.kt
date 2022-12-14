package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class UseListActivity3 : AppCompatActivity() {
    private lateinit var dbref: DatabaseReference
    private lateinit var userRecyclerview: RecyclerView
    private lateinit var itemArrayList: ArrayList<Item>
//    private lateinit var itemArrayList2: ArrayList<Item2>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_use_list3)
        val ss=intent.getStringExtra("email").toString()
        userRecyclerview = findViewById(R.id.user3)
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.setHasFixedSize(true)

        itemArrayList = arrayListOf<Item>()
        getUserData(ss)
        getUserData2(ss)
        val btn2=findViewById<Button>(R.id.back)
        btn2.setOnClickListener{
            val intent = Intent(this, bridge:: class.java)
            intent.putExtra("email",ss)
            startActivity(intent)
        }
    }

    private fun getUserData(ss :String) {
        dbref = FirebaseDatabase.getInstance().getReference("items")
        var query = dbref.orderByChild("ss").equalTo(ss) //order by email
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {

                    for (userSnapshot in snapshot.children) {


                            val item = userSnapshot.getValue(Item::class.java)

                            itemArrayList.add(item!!)


                    }
                    userRecyclerview.adapter = MyAdapter3(itemArrayList,UseListActivity3(),ss)
                }
            }


            override fun onCancelled(error: DatabaseError) {
                TODO("NOT YET IMPLEMENTED")
            }
        }
        )
    }
    private fun getUserData2(ss :String) {
        dbref = FirebaseDatabase.getInstance().getReference("items2")
        var query = dbref.orderByChild("ss").equalTo(ss)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {

                    for (userSnapshot in snapshot.children) {


                        val item2 = userSnapshot.getValue(Item::class.java)
                        itemArrayList.add(item2!!)


                    }
                    userRecyclerview.adapter = MyAdapter3(itemArrayList,UseListActivity3(),ss)
                }
            }


            override fun onCancelled(error: DatabaseError) {
                TODO("NOT YET IMPLEMENTED")
            }
        }
        )
    }

}