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
import com.example.coba1submission.databinding.FragmentLikedBinding
import com.example.coba1submission.ui.details.DetailsViewModel

class LikedFragment : Fragment() {
    private var _binding: FragmentLikedBinding? = null
    private val binding get() = _binding!!
    private lateinit var likedViewModel: LikedViewModel
    private lateinit var likedViewModelVactory: LikedViewModelFactory
    private lateinit var eventViewModel: EventViewModel
    private lateinit var detailsViewModel: DetailsViewModel
    private lateinit var likedAdapter: LikedAdapter
//    private val detailsViewModel: DetailsViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        likedViewModel = ViewModelProvider(this).get(LikedViewModel::class.java)

        _binding = FragmentLikedBinding.inflate(inflater, container, false)

        val layoutManager = LinearLayoutManager(requireContext())

        binding.rvReview.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvReview.addItemDecoration(itemDecoration)

//        eventViewModel = ViewModelProvider(this).get(EventViewModel::class.java)

        detailsViewModel = obtainViewModel(this)
        // Mengamati LiveData<List<Event>> untuk mendapatkan semua data Event
        detailsViewModel.getAllData().observe(viewLifecycleOwner) { events ->
            // 'events' adalah List<Event> yang diambil dari LiveData
            // Menampilkan data atau menggunakannya di UI

            events.forEachIndexed { index, event ->
                Log.d("eventId", "index ke: ${index} \n id: ${event.id}: ")



            }


//            for (event in events) {
//                Log.d("eventId", "onCreateView: ")
//                println(event) // Atau gunakan di RecyclerView atau lainnya
//                likedViewModelVactory = LikedViewModelFactory(event.id)
//                ViewModelProvider(this, likedViewModelVactory).get(likedViewModel::class.java)
//            }


//        likedViewModel.listEvents.observe(viewLifecycleOwner) { events ->
//            setReviewData(events)
//        }
//
//        likedViewModel.isLoading.observe(viewLifecycleOwner) {
//            showLoading(it)
//        }
//
//        likedViewModel.findRestaurant()
        }
        return binding.root
    }

//    private fun setReviewData(events: List<ListEventsItem>) {
    private fun setReviewData(events: Event) {
//        val adapter = LikedAdapter()
//        BUAT LIST YANG ISINYA SEMUA EVENT YANG ADA DI TABLE EVENT DI SQLLITE
//        adapter.submitList(events)
//        adapter.submitList(events)
//        binding.rvReview.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
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