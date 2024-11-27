package com.example.intellicater.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.intellicater.DetailsActivity
import com.example.intellicater.databinding.MenuItemBinding
import com.example.waveoffood.Model.MenuItem

class MenuAdapter(private val menuItems: List<MenuItem>,
                  private val requireContext: Context
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = MenuItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = menuItems.size

    inner class MenuViewHolder(private val binding: MenuItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    openDetailsActivity(position)
                }
            }
        }

        private fun openDetailsActivity(position: Int) {
            val menuItem = menuItems[position]

            // an intent to open details activity and pass data
            val intent = Intent(requireContext, DetailsActivity::class.java).apply {
                putExtra("menuKey",menuItem.menuKey)
                putExtra("MenuItemName", menuItem.foodName)
                putExtra("MenuItemDescription", menuItem.foodDescription)
                putExtra("MenuItemIngredients", menuItem.foodIngredient)
                putExtra("MenuItemPrice", menuItem.foodPrice)
            }
            // start the details activity
            requireContext.startActivity(intent)
        }

        // set data into RecyclerView items name, price, and other attributes
        fun bind(position: Int) {
            val menuItem = menuItems[position]
            binding.apply {
                menufoodName.text = menuItem.foodName
                menuPrice.text = menuItem.foodPrice
            }
        }
    }
}
