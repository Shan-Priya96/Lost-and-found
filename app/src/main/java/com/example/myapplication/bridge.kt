package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class bridge : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bridge)
        val btn=findViewById<Button>(R.id.addItems)
        val ss=intent.getStringExtra("email").toString()
        btn.setOnClickListener{
            val intent = Intent(this, addItem:: class.java)
            intent.putExtra("email",ss)
            startActivity(intent)
        }
        val btn2=findViewById<Button>(R.id.addItems2)
        btn2.setOnClickListener{
            val intent = Intent(this, addItem2:: class.java)
            intent.putExtra("email",ss)
            startActivity(intent)
        }
        val btn3=findViewById<Button>(R.id.viewItems)
        btn3.setOnClickListener{
            val intent = Intent(this, UseListActivity:: class.java)
            intent.putExtra("email",ss)
            startActivity(intent)
        }
        val btn4=findViewById<Button>(R.id.viewItems2)
        btn4.setOnClickListener{
            val intent = Intent(this, UseListActivity2:: class.java)
            startActivity(intent)
        }
        val btn5=findViewById<Button>(R.id.myposts)
        btn5.setOnClickListener{
            val intent = Intent(this, UseListActivity3:: class.java)
            intent.putExtra("email",ss)
            startActivity(intent)
        }
        val btn6=findViewById<Button>(R.id.signOut)
        btn6.setOnClickListener{
            val intent = Intent(this, MainActivity:: class.java)
            startActivity(intent)
        }

    }
}