package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class addItem2 : AppCompatActivity() {
    private lateinit var database : FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item2)

        val itemName2: EditText = findViewById(R.id.ItemName2)
        val lf2: EditText = findViewById(R.id.Lostfound2)
        val locn2:EditText =findViewById(R.id.location2)

        val addItemBtn: Button = findViewById(R.id.addItem2)
        val ss=intent.getStringExtra("email").toString()
        database = FirebaseDatabase.getInstance()

        val getImage= findViewById<Button>(R.id.getImage2)
        getImage.setOnClickListener{
            val intent =Intent(this,Storage2::class.java)
            val email=intent.putExtra("email",ss)
            startActivity(intent)

        }
        val ns: String=intent.getStringExtra("uri").toString()
        val st=intent.getStringExtra("email").toString()
//        Toast.makeText(this, "Cri aarha hai" +ns, Toast.LENGTH_SHORT).show()

        addItemBtn.setOnClickListener {
            val name2 = itemName2.text.toString()
            val lost2 = lf2.text.toString()
           val loc2 = locn2.text.toString()
            if(name2.isEmpty() || lost2.isEmpty()||loc2.isEmpty()) {
                if (name2.isEmpty()) {
                    itemName2.error = "Enter your Name"
                }
                if (lost2.isEmpty()) {
                    lf2.error = "Enter Phone Number"
                }
                if (loc2.isEmpty()) {
                    locn2.error = "Enter location last found"
                }

                Toast.makeText(this,"Enter valid details", Toast.LENGTH_SHORT).show()
            }

            else{

                val item2 = Item2(name2, lost2,loc2,st,ns)
                val databaseRef = database.reference.child("items2")
                databaseRef.push().setValue(item2).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "Added successfully", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, bridge::class.java)
                        intent.putExtra("email",st)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Cri aarha hai", Toast.LENGTH_SHORT).show()
                    }

                }
            }


        }
    }}