import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.intellicater.R
//import com.example.intellicater.adapter.RecommendedAdapter


class HomeFragment : Fragment() {
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mLayoutManager: LinearLayoutManager
//    private lateinit var mAdapter: RecommendedAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        mRecyclerView = view.findViewById(R.id.recommendFood)
//
//        mLayoutManager = LinearLayoutManager(requireContext())
//        mRecyclerView.layoutManager = mLayoutManager
//
//        val names = listOf("Burger", "Sandwich", "Momo", "Item")
//        val prices = listOf("$5", "$7", "$8", "$5")
//        val images = listOf(
//            R.drawable.cart,
//            R.drawable.chai,
//            R.drawable.chapati,
//            R.drawable.dalbhaat
//        )
//
//        mAdapter = RecommendedAdapter(names, prices)
//        mRecyclerView.adapter = mAdapter
//    }
}
