package com.example.myapplication

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignInActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    private val emailPattern = "[a-zA-Z0-9._-]+@iitp+\\.+ac+\\.+in"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        auth =FirebaseAuth.getInstance()

        val signInEmail :EditText=findViewById(R.id.signInEmail)
        val signInPassword :EditText =findViewById(R.id.signInPassword)
        val signInPasswordLayout : TextInputLayout=findViewById(R.id.signInPasswordLayout)
        val signInBtn: Button =findViewById(R.id.signInBtn)
        val signInProgressBar: ProgressBar=findViewById(R.id.signInProgressBar)
        val forgotpassword: Button =findViewById(R.id.btn_forgot_password)


        val signUpText : TextView = this.findViewById(R.id.signUpText)

        signUpText.setOnClickListener{
            val intent = Intent(this, SignUpActivity:: class.java)
            startActivity(intent)
        }
        signInBtn.setOnClickListener{
            signInProgressBar.visibility=View.VISIBLE
            signInPasswordLayout.isPasswordVisibilityToggleEnabled =true

            val email =signInEmail.text.toString()
            val password =signInPassword.text.toString()

            if(email.isEmpty()|| password.isEmpty()){
                if(email.isEmpty())
                {
                    signInEmail.error ="Enter your email address"
                }
                if (password.isEmpty()){
                    signInPassword.error="Enter your password"
                    signInPasswordLayout.isPasswordVisibilityToggleEnabled =false
                }
                signInProgressBar.visibility=View.GONE
                Toast.makeText(this, "Enter valid details", Toast.LENGTH_SHORT).show()

            }
            else if(!email.matches(emailPattern.toRegex()))
            {  signInProgressBar.visibility=View.GONE
                signInEmail.error ="Enter valid email address"
                Toast.makeText(this,"Enter valid email address", Toast.LENGTH_SHORT).show()
            }
            else if(password.length <6)
            {   signInPasswordLayout.isPasswordVisibilityToggleEnabled= false
                signInProgressBar.visibility=View.GONE
                signInPassword.error ="Enter password more than 6 characters"
                Toast.makeText(this,"Enter password more than 6 characters", Toast.LENGTH_SHORT).show()
            }
            else{
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener{
                    if(it.isSuccessful) {
                        val verification = auth.currentUser?.isEmailVerified
                        if (verification == true) {

                            val intent = Intent(this, bridge::class.java)
                            intent.putExtra("email", email)
                            startActivity(intent)
                        }
                        else
                        {
                            Toast.makeText(this,"Email not verified ",Toast.LENGTH_SHORT).show()
                        }
                    }
                    else{
                        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }

        forgotpassword.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle("Forgot Password")
            val view = layoutInflater.inflate(R.layout.dialog_forgot_password, null)
            val username = view.findViewById<EditText>(R.id.et_username)
            builder.setView(view)
            builder.setPositiveButton("Reset", DialogInterface.OnClickListener { _, _ ->
                forgotPassword(username)
            })

            builder.setNegativeButton("close", DialogInterface.OnClickListener { _, _ -> })
            builder.show()

        }}
        private fun forgotPassword(username :EditText) {
            if (username.text.toString().isEmpty())
            {
                return

        }
      if(!Patterns.EMAIL_ADDRESS.matcher(username.text.toString()).matches()) {

          return}
            auth.sendPasswordResetEmail(username.text.toString())
                .addOnCompleteListener{ task ->
                    if(task.isSuccessful)
                    {
                        Toast.makeText(this, "Email Sent", Toast.LENGTH_SHORT).show()
                    }
                }


    }}
