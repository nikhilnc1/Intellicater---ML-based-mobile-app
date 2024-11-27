package com.example.intellicater

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.intellicater.databinding.ActivityDetailsBinding
import com.example.waveoffood.Model.CartItems
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private var menuKey: String? = null
    private var foodName: String? = null
    private var foodDescriptions: String? = null
    private var foodIngredients: String? = null
    private var foodPrice: String? = null
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // initialize FirebaseAuth
        auth = FirebaseAuth.getInstance()
        menuKey = intent.getStringExtra("menuKey")
        foodName = intent.getStringExtra("MenuItemName")
        foodDescriptions = intent.getStringExtra("MenuItemDescription")
        foodIngredients = intent.getStringExtra("MenuItemIngredients")
        foodPrice = intent.getStringExtra("MenuItemPrice")

        with(binding) {
            detailFoodName.text = foodName
            detailDescription.text = foodDescriptions
            detailIngredients.text = foodIngredients
        }

        binding.imageButton.setOnClickListener {
            finish()
        }
        binding.addItemButton.setOnClickListener {
            addItemToCart()
        }
    }

    private fun addItemToCart() {
        val database = FirebaseDatabase.getInstance().reference
        val userId = auth.currentUser?.uid ?: ""

        //Create a cartItems object
        val cartItem = CartItems(
            menuKey.toString(),
            foodName.toString(),
            foodPrice.toString(),
            foodDescriptions.toString(),
             // No need to pass the image URL here since it's removed
            1
        )

        // save data to cart item to firebase database
        database.child("user").child(userId).child("CartItems").push().setValue(cartItem)
            .addOnSuccessListener {
                Toast.makeText(this, "Items added into cart successFully 😁", Toast.LENGTH_SHORT)
                    .show()
            }.addOnFailureListener {
                Toast.makeText(this, "Item Not added 😒", Toast.LENGTH_SHORT).show()
            }
    }
}