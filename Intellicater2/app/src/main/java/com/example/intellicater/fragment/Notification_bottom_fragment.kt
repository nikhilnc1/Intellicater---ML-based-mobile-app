package com.example.intellicater.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.intellicater.R
import com.example.intellicater.adapter.NotificationAdapter
import com.example.intellicater.databinding.FragmentNotificationBottomFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class Notification_bottom_fragment : BottomSheetDialogFragment() {


    private lateinit var binding: FragmentNotificationBottomFragmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNotificationBottomFragmentBinding.inflate(layoutInflater, container, false)
        val notifications = listOf(
            "Your order has been Canceled Successfully",
            "Order has been taken by the driver",
            "Congrats Your Order Placed"
        )
        val notificationImages = listOf(
            R.drawable.sademoji, R.drawable.truck, R.drawable.congrats
        )
        val adapter = NotificationAdapter(
            java.util.ArrayList(notifications),
            ArrayList(notificationImages)
        )
        binding.notificationRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.notificationRecyclerView.adapter = adapter
        return binding.root
    }
    companion object {
    }
}