package com.example.coba1submission.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coba1submission.data.response.ListEventsItem
import com.example.coba1submission.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var activeHomeViewModel: ActiveHomeViewModel
    private lateinit var finishedHomeViewModel: FinishedHomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.activeRecycleView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        model1()

//        activeHomeViewModel = ViewModelProvider(this).get(ActiveHomeViewModel::class.java)
//
//        activeHomeViewModel.listEvents.observe(viewLifecycleOwner){ events ->
//            setActiveReviewData(events)
//        }
//
//        activeHomeViewModel.findEvent()

// =====================================================================
        binding.finishedRecycleView.layoutManager =
            LinearLayoutManager(requireContext())

        model2()

//        finishedHomeViewModel = ViewModelProvider(this).get(FinishedHomeViewModel::class.java)
//
//        finishedHomeViewModel.listEvents.observe(viewLifecycleOwner){ events ->
//            setFinishedReviewData(events)
//        }
//
//        finishedHomeViewModel.isLoading.observe(viewLifecycleOwner){
//            showLoading(it)
//        }
//
//        finishedHomeViewModel.findEvent()


//        REFRESH FITUR
//        binding.containerFragment.setOnRefreshListener {
//            reloadData()  // Memuat ulang data saat swipe to refresh diaktifkan
//            swipeRefreshLayout.isRefreshing = false  // Berhenti memutar indikator refresh
//        }

        val root: View = binding.root
        return root
    }

    private fun model1(){
        activeHomeViewModel = ViewModelProvider(this).get(ActiveHomeViewModel::class.java)

        activeHomeViewModel.listEvents.observe(viewLifecycleOwner){ events ->
            setActiveReviewData(events)
        }

        activeHomeViewModel.findEvent()
    }

    private fun model2(){
        finishedHomeViewModel = ViewModelProvider(this).get(FinishedHomeViewModel::class.java)

        finishedHomeViewModel.listEvents.observe(viewLifecycleOwner){ events ->
            setFinishedReviewData(events)
        }

        finishedHomeViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

        finishedHomeViewModel.findEvent()
    }

    private fun setActiveReviewData(events: List<ListEventsItem>) {
        val adapter = ActiveHomeAdapter()
        adapter.submitList(events)
        binding.activeRecycleView.adapter = adapter
    }

    private fun setFinishedReviewData(events: List<ListEventsItem>) {
        val adapter = FinishedHomeAdapter()
        adapter.submitList(events)
        binding.finishedRecycleView.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}