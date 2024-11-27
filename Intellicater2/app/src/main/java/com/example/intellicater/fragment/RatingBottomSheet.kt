import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import com.example.intellicater.databinding.FragmentRatingBottomSheetBinding
import com.example.waveoffood.Model.OrderDetails
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RatingBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentRatingBottomSheetBinding
    private var orderDetails: OrderDetails? = null
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRatingBottomSheetBinding.inflate(inflater, container, false)
        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()

        arguments?.let {
            orderDetails = it.getParcelable("orderDetails")
        }

        setupRatingBar()

        return binding.root
    }

    private fun setupRatingBar() {
        binding.rbStars.setOnRatingBarChangeListener { _, rating, _ ->
            val ratingValue = rating.toInt()
            val currentUserID = auth.currentUser?.uid ?: ""
            val foodName = orderDetails?.foodNames?.firstOrNull() ?: ""
            val menuKey = orderDetails?.menuKey?.firstOrNull() ?: ""
            updateRatingInFirebase(ratingValue, foodName, currentUserID, menuKey)
            dismiss()
        }
    }

    private fun updateRatingInFirebase(rating: Int, foodName: String, currentUserID: String, menuKey: String) {
        val ratingRef = database.reference
            .child("Ratings")
            .child(currentUserID)
            .child(menuKey)
        ratingRef.setValue(rating)
            .addOnSuccessListener {
                // Rating updated successfully
            }
            .addOnFailureListener {
                // Failed to update rating
            }
    }

    companion object {
        fun newInstance(orderDetails: OrderDetails?): RatingBottomSheet {
            val fragment = RatingBottomSheet()
            fragment.arguments = Bundle().apply {
                putParcelable("orderDetails", orderDetails)
            }
            return fragment
        }
    }
}


//    private fun updateRatingInFirebase(rating: Int, foodName: String, userUid: String, menuKey: String) {
//
//        val menuRef = database.getReference("rating")
////        // Generate a unique key for the new menu item
//        val ratingKey = menuRef.push().key
//        val newRating = RatingModel(
//            menuKey,foodName, rating, userUid
//        )
////
////
////
//        ratingKey?.let { menukey ->
//            menuRef.child(menukey).setValue(newRating).addOnSuccessListener {
////                Toast.makeText(this, "data uploaded successfully", Toast.LENGTH_SHORT).show()
//            }
//                .addOnFailureListener {
////                    Toast.makeText(this, "data uploaded failed", Toast.LENGTH_SHORT).show()
//                }
//        }
//
//
//
//    }

