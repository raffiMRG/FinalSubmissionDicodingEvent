package com.example.coba1submission.ui.dashboard
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coba1submission.data.response.EventResponse
import com.example.coba1submission.data.response.ListEventsItem
import com.example.coba1submission.databinding.FragmentDashboardBinding
//import com.example.coba1submission.ui.dashboard

//import com.example.yourapplication.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
//==================================

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvReview.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvReview.addItemDecoration(itemDecoration)

//        dashboardViewModel.restaurant.observe(viewLifecycleOwner) { restaurant ->
//            setRestaurantData(restaurant)
//        }

        dashboardViewModel.listEvents.observe(viewLifecycleOwner) { events ->
            setReviewData(events)
        }

//        dashboardViewModel.isLoading.observe(viewLifecycleOwner) {
//            showLoading(it)
//        }

        dashboardViewModel.findRestaurant()


// ===========================================
        return binding.root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//
//
//
//    }

//    private fun setRestaurantData(restaurant: EventResponse) {
//        binding.tvTitle.text = restaurant.message
//    }

    private fun setReviewData(events: List<ListEventsItem>) {
        val adapter = EventAdapter()
        adapter.submitList(events)
        binding.rvReview.adapter = adapter
    }

//    private fun showLoading(isLoading: Boolean) {
//        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}