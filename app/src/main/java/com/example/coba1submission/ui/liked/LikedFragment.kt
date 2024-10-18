package com.example.coba1submission.ui.liked

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.window.application
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coba1submission.data.helper.ViewModelFactory
import com.example.coba1submission.data.response.Event
import com.example.coba1submission.data.response.ListEventsItem
import com.example.coba1submission.databinding.FragmentLikedBinding
import com.example.coba1submission.ui.adapter.Adapter
import com.example.coba1submission.ui.liked.LikedAdapter
import com.example.coba1submission.ui.details.DetailsViewModel

class LikedFragment : Fragment() {
    private var _binding: FragmentLikedBinding? = null
    private val binding get() = _binding!!
    private lateinit var likedViewModel: LikedViewModel
    private lateinit var likedViewModelVactory: LikedViewModelFactory
    private lateinit var detailsViewModel: DetailsViewModel
    private lateinit var likedAdapter: LikedAdapter
    private lateinit var ids: MutableList<Int>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLikedBinding.inflate(inflater, container, false)

        val layoutManager = LinearLayoutManager(requireContext())

        binding.rvReview.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvReview.addItemDecoration(itemDecoration)


        // Mengamati LiveData<List<Event>> untuk mendapatkan semua data Event
        ids = mutableListOf()
        detailsViewModel = obtainViewModel(this)
        detailsViewModel.getAllData().observe(viewLifecycleOwner) { events ->
            // 'events' adalah List<Event> yang diambil dari LiveData
            // Menampilkan data atau menggunakannya di UI
            ids.clear()
            events.forEachIndexed { index, event ->
                Log.d("eventId", "index ke: ${index} \n id: ${event.id}: ")
                ids.add(event.id)
            }
//            SETUP VIEW MODEL
            likedViewModel = ViewModelProvider(this, LikedViewModelFactory(ids))[LikedViewModel::class.java]

//            =========== NYALAKAN KALO G PAKE setReviewData() ================

            // Observe the events list from ViewModel
//            DISINI MASIH KEBACA ADA 2 PADAHAL UDAH DI APUS 1
            likedViewModel.eventsResponseList.observe(viewLifecycleOwner) { events ->
                events.forEachIndexed { index, event ->
                    Log.d("EventFromApi", "id: ${event.id} \n desc: ${event.description}")
                }
                setReviewData(events)
            }
        }
        return binding.root
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setReviewData(events: List<Event>) {
        val adapter = LikedAdapter()
        adapter.submitList(events)
        binding.rvReview.adapter = adapter
    }

    private fun obtainViewModel(activity: LikedFragment): DetailsViewModel {
        val application = requireActivity().application
        val factory = ViewModelFactory.getInstance(application)
        return ViewModelProvider(activity, factory).get(DetailsViewModel::class.java)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}