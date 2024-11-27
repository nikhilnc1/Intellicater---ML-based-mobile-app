package com.example.intellicater

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class forgotpass_activity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgotpass)

        val email = findViewById<EditText>(R.id.emailAddress12)


        val auth = FirebaseAuth.getInstance()

        val signInRedirect = findViewById<TextView>(R.id.signInRedirect)

        signInRedirect.setOnClickListener{
            startActivity(Intent(this@forgotpass_activity,login_activity::class.java))
        }

        val b = findViewById<Button>(R.id.RegisterButton11)

        b.setOnClickListener {
            val e = email.text.toString().trim()

            auth.fetchSignInMethodsForEmail(e)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val signInMethods = task.result?.signInMethods ?: emptyList()

                        if (signInMethods.isNotEmpty()) {
                            // Email is registered, proceed with sending the password reset email
                            auth.sendPasswordResetEmail(e)
                                .addOnCompleteListener { resetTask ->
                                    if (resetTask.isSuccessful) {
                                        // Password reset email sent successfully
                                        Toast.makeText(this@forgotpass_activity, "Password reset email sent to $e", Toast.LENGTH_SHORT).show()
                                    } else {
                                        // Handle other errors during password reset
                                        val errorMessage = resetTask.exception?.message ?: "Unknown error"
                                        Toast.makeText(this@forgotpass_activity, "Failed to send password reset email: $errorMessage", Toast.LENGTH_SHORT).show()
                                    }
                                }
                        } else {
                            // Email is not registered
                            Toast.makeText(this@forgotpass_activity, "Email is not registered.", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        // Handle other errors during email check
                        val errorMessage = task.exception?.message ?: "Unknown error"
                        Toast.makeText(this@forgotpass_activity, "Failed to check email: $errorMessage", Toast.LENGTH_SHORT).show()
                    }
                }
        }




    }
}