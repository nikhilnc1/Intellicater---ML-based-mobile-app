package com.example.intellicater

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.intellicater.databinding.ActivityPayOutBinding
import com.example.intellicater.fragment.CongratsBottonSheet
import com.example.waveoffood.Model.OrderDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.UUID

class PayOutActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var name: String
    private lateinit var uniqueToken: String

    private lateinit var totalAmount: String
    private lateinit var menuKey : ArrayList<String>
    private lateinit var foodItemName: ArrayList<String>
    private lateinit var foodItemPrice: ArrayList<String>
    private lateinit var foodItemDescription: ArrayList<String>
    private lateinit var foodItemIngredient: ArrayList<String>
    private lateinit var foodItemQuantities: ArrayList<Int>
    private lateinit var databaseReference: DatabaseReference
    private lateinit var userId: String
    private lateinit var binding:ActivityPayOutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayOutBinding.inflate(layoutInflater)
        setContentView(binding.root) // Set the content view from the binding's root view

        // Generate a unique token using UUID
        uniqueToken = UUID.randomUUID().toString().substring(0, 8)

        // Set the generated token to the TextView
        binding.tokenTextView.text = "Token: $uniqueToken"

        // Initialize Firebase and User details
        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference()
        // set user data
//        setUserData()

        val intent = intent
        foodItemName = intent.getStringArrayListExtra("FoodItemName") as ArrayList<String>
        foodItemPrice = intent.getStringArrayListExtra("FoodItemPrice") as ArrayList<String>
        foodItemQuantities = intent.getIntegerArrayListExtra("FoodItemQuantities") as ArrayList<Int>
        menuKey = intent.getStringArrayListExtra("menuKey") as ArrayList<String>

        totalAmount = calculateTotalAmount().toString() +"Rs"
        binding.totalAmount.setText(totalAmount)
        binding.backeButton.setOnClickListener {
            finish()
        }

        binding.PlaceMyOrder.setOnClickListener{

                placeOrder()
                val bottomSheetDialog = CongratsBottonSheet()
                bottomSheetDialog.show(supportFragmentManager, "Test")


        }


    }

    private fun placeOrder() {
        userId = auth.currentUser?.uid?:""
        val time = System.currentTimeMillis()
        val itemPushKey= databaseReference.child("OrderDetails").push().key
        val orderDetails = OrderDetails(userId,menuKey, foodItemName, foodItemPrice, foodItemQuantities,totalAmount, false, false, itemPushKey, time, uniqueToken)
        val orderReference = databaseReference.child("OrderDetails").child(itemPushKey!!)
        orderReference.setValue(orderDetails).addOnSuccessListener {
            val bottomSheetDialog = CongratsBottonSheet()
            bottomSheetDialog.show(supportFragmentManager, "Test")
            removeItemFromCart()
            addOrderToHistory(orderDetails)

        }
            .addOnFailureListener {
                Toast.makeText(this, "failed to order 😒", Toast.LENGTH_SHORT).show()
            }
    }

    private fun calculateTotalAmount(): Int {
        var totalAmount = 0
        for ( i in 0 until foodItemPrice.size){
            var price = foodItemPrice[i]
            val lastChar = price.last()
            val priceIntVale = if (lastChar == '$'){
                price.dropLast(1).toInt()
            }else
            {
                price.toInt()
            }
            var quantity = foodItemQuantities[i]
            totalAmount += priceIntVale *quantity
        }
        return totalAmount
    }

    private fun addOrderToHistory(orderDetails: OrderDetails) {
        databaseReference.child("user").child(userId).child("BuyHistory").child(orderDetails.itemPushKey!!).setValue(orderDetails).addOnSuccessListener {

        }
    }
    private fun removeItemFromCart() {
        val cartItemsReference = databaseReference.child("user").child(userId).child("CartItems")
        cartItemsReference.removeValue()
    }

//    private fun setUserData() {
//        val user = auth.currentUser
//        if (user != null) {
//            val userId = user.uid
//            val userReference = databaseReference.child("user").child(userId)
//
//            userReference.addListenerForSingleValueEvent(object : ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//
//                    if (snapshot.exists()){
//                        val names = snapshot.child("name").getValue(String::class.java)?: ""
//                        binding.apply {
//                            name.setText(names)
//                        }
//                    }
//
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//
//                }
//            })
//        }
//    }

}