package com.example.coba1submission.ui.liked

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coba1submission.R
import com.example.coba1submission.data.helper.ViewModelFactory
import com.example.coba1submission.data.response.Event
import com.example.coba1submission.databinding.FragmentLikedBinding
import com.example.coba1submission.ui.details.DetailsViewModel

class LikedFragment : Fragment() {
    private var _binding: FragmentLikedBinding? = null
    private val binding get() = _binding!!
    private lateinit var likedViewModel: LikedViewModel
    private lateinit var detailsViewModel: DetailsViewModel
    private val ids: MutableList<Int> = mutableListOf()
    private val idsLiveData = MutableLiveData(ids)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLikedBinding.inflate(inflater, container, false)

        val layoutManager = LinearLayoutManager(requireContext())

        binding.rvReview.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvReview.addItemDecoration(itemDecoration)

        detailsViewModel = obtainViewModel(this)
        detailsViewModel.getAllData().observe(viewLifecycleOwner) { events ->
            if (events.isNullOrEmpty()) {
                binding.likedStatus.visibility = View.VISIBLE
                binding.rvReview.visibility = View.INVISIBLE
                binding.likedStatus.text = getString(R.string.event_notfound)
            }else {
                ids.clear()
                events.forEachIndexed { _, event ->
                    ids.add(event.id)
                }

                likedViewModel = ViewModelProvider(
                    this@LikedFragment,
                    LikedViewModelFactory(idsLiveData)
                )[LikedViewModel::class.java]
                likedViewModel.updateIds(ids)
                likedViewModel.eventsResponseList.observe(viewLifecycleOwner) { likedEvents ->
                    setReviewData(likedEvents)
                }
                likedViewModel.isLoading.observe(viewLifecycleOwner) {
                    showLoading(it)
                }
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
        return ViewModelProvider(activity, factory)[DetailsViewModel::class.java]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}