package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase


class addItem : AppCompatActivity() {
//     lateinit var binding :ActivityAddItemBinding
    private lateinit var database : FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityAddItemBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_add_item)
//        setContentView(binding.root)
//        binding.getImage.setOnClickListener{
//
//        }


        val itemName: EditText = findViewById(R.id.ItemName)
        val lf: EditText = findViewById(R.id.Lostfound)
        val locn: EditText = findViewById(R.id.location)


        val addItemBtn: Button = findViewById(R.id.addItem)
        val ss=intent.getStringExtra("email").toString()

        database = FirebaseDatabase.getInstance()
        val getImage= findViewById<Button>(R.id.getImage)
        getImage.setOnClickListener{
            val intent =Intent(this,Storage::class.java)
            val email=intent.putExtra("email",ss)
            startActivity(intent)

        }
        val ns: String=intent.getStringExtra("uri").toString()
        val st=intent.getStringExtra("email").toString()
//        Toast.makeText(this, "Cri aarha hai" +ns, Toast.LENGTH_SHORT).show()
        addItemBtn.setOnClickListener {
            val name = itemName.text.toString()
            val lost = lf.text.toString()
            val loc = locn.text.toString()



            if(name.isEmpty() || lost.isEmpty()||loc.isEmpty()) {
                if (name.isEmpty()) {
                    itemName.error = "Enter your Name "
                }
                if (lost.isEmpty()) {
                    lf.error = "Enter Phone Number"
                }
                if (loc.isEmpty()) {
                    locn.error = "Enter Location"
                }
//                if(ns.isNullOrBlank())
//                {
//                    Toast.makeText(this,"Upload Image", Toast.LENGTH_SHORT).show()
//                }

                Toast.makeText(this,"Enter valid details", Toast.LENGTH_SHORT).show()
            }

            else{

            val item = Item(name, lost, loc,st,ns)
             //  val itemchk = item.toString()
              //  val itemck=Item(lost,itemchk)
            val databaseRef = database.reference.child("items")
            databaseRef.push().setValue(item).addOnCompleteListener {
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