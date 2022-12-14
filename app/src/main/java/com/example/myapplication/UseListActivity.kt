package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class UseListActivity : AppCompatActivity() {
    private lateinit var dbref: DatabaseReference
    private lateinit var userRecyclerview: RecyclerView
    private lateinit var itemArrayList: ArrayList<Item>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_use_list)
        val ss=intent.getStringExtra("email").toString()

        userRecyclerview = findViewById(R.id.userList)
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.setHasFixedSize(true)

        itemArrayList = arrayListOf<Item>()
        getUserData(ss)
    }

    private fun getUserData(ss: String) {
        dbref = FirebaseDatabase.getInstance().getReference("items")
//        var query = dbref.orderByChild("name").equalTo("shan")
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {

                    for (userSnapshot in snapshot.children) {


                        val item = userSnapshot.getValue(Item::class.java)
                        itemArrayList.add(item!!)


                    }
                    userRecyclerview.adapter = MyAdapter(itemArrayList,ss)
                }
            }


            override fun onCancelled(error: DatabaseError) {
                TODO("NOT YET IMPLEMENTED")
            }
        }
        )
    }

}
