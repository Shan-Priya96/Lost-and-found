package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.File


class MyAdapter3 (private val userList: ArrayList<Item>,private val context: UseListActivity3, private val ss: String) :RecyclerView.Adapter<MyAdapter3.MyViewHolder>() {


    private lateinit var database : FirebaseDatabase

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_item3,
            parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        database=FirebaseDatabase.getInstance()
        val currentitem = userList[position]

                holder.firstName.text = currentitem.name
                holder.lastName.text = currentitem.lost
                holder.age.text = currentitem.loc
        val hh=currentitem.ns
        val storageref= FirebaseStorage.getInstance().getReference(hh.toString())
        val localfile = File.createTempFile("tempImage",".jpg")
        Log.d("message",localfile.absolutePath.toString())
//        holder.ite.setImageResource( R.drawable.back)
        storageref.getFile(localfile).addOnSuccessListener {
            val bitmap= BitmapFactory.decodeFile(localfile.absolutePath)

            holder.ite.setImageBitmap(bitmap)

        }

                holder.btn.setOnClickListener {
//            val bundle=Bundle()
//            val intent = Intent(context, bridge:: class.java)
//                startActivity(context,intent,bundle)
                    val databaseRef = database.reference.child("items")
                    databaseRef.get().addOnSuccessListener { result->
                        for (document in result.children){
                            val item = document.getValue(Item::class.java)
                            if(item?.ns==currentitem.ns)
                            {
                                document.ref.removeValue()
                            }
                        }
                    }
                    val databaseRef2 = database.reference.child("items2")
                    databaseRef2.get().addOnSuccessListener { result->
                        for (document in result.children){
                            val item = document.getValue(Item2::class.java)
                            if(item?.ns==currentitem.ns)
                            {
                                document.ref.removeValue()
                            }
                        }
                    }


                }

    }

    override fun getItemCount(): Int {
        return  userList.size
    }
    class MyViewHolder(itemView :View) :RecyclerView.ViewHolder(itemView){
        val firstName:TextView = itemView.findViewById(R.id.Name)
        val lastName : TextView = itemView.findViewById(R.id.lflf)
        val age: TextView = itemView.findViewById(R.id.locationx)
        val btn: Button=itemView.findViewById(R.id.delete)
        val ite: ImageView =itemView.findViewById(R.id.img3)

//        init{
//            btn.setOnClickListener{
//                val intent = Intent(this, UseListActivity2:: class.java)
//                startActivity(intent)
//            }
//        }

    }

}