package com.example.intellicater

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.intellicater.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class login_activity : AppCompatActivity() {
    private val binding : ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        val password = findViewById<EditText>(R.id.password1)
        auth = FirebaseAuth.getInstance()



        binding.signUpRedirect.setOnClickListener{
            startActivity(Intent(this@login_activity,MainActivity::class.java))
        }

        binding.forgotPassRedirect.setOnClickListener {
            startActivity(Intent(this@login_activity,forgotpass_activity::class.java))
        }

        binding.LoginButton.setOnClickListener {
            val e = binding.emailAddress1.text.toString().trim()
            val pass = binding.password1.text.toString().trim()

            if (!(e.isEmpty()) && !(pass.isEmpty())) {
                if (e.contains("moderncoe.edu.in")) {
                    auth.signInWithEmailAndPassword(e, pass).addOnCompleteListener { signIn ->
                        if (signIn.isSuccessful) {
                            startActivity(Intent(this@login_activity, home_screen::class.java))
                            Toast.makeText(this, "SignIn Successful", Toast.LENGTH_SHORT).show()

                        } else {
                            Toast.makeText(
                                this@login_activity,
                                signIn.exception?.message.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Only College email is accepted", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this@login_activity, "Please enter the field", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
