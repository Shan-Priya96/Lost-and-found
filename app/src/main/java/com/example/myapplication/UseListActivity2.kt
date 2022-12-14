package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class UseListActivity2 : AppCompatActivity() {
    private lateinit var dbref2: DatabaseReference
    private lateinit var userRecyclerview2: RecyclerView
    private lateinit var itemArrayList2: ArrayList<Item2>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_use_list2)

        userRecyclerview2 = findViewById(R.id.user2)
        userRecyclerview2.layoutManager = LinearLayoutManager(this)
        userRecyclerview2.setHasFixedSize(true)

        itemArrayList2 = arrayListOf<Item2>()
        getUserData()
    }

    private fun getUserData() {
        dbref2 = FirebaseDatabase.getInstance().getReference("items2")

        dbref2.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {

                    for (userSnapshot in snapshot.children) {


                        val item2 = userSnapshot.getValue(Item2::class.java)
                        itemArrayList2.add(item2!!)


                    }
                    userRecyclerview2.adapter = MyAdapter2(itemArrayList2)
                }
            }


            override fun onCancelled(error: DatabaseError) {
                TODO("NOT YET IMPLEMENTED")
            }
        })
    }
}
