package com.example.intellicater

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.intellicater.databinding.ActivityMainBinding
import com.example.waveoffood.Model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var email: String = ""
    private var password: String = ""
    private lateinit var auth : FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        database = Firebase.database.reference // Initializing database reference


        setupViews()
    }

    private fun setupViews() {
        binding.signInRedirect.setOnClickListener {
            startActivity(Intent(this@MainActivity, login_activity::class.java))
        }

        binding.RegisterButton.setOnClickListener {
            if (validateFields()) {
//                val iNext = Intent(this@MainActivity, otp_screen::class.java)
//                iNext.putExtra("email", email)
//                iNext.putExtra("pass", password)
//                startActivity(iNext)
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        saveUserData()
                        var intent = Intent(this@MainActivity, home_screen::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@MainActivity, it.exception?.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
    }

    private fun saveUserData() {
        val user = UserModel(email, password)
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        // save data to Firebase data base
        database.child("user").child(userId).setValue(user)
    }

    private fun validateFields(): Boolean {
        email = binding.emailAddress.text.toString().trim()
        password = binding.password.text.toString().trim()
        val confirmPassword = binding.confirmPassword.text.toString().trim()

        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this@MainActivity, "Please fill in all the fields", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!email.contains("moderncoe.edu.in")) {
            Toast.makeText(this@MainActivity, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
            return false
        }

        if (password != confirmPassword) {
            Toast.makeText(this@MainActivity, "Password and Confirm Password must be the same", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }
}
