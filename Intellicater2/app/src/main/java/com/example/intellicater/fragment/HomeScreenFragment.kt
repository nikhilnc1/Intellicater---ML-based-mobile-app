package com.example.intellicater.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.intellicater.MenuBottomSheetFragment
import com.example.intellicater.R
import com.example.intellicater.adapter.RecommendedAdapter
import com.example.intellicater.databinding.FragmentHomeScreenBinding
import com.example.waveoffood.Model.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONException
import org.json.JSONObject

class HomeScreenFragment : Fragment() {

    private lateinit var binding: FragmentHomeScreenBinding
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var mAdapter: RecommendedAdapter
    private lateinit var database: FirebaseDatabase
    private val apiUrl = "https://recommend-food.onrender.com/recommendation"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = FirebaseDatabase.getInstance()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        binding.viewAllMenu.setOnClickListener {
            val bottomSheetDialog = MenuBottomSheetFragment()
            bottomSheetDialog.show(parentFragmentManager, "Test")
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mRecyclerView = view.findViewById(R.id.recommendFood1)
        mLayoutManager = LinearLayoutManager(requireContext())
        mRecyclerView.layoutManager = mLayoutManager

        val queue = Volley.newRequestQueue(requireContext())
        val url = "https://recommend-food-2.onrender.com/recommendation"
        val currentUser = FirebaseAuth.getInstance().currentUser

        var userID = currentUser?.uid

        val stringRequest = object : StringRequest(
            Request.Method.POST,
            url,
            Response.Listener<String> { response ->
                try {

                    val jsonArray = JSONObject(response).getJSONArray("recommended_items")
                    val recommendedItems = ArrayList<String>()

                    // Convert JSONArray to ArrayList
                    for (i in 0 until jsonArray.length()) {
                        val itemID = jsonArray.getString(i)
                        recommendedItems.add(itemID)
                    }

                    // Now you have the recommended items array
                    Log.d("RecommendedItems", recommendedItems.toString())
                    fetchMenuItems(recommendedItems)

                } catch (e: JSONException) {
                    e.printStackTrace()
                    Log.e("JSONException", "Error parsing JSON response: ${e.message}")
                }
            },
            Response.ErrorListener { error ->
                error.printStackTrace()
                Log.e("VolleyError", "Error fetching recommendation: ${error.message}")
            }) {

            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                if (userID != null) {
                    params["userID"] = userID
                } else {
                    // Handle the case where userID is null (optional)
                    // You can log a message or set a default value here
                    Log.d("Recommended", "User ID is null")
                }
                return params
            }
        }

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }

    private fun fetchMenuItems(itemList: ArrayList<String>) {
        val menuRef = database.getReference("menu")
        menuRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val allMenuItems = mutableListOf<MenuItem>()
                for (menuSnapshot in dataSnapshot.children) {
                    val menu = menuSnapshot.getValue(MenuItem::class.java)
                    if (menu != null) {
                        allMenuItems.add(menu)
                    }
                }

                // Filter menu items based on the provided list
                val filteredMenuList = allMenuItems.filter { menuItem ->
                    itemList.contains(menuItem.menuKey)
                }

                // Update adapter with filtered menu items
                mAdapter = RecommendedAdapter(filteredMenuList, requireContext())
                mRecyclerView.adapter = mAdapter
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("HomeScreenFragment", "Firebase data retrieval error: $databaseError")
                // Handle error, such as displaying a message or retrying
            }
        })
    }


    companion object {
        // Mock response for testing
        private const val mockResponse = """{"items":["item1", "item2", "item3"]}"""
    }
}
