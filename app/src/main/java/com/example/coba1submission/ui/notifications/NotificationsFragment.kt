package com.example.coba1submission.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.coba1submission.data.response.ListEventsItem
import com.example.coba1submission.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!
    private lateinit var NotificationViewModel: NotificationsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        NotificationViewModel = ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
//==================================

        val layoutManager = LinearLayoutManager(requireContext())
//        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.rvReview.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvReview.addItemDecoration(itemDecoration)

//        dashboardViewModel.restaurant.observe(viewLifecycleOwner) { restaurant ->
//            setRestaurantData(restaurant)
//        }

        NotificationViewModel.listEvents.observe(viewLifecycleOwner) { events ->
            setReviewData(events)
        }

//        dashboardViewModel.isLoading.observe(viewLifecycleOwner) {
//            showLoading(it)
//        }

        NotificationViewModel.findRestaurant()


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
        val adapter = NotificationAdapter()
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