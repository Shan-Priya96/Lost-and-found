package com.example.myapplication

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.databinding.ActivityStorage1Binding
import com.example.myapplication.databinding.ActivityStorage2Binding
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

class Storage2 : AppCompatActivity() {
    lateinit var binding: ActivityStorage2Binding
    lateinit var ImageUri :Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStorage2Binding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.selectImageBtn2.setOnClickListener{

            selectImage()
        }
        binding.uploadImageBtn2.setOnClickListener{
            uploadImage()
        }
    }



    private fun uploadImage() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading...")
        progressDialog.setCancelable(false)
        progressDialog.show()
        val formatter = SimpleDateFormat("yyyyMM_dd_HH_MM_SS", Locale.getDefault())
        val now = Date()
        val fileName = formatter.format(now)
        val ss=intent.getStringExtra("email").toString()
        val storageReference = FirebaseStorage.getInstance().getReference("images/$fileName")
        storageReference.putFile(ImageUri).addOnSuccessListener {
            val ur=storageReference.child("images/$fileName"+".jpg").downloadUrl.toString()
            binding.firebaseImage2.setImageURI(null)
            Toast.makeText(this@Storage2, "Successfully Uploaded", Toast.LENGTH_SHORT).show()
            val intent=Intent(this,addItem2::class.java)
            intent.putExtra("uri","images/$fileName")
            intent.putExtra("email",ss)
            startActivity(intent)
            if (progressDialog.isShowing) progressDialog.dismiss()

        }.addOnFailureListener {
            if (progressDialog.isShowing) progressDialog.dismiss()
            Toast.makeText(this@Storage2, "Failed", Toast.LENGTH_SHORT).show()


        }
    }
    private fun selectImage() {
        val intent =Intent()
        intent.type="image/*"
        intent.action=Intent.ACTION_GET_CONTENT

        startActivityForResult(intent,100)

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {

            ImageUri = data?.data!!
            binding.firebaseImage2.setImageURI(ImageUri)


        }
    }

}