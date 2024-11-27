package com.example.intellicater

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.example.waveoffood.Model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import papaya.`in`.sendmail.SendMail
import kotlin.random.Random
import kotlin.random.nextInt

@Suppress("DEPRECATION")
class otp_screen : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    private lateinit var database: DatabaseReference

    var email : String = ""
    var random: Int = 0
    var pass : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_screen)
        email = intent.getStringExtra("email").toString()
        pass = intent.getStringExtra("pass").toString()
        auth = FirebaseAuth.getInstance()

        database = Firebase.database.reference // Initializing database reference

        val imgView = findViewById<ImageView>(R.id.imageView)
        val iback = Intent(this@otp_screen,MainActivity::class.java)
        imgView.setOnClickListener {
            startActivity(iback)
        }

        val otpResend = findViewById<TextView>(R.id.otpResend)
        otpResend.setOnClickListener {
            Toast.makeText(this@otp_screen,"OTP is sent to your email address",Toast.LENGTH_SHORT).show()
            random()
        }

        random()

        val otp1 = findViewById<EditText>(R.id.otp1)
        val otp2 = findViewById<EditText>(R.id.otp2)
        val otp3 = findViewById<EditText>(R.id.otp3)
        val otp4 = findViewById<EditText>(R.id.otp4)
        val donebtn = findViewById<Button>(R.id.donebtn)

        otp1.doOnTextChanged { text, start, before, count ->
            if (!text.isNullOrEmpty()) {
                otp2.requestFocus()
            }
        }

        otp2.doOnTextChanged { text, start, before, count ->
            if (!text.isNullOrEmpty()) {
                otp3.requestFocus()
            } else {
                otp1.requestFocus()
            }
        }

        otp3.doOnTextChanged { text, start, before, count ->
            if (!text.isNullOrEmpty()) {
                otp4.requestFocus()
            } else {
                otp2.requestFocus()
            }
        }

        otp4.doOnTextChanged { text, start, before, count ->
            if (text.isNullOrEmpty()) {
                otp3.requestFocus()
            }

            donebtn.setOnClickListener {
                val o1 = otp1.text.toString()
                val o2 = otp2.text.toString()
                val o3 = otp3.text.toString()
                val o4 = otp4.text.toString()

                val enteredOtp = "$o1$o2$o3$o4".toIntOrNull()

                if (o1.isEmpty() || o2.isEmpty() || o3.isEmpty() || o4.isEmpty()) {
                    Toast.makeText(this@otp_screen, "Please Enter OTP", Toast.LENGTH_SHORT).show()
                } else if (enteredOtp != random) {
                    Toast.makeText(this@otp_screen, "$random", Toast.LENGTH_SHORT).show()
                } else {
                    auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            saveUserData()
                            var intent = Intent(this@otp_screen, home_screen::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this@otp_screen, it.exception?.message.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun saveUserData() {
        val user = UserModel(email, pass)
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        // save data to Firebase data base
        database.child("user").child(userId).setValue(user)
    }

    fun random(){
        random = Random.nextInt(1000..9999)

        val mail = SendMail(
            "nikhilnchavan1@gmail.com", "fccprskfzbgfzrny",
            email,
            "Testing Email Sending"+random,
            "Yes, it's working well\nI will use it always."
        )
        mail.execute()
    }
}
