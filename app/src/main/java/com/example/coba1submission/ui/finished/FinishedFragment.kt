package com.example.coba1submission.ui.finished

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coba1submission.data.response.ListEventsItem
import com.example.coba1submission.databinding.FragmentNotificationsBinding
import com.example.coba1submission.ui.adapter.Adapter

class FinishedFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!
    private lateinit var notificationViewModel: FinishedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        notificationViewModel = ViewModelProvider(this)[FinishedViewModel::class.java]

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)

        val layoutManager = LinearLayoutManager(requireContext())

        binding.rvReview.layoutManager = layoutManager

        // atur agar setiap item yang ada di resicle view memiliki batas garis tipis
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvReview.addItemDecoration(itemDecoration)

        notificationViewModel.listEvents.observe(viewLifecycleOwner) { events ->
            setReviewData(events)
        }

        notificationViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        notificationViewModel.findRestaurant()

        return binding.root
    }

    private fun setReviewData(events: List<ListEventsItem>) {
        val adapter = Adapter()
        adapter.submitList(events)
        binding.rvReview.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}