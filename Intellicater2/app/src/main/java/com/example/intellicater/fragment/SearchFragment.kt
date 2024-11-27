
package com.example.intellicater.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.intellicater.R
import com.example.intellicater.adapter.MenuAdapter
import com.example.intellicater.databinding.FragmentSearchBinding


class  SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding

    private lateinit var adapter : MenuAdapter

    private val originalFoodName = listOf("burger","pizza","sandwich","burger","momo","sandwich","burger","momo","sandwich","burger","momo","sandwich")
    private val originalMenuItemPrice = listOf("$4","$5","$4","$4","$5","$4","$4","$5","$4","$4","$5","$4")
    val originalMenuImage = listOf(
        R.drawable.chapati,
        R.drawable.chapati,
        R.drawable.chapati,
        R.drawable.chapati,
        R.drawable.chapati,
        R.drawable.chapati,
        R.drawable.chapati,
        R.drawable.chapati,
        R.drawable.chapati,
        R.drawable.chapati,
        R.drawable.chapati,
        R.drawable.chapati
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private val filteredMenuFoodName = mutableListOf<String>()
    private val filteredMenuItemPrice = mutableListOf<String>()
    private val filteredMenuImage = mutableListOf<Int>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater,container,false)

//        adapter = MenuAdapter(filteredMenuFoodName,filteredMenuItemPrice)
        binding.menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.menuRecyclerView.adapter = adapter

        //set up for search view
        setupSearchView()
        //show all menu items

        showAllMenu()


        return binding.root
    }

    private fun showAllMenu() {
        filteredMenuFoodName.clear()
        filteredMenuItemPrice.clear()
        filteredMenuImage.clear()

        filteredMenuFoodName.addAll(originalFoodName)
        filteredMenuItemPrice.addAll(originalMenuItemPrice)
        filteredMenuImage.addAll(originalMenuImage)

        adapter.notifyDataSetChanged()
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle query text submission here
                filterMenuItems(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Handle query text change here
                filterMenuItems(newText)
                return true
            }
        })
    }

    private fun filterMenuItems(query: String?) {

        filteredMenuFoodName.clear()
        filteredMenuItemPrice.clear()
        filteredMenuImage.clear()

        originalFoodName.forEachIndexed { index, foodName ->
            if (foodName.contains(query.toString(), ignoreCase = true)){
                filteredMenuFoodName.add(foodName)
                filteredMenuItemPrice.add(originalMenuItemPrice[index])
                filteredMenuImage.add(originalMenuImage[index])
            }
        }
        adapter.notifyDataSetChanged()

    }

    companion object {

    }
}