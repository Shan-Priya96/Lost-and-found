package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import java.io.File


class MyAdapter (private val userList: ArrayList<Item>, private val ss: String) :RecyclerView.Adapter<MyAdapter.MyViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_item,
            parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = userList[position]

        holder.firstName.text = currentitem.name
        holder.lastName.text = currentitem.lost
        holder.age.text=currentitem.loc
        val hh=currentitem.ns
        val storageref=FirebaseStorage.getInstance().getReference(hh.toString())
        val localfile = File.createTempFile("tempImage",".jpg")
        Log.d("message",localfile.absolutePath.toString())
//        holder.ite.setImageResource( R.drawable.back)
        storageref.getFile(localfile).addOnSuccessListener {
            val bitmap=BitmapFactory.decodeFile(localfile.absolutePath)

            holder.ite.setImageBitmap(bitmap)

        }

    }

    override fun getItemCount(): Int {
        return  userList.size
    }
    class MyViewHolder(itemView :View) :RecyclerView.ViewHolder(itemView){
        val firstName:TextView = itemView.findViewById(R.id.Name)
        val lastName : TextView = itemView.findViewById(R.id.lflf)
        val age :TextView =itemView.findViewById(R.id.locationx)
        val ite:ImageView=itemView.findViewById(R.id.img)

    }

}